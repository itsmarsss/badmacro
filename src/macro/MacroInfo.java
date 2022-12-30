package src.macro;

import java.util.LinkedList;

public class MacroInfo {
    private LinkedList<SequenceItem> sequence = new LinkedList<>();
    private String name;
    private int bind;

    public MacroInfo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return bind;
    }

    public LinkedList<SequenceItem> getSequence() {
        return sequence;
    }

    public MacroInfo addSeqItem(int index, SequenceItem seqItem) {
        sequence.add(index, seqItem);
        return this;
    }

    public MacroInfo appendSeqItem(SequenceItem seqItem) {
        sequence.addLast(seqItem);
        return this;
    }

    public String toString() {
        return name;
    }

    public void removeSeqItem(int index) {
        sequence.remove(index);
    }

    public void setSequence(LinkedList<SequenceItem> sequence) {
        this.sequence = sequence;
    }
}
