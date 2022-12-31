package src.macro.seqitems;

import src.macro.SequenceItem;

public class MoveItem extends SequenceItem {
    private final int x;
    private final int y;

    public MoveItem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "MouseMove: " + x + ":" + y;
    }

    public int[] getValues() {
        return new int[]{x, y};
    }

    public String toExport() {
        return "MouseMove: " + x + ":" + y;
    }
}
