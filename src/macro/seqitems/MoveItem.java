package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

import java.awt.event.MouseEvent;

public class MoveItem extends SequenceItem {
    private int x, y;
    private Mode mode;
    public MoveItem(int x, int y) {
        this.x = x;
        this.y = y;
        this.mode = Mode.MOVE;
    }

    public String toString() {
        return "MouseMove: " + x + ":" + "y";
    }

    public int[] getValues() {
        return new int[]{x, y};
    }

    public String toExport() {
        return "MouseMove: " + x + ":" + y;
    }
}
