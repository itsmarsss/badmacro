package src.macro;

import src.macro.seqitems.DelayItem;
import src.macro.seqitems.KeyItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
                String[] choices = {"Delay", "KeyUp", "KeyDown", "MouseUp", "MouseDown"};
                String input = (String) JOptionPane.showInputDialog(editPanel, "Choose a sequence type",
                        "Sequence Item Type", JOptionPane.QUESTION_MESSAGE, null,
                        choices,
                        choices[0]);

                if(input == null){
                    return;
                }

                SequenceItem seqItem = null;

                switch (input) {
                    case "Delay":
                        int ans;
                        try {
                            ans = (Integer) JOptionPane.showInputDialog(editPanel, "Input delay length (milliseconds)",
                                    "Delay Duration", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                        } catch (Exception x) {
                            ans = -1;
                        }
                        if (ans < 1) {
                            ans = -1;
                        }
                        if (ans == -1) {
                            JOptionPane.showMessageDialog(editPanel, "Invalid input", "Invalid delay", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        seqItem = new DelayItem(ans);
                        break;
                    case "KeyUp":
                        int keyUp;
                        try {
                            keyUp = (Integer) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyUp Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                        } catch (Exception x) {
                            keyUp = -1;
                        }
                        if (KeyEvent.getKeyText(keyUp).contains("Unknown keyCode: ")) {
                            keyUp = -1;
                        }
                        if (keyUp == -1) {
                            JOptionPane.showMessageDialog(editPanel, "Invalid input", "Invalid keycode", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        seqItem = new KeyItem(keyUp, Mode.UP);
                        break;
                    case "KeyDown":
                        int keyDown;
                        try {
                            keyDown = (Integer) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyDown Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                        } catch (Exception x) {
                            keyDown = -1;
                        }
                        if (KeyEvent.getKeyText(keyDown).contains("Unknown keyCode: ")) {
                            keyDown = -1;
                        }
                        if (keyDown == -1) {
                            JOptionPane.showMessageDialog(editPanel, "Invalid input", "Invalid keycode", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        seqItem = new KeyItem(keyDown, Mode.DOWN);
                        break;
                    case "MouseUp":
                        int mouseUp;
                        try {
                            mouseUp = (Integer) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyDown Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                        } catch (Exception x) {
                            mouseUp = -1;
                        }
                        if (KeyEvent.getKeyText(mouseUp).contains("Unknown keyCode: ")) {
                            mouseUp = -1;
                        }
                        if (mouseUp == -1) {
                            JOptionPane.showMessageDialog(editPanel, "Invalid input", "Invalid keycode", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        seqItem = new KeyItem(mouseUp, Mode.DOWN);
                        break;
                    case "MouseDown":
                        int mouseDown;
                        try {
                            mouseDown = (Integer) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyDown Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                        } catch (Exception x) {
                            mouseDown = -1;
                        }
                        if (KeyEvent.getKeyText(mouseDown).contains("Unknown keyCode: ")) {
                            mouseDown = -1;
                        }
                        if (mouseDown == -1) {
                            JOptionPane.showMessageDialog(editPanel, "Invalid input", "Invalid keycode", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        seqItem = new KeyItem(mouseDown, Mode.DOWN);
                        break;
                }

                if(seqList.getSelectedIndex() == -1) {
                    macro.appendSeqItem(seqItem);
                }else{
                    macro.addSeqItem(seqList.getSelectedIndex(), seqItem);
                }
            }
        });
    }

    public void edit(MacroInfo macro) {
        this.macro = macro;
        seqList.setListData(this.macro.getSequence().toArray());
    }
}
