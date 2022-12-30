package src.macro.seqitems;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import src.macro.Mode;
import src.macro.SequenceItem;

public class ScrollItem extends SequenceItem {
    private int amount;
    private Mode mode;

    public ScrollItem(int amount) {
        this.amount = amount;
        this.mode = Mode.SCROLL;
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
