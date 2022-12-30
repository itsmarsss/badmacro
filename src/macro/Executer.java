package src.macro;

import java.awt.*;

public class Executer {
    public final Robot rb = new Robot();

    public Executer() throws AWTException {
    }

    public void keyPress(int keyNum) {
        rb.keyPress(keyNum);
    }
    public void keyRelease(int keyNum) {
        rb.keyRelease(keyNum);
    }

    public void mouseDown(int mouseNum) {
        rb.mousePress(mouseNum);
    }
    public void mouseUp(int mouseNum) {
        rb.mouseRelease(mouseNum);
    }

    public void mouseMove(int[] coords) {
        rb.mouseMove(coords[0], coords[1]);
    }

    public void mouseScroll(int value) {
        rb.mouseWheel(value);
    }
}
