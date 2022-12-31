package src.macro;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KillKeyEdit {
    private JTextField bindTextField;
    private JButton saveButton;
    private JButton cancelButton;
    public JPanel editPanel;

    private MacroInfo macro;
    private MacroInfo original;
    private JFrame frame;
    private JFrame pframe;

    public KillKeyEdit() {
        saveButton.addActionListener(e -> {
            original.setSelf(macro);
            JOptionPane.showMessageDialog(editPanel, "KILLKEY saved!", "Save KILLKEY", JOptionPane.INFORMATION_MESSAGE);
            pframe.setEnabled(true);
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            pframe.setEnabled(true);
            frame.dispose();
        });
        bindTextField.addKeyListener(new KeyAdapter() {

            private void setBind(int code) {
                macro.setBind(code);
                String display = KeyEvent.getKeyText(macro.getBind());
                bindTextField.setText((display.contains("Unknown keyCode: ") ? "Unknown" : display));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                setBind(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setBind(e.getKeyCode());
            }
        });
    }

    public void edit(MacroInfo macro, JFrame frame, JFrame pframe) {
        this.original = macro;
        this.macro = new MacroInfo(macro);
        this.frame = frame;
        this.pframe = pframe;
        bindTextField.setText(
                (KeyEvent.getKeyText(macro.getBind()).contains("Unknown keyCode: ") ?
                        "N/A" : KeyEvent.getKeyText(macro.getBind())));
    }
}
