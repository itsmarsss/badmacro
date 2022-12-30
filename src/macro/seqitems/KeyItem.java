package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

public class KeyItem extends SequenceItem {
    private int keyNum;
    private Mode mode;
    public KeyItem(int keyNum, Mode mode) {
        this.keyNum = keyNum;
        this.mode = mode;
    }
}
