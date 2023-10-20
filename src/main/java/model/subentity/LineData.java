package model.subentity;

import java.util.List;

public class LineData {
    private int FirstAddressXOfRecord;
    private int FirstAddressYOfRecord;
    private short NoOfDies;
    private List<DieData> lines;
    private List<Character> map;


    public List<Character> getMap() {
        return map;
    }

    public void setMap(List<Character> map) {
        this.map = map;
    }


    public int getFirstAddressXOfRecord() {
        return FirstAddressXOfRecord;
    }

    public void setFirstAddressXOfRecord(int firstAddressXOfRecord) {
        FirstAddressXOfRecord = firstAddressXOfRecord;
    }

    public int getFirstAddressYOfRecord() {
        return FirstAddressYOfRecord;
    }

    public void setFirstAddressYOfRecord(int firstAddressYOfRecord) {
        FirstAddressYOfRecord = firstAddressYOfRecord;
    }

    public short getNoOfDies() {
        return NoOfDies;
    }

    public void setNoOfDies(short noOfDies) {
        NoOfDies = noOfDies;
    }

    public List<DieData> getLines() {
        return lines;
    }

    public void setLines(List<DieData> lines) {
        this.lines = lines;
    }
}
