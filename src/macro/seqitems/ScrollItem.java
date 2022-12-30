package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

public class ScrollItem extends SequenceItem {
    private final int amount;

    public ScrollItem(int amount) {
        this.amount = amount;
        Mode mode = Mode.SCROLL;
    }

    public String toString() {
        return "MouseScroll: " + amount;
    }

    public int getValue() {
        return amount;
    }

    public String toExport() {
        return "MouseScroll: " + amount;
    }
}
