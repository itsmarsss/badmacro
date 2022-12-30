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
            start(sequence);
        } else if (runType == Mode.REPEAT) {
            for (int i = 0; i < macro.getRunIter(); i++) {
                start(sequence);
            }
        } else if (runType == Mode.REPEATUNTILSTOPPED) {
            while (true) {
                start(sequence);
            }
        }
        this.interrupt();
        this.stop();
        Macro.setStatus("Status: Idle");
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
                try {
                    Thread.sleep(seqItem.getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
            } else {
                System.out.println("???");
            }
        }
    }
}
