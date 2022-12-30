package src.macro;

import src.macro.seqitems.DelayItem;
import src.macro.seqitems.KeyItem;
import src.macro.seqitems.MouseItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
    private JFrame frame;
    private LinkedList<SequenceItem> sequence;

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

                if (input == null) {
                    return;
                }

                SequenceItem seqItem = null;

                switch (input) {
                    case "Delay":
                        int ans;
                        try {
                            ans = Integer.parseInt((String)JOptionPane.showInputDialog(editPanel, "Input delay length (milliseconds)",
                                    "Delay Duration", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null));
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
                            keyUp = Integer.parseInt((String) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyUp Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null));
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
                            keyDown = Integer.parseInt((String) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyDown Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null));
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
                        String[] mchoices = {"Button1", "Button2", "Button3"};
                        String minput = (String) JOptionPane.showInputDialog(editPanel, "Choose a button type",
                                "Button Item Type", JOptionPane.QUESTION_MESSAGE, null,
                                mchoices,
                                mchoices[0]);

                        if(minput == null) {
                            return;
                        }
                        if(minput.equals("Button1")){
                            seqItem = new MouseItem(MouseEvent.BUTTON1, Mode.UP);
                        }else if(minput.equals("Button2")){
                            seqItem = new MouseItem(MouseEvent.BUTTON2, Mode.UP);
                        }else if(minput.equals("Button3")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON3, Mode.UP);
                        }
                        break;
                    case "MouseDown":
                        String[] mdchoices = {"Button1", "Button2", "Button3"};
                        String mdinput = (String) JOptionPane.showInputDialog(editPanel, "Choose a button type",
                                "Button Item Type", JOptionPane.QUESTION_MESSAGE, null,
                                mdchoices,
                                mdchoices[0]);

                        if(mdinput == null) {
                            return;
                        }
                        if(mdinput.equals("Button1")){
                            seqItem = new MouseItem(MouseEvent.BUTTON1, Mode.DOWN);
                        }else if(mdinput.equals("Button2")){
                            seqItem = new MouseItem(MouseEvent.BUTTON2, Mode.DOWN);
                        }else if(mdinput.equals("Button3")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON3, Mode.DOWN);
                        }
                        break;
                }

                if (seqList.getSelectedIndex() == -1) {
                    sequence.add(seqItem);
                } else {
                    sequence.add(seqList.getSelectedIndex(), seqItem);
                }
                seqList.setListData(sequence.toArray());
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seqList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(editPanel, "Please select a sequence item!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                sequence.remove(seqList.getSelectedIndex());
                seqList.setListData(sequence.toArray());
            }
        });
        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seqList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(editPanel, "Please select a sequence item!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int index = seqList.getSelectedIndex();
                if(index == 0) {
                    JOptionPane.showMessageDialog(editPanel, "Sequence item cannot be moved further up!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(index);
                sequence.remove(index);
                sequence.add(index-1, seqItem);
                seqList.setListData(sequence.toArray());
            }
        });
        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seqList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(editPanel, "Please select a sequence item!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int index = seqList.getSelectedIndex();
                if(index == sequence.size()-1) {
                    JOptionPane.showMessageDialog(editPanel, "Sequence item cannot be moved further down!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(index);
                sequence.remove(index);
                sequence.add(index+1, seqItem);
                seqList.setListData(sequence.toArray());
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                macro.setSequence(sequence);
                JOptionPane.showMessageDialog(editPanel, "Macro saved!", "Save Macro", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void edit(MacroInfo macro, JFrame frame) {
        this.macro = macro;
        this.frame = frame;
        sequence = new LinkedList<>(this.macro.getSequence());
        seqList.setListData(sequence.toArray());
    }
}
