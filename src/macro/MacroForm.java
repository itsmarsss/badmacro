package src.macro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacroForm {
    private JList macrosList;
    private JButton editButton;
    private JButton deleteButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton newButton;
    private JButton importButton;
    public JPanel mainPanel;
    private JButton exportButton;

    public MacroForm() {
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
