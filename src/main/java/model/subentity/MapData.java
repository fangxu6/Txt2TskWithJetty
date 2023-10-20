package model.subentity;

import java.util.List;
import java.util.Map;

public class MapData {
    private short NoOfRecords;
    private int  InitialDieDistanceX;
    private int InitialDieDistanceY;
    private List<LineData> records;
    private Map<Integer,Integer> binMap;
    private List<BinData> binData;

    public short getNoOfRecords() {
        return NoOfRecords;
    }

    public void setNoOfRecords(short noOfRecords) {
        NoOfRecords = noOfRecords;
    }

    public int getInitialDieDistanceX() {
        return InitialDieDistanceX;
    }

    public void setInitialDieDistanceX(int initialDieDistanceX) {
        InitialDieDistanceX = initialDieDistanceX;
    }

    public int getInitialDieDistanceY() {
        return InitialDieDistanceY;
    }

    public void setInitialDieDistanceY(int initialDieDistanceY) {
        InitialDieDistanceY = initialDieDistanceY;
    }

    public List<LineData> getRecords() {
        return records;
    }

    public void setRecords(List<LineData> records) {
        this.records = records;
    }

    public Map<Integer, Integer> getBinMap() {
        return binMap;
    }

    public void setBinMap(Map<Integer, Integer> binMap) {
        this.binMap = binMap;
    }

    public List<BinData> getBinData() {
        return binData;
    }

    public void setBinData(List<BinData> binData) {
        this.binData = binData;
    }
}
