package src.macro;

import javax.swing.*;

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

    public void edit(MacroInfo macro) {
        seqList.setListData(macro.getSequence().toArray());
    }
}
