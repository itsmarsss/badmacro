package src.macro;

import src.macro.seqitems.KeyItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class EditMacroForm {
    private JList seqList;
    private JButton insertButton;
    private JButton removeButton;
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton editButton;
    private JButton duplicateButton;
    public JPanel editPanel;

    private MacroInfo macro;

    public EditMacroForm() {
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = seqList.getSelectedIndex();
                String[] choices = {"KeyUp", "KeyDown", "MouseUp", "MouseDown", "Delay"};
                String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                        "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                        // default
                        // icon
                        choices, // Array of choices
                        choices[1]); // Initial choice
                System.out.println(input);
                if(index == -1) {
                    macro.appendSeqItem(new KeyItem(12));
                }
            }
        });
    }

    public void edit(MacroInfo macro) {
        this.macro = macro;
        seqList.setListData(this.macro.getSequence().toArray());
    }
}
