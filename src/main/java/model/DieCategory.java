package model;

/**
 * className: DieCategory
 * package: model
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/17 15:39
 */
public enum DieCategory {
    Unknow(1), PassDie(2), FailDie(4), SkipDie(8), SkipDie2(9), NoneDie(16), MarkDie(32),

    TIRefPass(64), TIRefFail(128);

    private final int value;

    private DieCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
