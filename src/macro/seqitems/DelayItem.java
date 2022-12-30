package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

public class DelayItem extends SequenceItem {
    private int waitDur;
    private Mode mode;
    public DelayItem(int waitDur, Mode mode) {
        this.waitDur = waitDur;
        this.mode = mode;
    }
}
