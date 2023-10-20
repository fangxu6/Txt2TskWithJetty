package model;

/**
 * className: WaferMapDate
 * package: model
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/30 14:32
 */
public class WaferMapData {


    private DieCategory attribute = DieCategory.Unknow;
    private int bin=-1;
    private int addressXOfRecord=0;
    private int addressYOfRecord=0;

    public DieCategory getAttribute() {
        return attribute;
    }

    public void setAttribute(DieCategory attribute) {
        this.attribute = attribute;
    }

    public int getBin() {
        return bin;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    public int getAddressXOfRecord() {
        return addressXOfRecord;
    }

    public void setAddressXOfRecord(int addressXOfRecord) {
        this.addressXOfRecord = addressXOfRecord;
    }

    public int getAddressYOfRecord() {
        return addressYOfRecord;
    }

    public void setAddressYOfRecord(int addressYOfRecord) {
        this.addressYOfRecord = addressYOfRecord;
    }
}
