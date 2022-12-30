package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

public class ScrollItem extends SequenceItem {
    private final int amount;

    public ScrollItem(int amount) {
        this.amount = amount;
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
