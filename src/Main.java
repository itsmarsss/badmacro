package src;

import src.macro.Macro;

import javax.swing.*;
import java.util.Properties;

public class Main {
    public static void main(String[]args) {
        //System.setProperty("sun.java2d.uiScale", "1");
        setLooksAndFeels();
        Macro mac = new Macro();
        mac.startMacro();
    }

    private static void setLooksAndFeels() {
        try {
            String className = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
