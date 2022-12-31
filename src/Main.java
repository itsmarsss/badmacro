package src;

import src.macro.Macro;

import javax.swing.*;

public class Main {
    public static void main(String[]args) {
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
