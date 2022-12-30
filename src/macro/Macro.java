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
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("src/assets/icon.png"));
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
