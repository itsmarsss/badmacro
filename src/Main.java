package src;

import src.macro.MacroForm;
import src.macro.seqitems.Macro;

import javax.swing.*;

public class Main {
    public static void main(String[]args) {
        setLooksAndFeels();
        Macro mac = new Macro();
        mac.Macro();
    }

    private static void setLooksAndFeels() {
        try {
            String className = UIManager.getSystemLookAndFeelClassName();
            System.out.println("className = " + className);
            UIManager.setLookAndFeel(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
