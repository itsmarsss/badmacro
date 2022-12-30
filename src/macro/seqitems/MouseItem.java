package src.macro.seqitems;

import src.macro.Mode;
import src.macro.SequenceItem;

import java.awt.event.MouseEvent;

public class MouseItem extends SequenceItem {
    private int mouseNum;
    private Mode mode;
    public MouseItem(int mouseNum, Mode mode) {
        this.mouseNum = mouseNum;
        this.mode = mode;
    }
    public String toString() {
        return (mode == Mode.UP ? "MouseUp: " : "MouseDown: ") + MouseEvent.getMouseModifiersText(mouseNum);
    }
}
