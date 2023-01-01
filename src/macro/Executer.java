package src.macro;

import java.awt.*;
import java.awt.geom.AffineTransform;

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
        GraphicsConfiguration asdf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        AffineTransform asfd2 = asdf.getDefaultTransform();

        double scaleX = asfd2.getScaleX();
        double scaleY = asfd2.getScaleY();
        rb.mouseMove((int) (coords[0]/scaleX), (int) (coords[1]/scaleY));
    }

    public void mouseScroll(int value) {
        rb.mouseWheel(value);
    }
}
