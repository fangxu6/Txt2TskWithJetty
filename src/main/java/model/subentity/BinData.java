package model.subentity;

public class BinData {
    byte binBytes;
    Integer binNum;
    String dieAttribute;

    public BinData(byte binBytes, Integer binNum) {
        this.binBytes = binBytes;
        this.binNum = binNum;
    }

    public void setDieAttribute(String dieAttribute) {
        this.dieAttribute = dieAttribute;
    }

    public byte getBinBytes() {
        return binBytes;
    }

    public Integer getBinNum() {
        return binNum;
    }

    public String getDieAttribute() {
        return dieAttribute;
    }
}