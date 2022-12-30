package src.macro;

import src.macro.seqitems.DelayItem;
import src.macro.seqitems.KeyItem;
import src.macro.seqitems.MouseItem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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
    private JFrame frame;

    public MacroForm() {
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = JOptionPane.showInputDialog("Enter a name:");
                if (path == null) {
                    return;
                }
                if (path.equals("")) {
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
                editMac.edit(selected, editFrame, frame);
                editFrame.pack();
                editFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                editFrame.setResizable(false);
                editFrame.setVisible(true);
                frame.setEnabled(false);
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
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                JFileChooser fileChooser = new JFileChooser(".txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(mainPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    writeFile(selectedFile);
                }
            }
        });
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                JFileChooser fileChooser = new JFileChooser(".txt");
                fileChooser.setFileFilter(filter);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(mainPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    readFile(selectedFile);
                }
            }
        });
    }

    private void readFile(File selectedFile) {
        try {
            Scanner reader = new Scanner(selectedFile);
            MacroInfo info = new MacroInfo(selectedFile.getName());
            while (reader.hasNextLine()) {
                String seqItem = reader.next();
                SequenceItem newSeqItem = null;
                if (seqItem.startsWith("Delay: ")) {
                    newSeqItem = new DelayItem(Integer.parseInt(seqItem.replace("Delay: ", "")));
                } else if (seqItem.startsWith("KeyUp: ")) {
                    newSeqItem = new KeyItem(Integer.parseInt(seqItem.replace("KeyUp: ", "")), Mode.UP);
                } else if (seqItem.startsWith("KeyDown: ")) {
                    newSeqItem = new KeyItem(Integer.parseInt(seqItem.replace("KeyDown: ", "")), Mode.DOWN);
                } else if (seqItem.startsWith("MouseUp: ")) {
                    newSeqItem = new MouseItem(Integer.parseInt(seqItem.replace("MouseUp: ", "")), Mode.UP);
                } else if (seqItem.startsWith("MouseDown: ")) {
                    newSeqItem = new MouseItem(Integer.parseInt(seqItem.replace("MouseDown: ", "")), Mode.DOWN);
                } else {
                    throw new Exception("Invalid value");
                }
                info.appendSeqItem(newSeqItem);
            }
            macros.add(info);
            macrosList.setListData(macros.toArray());
            reader.close();
            JOptionPane.showMessageDialog(mainPanel, "Successfully imported macro!", "Import Macro", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainPanel, "Error occurred during importing macro!", "Import Macro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void writeFile(File selectedFile) {
        try {
            FileWriter writer = new FileWriter(selectedFile);
            for(SequenceItem seqItem : macros.get(macrosList.getSelectedIndex()).getSequence()){
                writer.write(seqItem.toString());
            }
            writer.close();
            JOptionPane.showMessageDialog(mainPanel, "Successfully exported macro!", "Export Macro", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainPanel, "Error occurred during exporting macro!", "Export Macro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setParent(JFrame frame) {
        this.frame = frame;
    }
}
