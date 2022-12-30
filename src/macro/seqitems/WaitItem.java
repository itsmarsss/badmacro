package src.macro.seqitems;

import src.macro.SequenceItem;

public class WaitItem extends SequenceItem {
    private int waitDur;
    public WaitItem(int waitDur) {
        this.waitDur = waitDur;
    }
}
