package src.macro;

import javax.swing.*;

public class Macro {

    public static MacroForm mac = new MacroForm();


    public void startMacro(){
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

    public static void setStatus(String status) {
        mac.setStatus(status);
    }

    public static void updateList() {
        mac.updateList();
    }

}
