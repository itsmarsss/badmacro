package src.macro;

import java.awt.*;
import java.util.LinkedList;

public class RunSequence extends Thread {
    private MacroInfo macro;

    public void setMacro(MacroInfo macro) {
        this.macro = macro;
    }

    @Override
    public void run() {

        System.out.println(macro);

        LinkedList<SequenceItem> sequence = new LinkedList<>(macro.getSequence());
        Mode runType = macro.getRunType();
        if (runType == Mode.SINGLE) {
            System.out.println("Single");
            start(sequence);
        } else if (runType == Mode.REPEAT) {
            System.out.println("Repeat");
            for (int i = 0; i < macro.getRunIter(); i++) {
                start(sequence);
            }
        } else if (runType == Mode.REPEATUNTILSTOPPED) {
            System.out.println("RepeatUntilStopped");
            while (true) {
                start(sequence);
            }
        }
        Macro.setStatus("Status: Idle");
        MacroForm.isRunning = false;
        this.stop();
    }

    private void start(LinkedList<SequenceItem> seq) {
        Executer ex;
        try {
            ex = new Executer();
        } catch (AWTException e) {
            return;
        }
        LinkedList<SequenceItem> sequence = new LinkedList<>(seq);
        while (!sequence.isEmpty()) {
            SequenceItem seqItem = sequence.pop();
            String name = seqItem.toString();
            if (name.startsWith("Delay: ")) {
                int sleep = seqItem.getValue();
                long start = System.currentTimeMillis();
                while(System.currentTimeMillis()-start < sleep) {
                    System.out.print("");
                }
                System.out.println("sleep");
            } else if (name.startsWith("KeyUp: ")) {
                ex.keyRelease(seqItem.getValue());
                System.out.println("keyup");
            } else if (name.startsWith("KeyDown: ")) {
                ex.keyPress(seqItem.getValue());
                System.out.println("keydown");
            } else if (name.startsWith("MouseUp: ")) {
                ex.mouseUp(seqItem.getValue());
                System.out.println("mouseup");
            } else if (name.startsWith("MouseDown: ")) {
                ex.mouseDown(seqItem.getValue());
                System.out.println("mousedown");
            } else if (name.startsWith("MouseMove: ")) {
                ex.mouseMove(seqItem.getValues());
                System.out.println("mousemove");
            } else if (name.startsWith("MouseScroll: ")) {
                ex.mouseScroll(seqItem.getValue());
                System.out.println("mousescroll");
            } else {
                System.out.println("???");
            }
        }
    }
}
