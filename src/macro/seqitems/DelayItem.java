package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

public class DelayItem extends SequenceItem {
    private int waitDur;
    private Mode mode;

    public DelayItem(int waitDur) {
        this.waitDur = waitDur;
        this.mode = Mode.DELAY;
    }

    public String toString() {
        return "Delay: " + waitDur;
    }

    public int getValue() {
        return waitDur;
    }

    public String toExport() {
        return "Delay: " + waitDur;
    }
}
