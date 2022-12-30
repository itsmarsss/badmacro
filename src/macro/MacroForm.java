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

    public LinkedList<MacroInfo> macros = new LinkedList<>();

    public MacroForm() {
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("Enter a name:");
                if(path == null || path.equals("")){
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
                if(selected == null) {
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
    }
}
