package src.macro;

import java.awt.*;
import java.util.LinkedList;

public class RunSequence implements Runnable {
    private MacroInfo macro;

    public void setMacro(MacroInfo macro) {
        this.macro = macro;
    }

    @Override
    public void run() {
        Executer ex;
        try {
            ex = new Executer();
        } catch (AWTException e) {
            return;
        }
        LinkedList<SequenceItem> sequence = new LinkedList<>(macro.getSequence());
        while (!sequence.isEmpty()) {
            SequenceItem seqItem = sequence.pop();
            String name = seqItem.toString();
            if (name.startsWith("Delay: ")) {
                try {
                    Thread.sleep(seqItem.getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (name.startsWith("KeyUp: ")) {
                ex.keyRelease(seqItem.getValue());
            } else if (name.startsWith("KeyDown: ")) {
                ex.keyPress(seqItem.getValue());
            } else if (name.startsWith("MouseUp: ")) {
                ex.mouseUp(seqItem.getValue());
            } else if (name.startsWith("MouseDown: ")) {
                ex.mouseDown(seqItem.getValue());
            }
        }
    }
}
