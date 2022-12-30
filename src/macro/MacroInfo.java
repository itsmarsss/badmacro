package src.macro;

import java.util.LinkedList;

public class MacroInfo {
    private LinkedList<SequenceItem> sequence = new LinkedList<>();
    private String name;
    public MacroInfo(String name) {
        this.name = name;
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
}
