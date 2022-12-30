package src.macro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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
    private JLabel statusLabel;

    public LinkedList<MacroInfo> macros = new LinkedList<>();

    public RunSequence sequenceRunner;

    public MacroForm() {
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("Enter a name:");
                if (path == null || path.equals("")) {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid macro name!", "New Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                macros.add(new MacroInfo(path));
                macrosList.setListData(macros.toArray());
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MacroInfo selected = (MacroInfo) macrosList.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                deleteButton.setEnabled(false);

                EditMacroForm editMac = new EditMacroForm();
                JFrame editFrame = new JFrame("Editing macro: " + selected);
                editFrame.setContentPane(editMac.editPanel);
                editMac.edit(selected, editFrame);
                editFrame.pack();
                editFrame.setDefaultCloseOperation(0);
                editFrame.setResizable(false);
                editFrame.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (macrosList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                macros.remove(macrosList.getSelectedIndex());
                macrosList.setListData(macros.toArray());
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (macrosList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (sequenceRunner == null) {
                    sequenceRunner = new RunSequence();
                    sequenceRunner.setMacro(macros.get(macrosList.getSelectedIndex()));
                    sequenceRunner.start();
                    statusLabel.setText("Status: Running \"" + macros.get(macrosList.getSelectedIndex()) + "\"");
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sequenceRunner = null;
                statusLabel.setText("Status: Idle");
            }
        });
    }
}
