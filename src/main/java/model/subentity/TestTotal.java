package model.subentity;

import java.time.LocalDateTime;

public class TestTotal {
    private short PassTotal;


    private short FailTotal;
    private short TestTotal;
    private LocalDateTime LotStartTime;
    private LocalDateTime LotEndTime;

    public short getPassTotal() {
        return PassTotal;
    }

    public void setPassTotal(short passTotal) {
        PassTotal = passTotal;
    }

    public short getFailTotal() {
        return FailTotal;
    }

    public void setFailTotal(short failTotal) {
        FailTotal = failTotal;
    }

    public short getTestTotal() {
        return TestTotal;
    }

    public void setTestTotal(short testTotal) {
        TestTotal = testTotal;
    }

    public LocalDateTime getLotStartTime() {
        return LotStartTime;
    }

    public void setLotStartTime(LocalDateTime lotStartTime) {
        LotStartTime = lotStartTime;
    }

    public LocalDateTime getLotEndTime() {
        return LotEndTime;
    }

    public void setLotEndTime(LocalDateTime lotEndTime) {
        LotEndTime = lotEndTime;
    }

}
