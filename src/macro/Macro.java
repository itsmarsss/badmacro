package src.macro;

import javax.swing.*;

public class Macro {
    public void startMacro(){
        MacroForm mac = new MacroForm();
        JFrame frame = new JFrame("Bad Macro");
        mac.setParent(frame);
        frame.setContentPane(mac.mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
