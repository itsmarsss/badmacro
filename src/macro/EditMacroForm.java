package src.macro;

import src.macro.seqitems.DelayItem;
import src.macro.seqitems.KeyItem;
import src.macro.seqitems.MouseItem;

import javax.swing.*;
import java.awt.event.*;
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
    private JTextField nameTextField;
    private JTextField bindTextField;
    private JRadioButton singleRadioButton;
    private JRadioButton repeatUntilStoppedRadioButton;
    private JRadioButton repeatRadioButton;
    private JFormattedTextField iterationTextField;
    private JButton plusButton;
    private JButton minusButton;

    private MacroInfo macro;
    private MacroInfo original;
    private JFrame frame;
    private JFrame pframe;
    private LinkedList<SequenceItem> sequence;

    public EditMacroForm() {
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                            String sans = (String) JOptionPane.showInputDialog(editPanel, "Input delay length (milliseconds)",
                                    "Delay Duration", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                            if (sans == null) {
                                return;
                            }
                            ans = Integer.parseInt(sans);
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
                            String skeyUp = (String) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyUp Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                            if (skeyUp == null) {
                                return;
                            }
                            keyUp = Integer.parseInt(skeyUp);
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
                            String skeyDown = (String) JOptionPane.showInputDialog(editPanel, "Input key code",
                                    "KeyDown Key", JOptionPane.QUESTION_MESSAGE, null,
                                    null, null);
                            if (skeyDown == null) {
                                return;
                            }
                            keyDown = Integer.parseInt(skeyDown);
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

                        if (minput == null) {
                            return;
                        }
                        if (minput.equals("Button1")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.UP);
                        } else if (minput.equals("Button2")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.UP);
                        } else if (minput.equals("Button3")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.UP);
                        }
                        break;
                    case "MouseDown":
                        String[] mdchoices = {"Button1", "Button2", "Button3"};
                        String mdinput = (String) JOptionPane.showInputDialog(editPanel, "Choose a button type",
                                "Button Item Type", JOptionPane.QUESTION_MESSAGE, null,
                                mdchoices,
                                mdchoices[0]);

                        if (mdinput == null) {
                            return;
                        }
                        if (mdinput.equals("Button1")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.DOWN);
                        } else if (mdinput.equals("Button2")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.DOWN);
                        } else if (mdinput.equals("Button3")) {
                            seqItem = new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.DOWN);
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(editPanel, "Invalid sequence type", "Add Sequence Item", JOptionPane.WARNING_MESSAGE);
                        return;
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
                if (index == 0) {
                    JOptionPane.showMessageDialog(editPanel, "Sequence item cannot be moved further up!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(index);
                sequence.remove(index);
                sequence.add(index - 1, seqItem);
                seqList.setListData(sequence.toArray());
                seqList.setSelectedIndex(index - 1);
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
                if (index == sequence.size() - 1) {
                    JOptionPane.showMessageDialog(editPanel, "Sequence item cannot be moved further down!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(index);
                sequence.remove(index);
                sequence.add(index + 1, seqItem);
                seqList.setListData(sequence.toArray());
                seqList.setSelectedIndex(index + 1);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                original.setSelf(macro);
                Macro.updateList();
                JOptionPane.showMessageDialog(editPanel, "Macro saved!", "Save Macro", JOptionPane.INFORMATION_MESSAGE);
                pframe.setEnabled(true);
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pframe.setEnabled(true);
                frame.dispose();
            }
        });
        duplicateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seqList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(editPanel, "Please select a sequence item!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(seqList.getSelectedIndex());
                sequence.add(seqList.getSelectedIndex(), seqItem);
                int index = seqList.getSelectedIndex();
                seqList.setListData(sequence.toArray());
                seqList.setSelectedIndex(index);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (seqList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(editPanel, "Please select a sequence item!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                SequenceItem seqItem = sequence.get(seqList.getSelectedIndex());
                String name = seqItem.toString();

                SequenceItem newSeqItem = null;
                if (name.contains("Delay: ")) {
                    int ans;
                    try {
                        ans = Integer.parseInt((String) JOptionPane.showInputDialog(editPanel, "Input delay length (milliseconds)",
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
                    newSeqItem = new DelayItem(ans);
                } else if (name.contains("KeyUp: ")) {
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
                    newSeqItem = new KeyItem(keyUp, Mode.UP);
                } else if (name.contains("KeyDown: ")) {
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
                    newSeqItem = new KeyItem(keyDown, Mode.DOWN);
                } else if (name.contains("MouseUp: ")) {
                    String[] choices = {"Button1", "Button2", "Button3"};
                    String input = (String) JOptionPane.showInputDialog(editPanel, "Choose a button type",
                            "Button Item Type", JOptionPane.QUESTION_MESSAGE, null,
                            choices,
                            choices[0]);

                    if (input == null) {
                        return;
                    }
                    if (input.equals("Button1")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.UP);
                    } else if (input.equals("Button2")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.UP);
                    } else if (input.equals("Button3")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.UP);
                    }
                } else if (name.contains("MouseDown: ")) {
                    String[] choices = {"Button1", "Button2", "Button3"};
                    String input = (String) JOptionPane.showInputDialog(editPanel, "Choose a button type",
                            "Button Item Type", JOptionPane.QUESTION_MESSAGE, null,
                            choices,
                            choices[0]);

                    if (input == null) {
                        return;
                    }
                    if (input.equals("Button1")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.DOWN);
                    } else if (input.equals("Button2")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.DOWN);
                    } else if (input.equals("Button3")) {
                        newSeqItem = new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.DOWN);
                    }
                } else {
                    JOptionPane.showMessageDialog(editPanel, "Invalid sequence type", "Edit Sequence Item", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int index = seqList.getSelectedIndex();
                sequence.remove(index);
                sequence.add(index, newSeqItem);
                seqList.setListData(sequence.toArray());
            }
        });

        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                macro.setName(nameTextField.getText());
            }
        });
        bindTextField.addKeyListener(new KeyAdapter() {

            private void setBind(int code) {
                macro.setBind(code);
                String display = KeyEvent.getKeyText(macro.getBind());
                if (display.equals("Backspace")) {
                    bindTextField.setText("N/A");
                } else {
                    bindTextField.setText((display.contains("Unknown keyCode: ") ? "Unknown" : display));
                }
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
        singleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singleRadioButton.isSelected()) {
                    repeatRadioButton.setSelected(false);
                    repeatUntilStoppedRadioButton.setSelected(false);
                    iterationTextField.setEnabled(false);
                    plusButton.setEnabled(false);
                    minusButton.setEnabled(false);
                }
            }
        });
        repeatRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (repeatRadioButton.isSelected()) {
                    singleRadioButton.setSelected(false);
                    repeatUntilStoppedRadioButton.setSelected(false);
                    iterationTextField.setEnabled(true);
                    plusButton.setEnabled(true);
                    minusButton.setEnabled(true);
                    macro.setRun(Mode.REPEAT, 0);
                }
            }
        });
        repeatUntilStoppedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (repeatUntilStoppedRadioButton.isSelected()) {
                    repeatRadioButton.setSelected(false);
                    singleRadioButton.setSelected(false);
                    iterationTextField.setEnabled(false);
                    plusButton.setEnabled(false);
                    minusButton.setEnabled(false);
                    macro.setRun(Mode.REPEATUNTILSTOPPED, 0);
                }
            }
        });
        iterationTextField.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    iterationTextField.setText(String.valueOf(Math.max(Integer.parseInt(iterationTextField.getText()) - 1, 0)));
                } else if (e.getWheelRotation() > 0) {
                    iterationTextField.setText(String.valueOf(Math.min(Integer.parseInt(iterationTextField.getText()) + 1, Integer.MAX_VALUE)));
                }
                macro.setRun(Mode.REPEAT, Integer.parseInt(iterationTextField.getText()));
            }
        });
    }

    public void edit(MacroInfo macro, JFrame frame, JFrame pframe) {
        this.original = macro;
        this.macro = new MacroInfo(macro);
        this.frame = frame;
        this.pframe = pframe;
        nameTextField.setText(macro.toString());
        bindTextField.setText(
                (KeyEvent.getKeyText(macro.getBind()).contains("Unknown keyCode: ") ?
                        "N/A" : KeyEvent.getKeyText(macro.getBind())));
        iterationTextField.setText(String.valueOf(macro.getRunIter()));
        if (macro.getRunType() == Mode.SINGLE) {
            singleRadioButton.setSelected(true);
            repeatUntilStoppedRadioButton.setSelected(false);
            repeatRadioButton.setSelected(false);
        } else if (macro.getRunType() == Mode.REPEATUNTILSTOPPED) {
            singleRadioButton.setSelected(false);
            repeatUntilStoppedRadioButton.setSelected(true);
            repeatRadioButton.setSelected(false);
        } else if (macro.getRunType() == Mode.REPEAT) {
            singleRadioButton.setSelected(false);
            repeatUntilStoppedRadioButton.setSelected(false);
            repeatRadioButton.setSelected(true);
        }
        sequence = new LinkedList<>(this.macro.getSequence());
        seqList.setListData(sequence.toArray());
    }
}
