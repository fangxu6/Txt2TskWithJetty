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


    public void setAttribute(DieCategory attribute) {
        this.attribute = attribute;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    public void setAddressXOfRecord(int addressXOfRecord) {
        this.addressXOfRecord = addressXOfRecord;
    }

    public void setAddressYOfRecord(int addressYOfRecord) {
        this.addressYOfRecord = addressYOfRecord;
    }
}
