package model;

/**
 * className:OrientatioFlatAngle
 * package:model
 * Description:
 *
 * @Date:2022/12/2621:51
 * @Author:fangxu6@gmail.com
 */
public enum OrientatioFlatAngleEnum {
    U(0),R(90),D(180),L(270);
    private int value;
    private OrientatioFlatAngleEnum(int value) {
        this.value = value;
    }
        public static OrientatioFlatAngleEnum getByOrientatioFlatAngle(Integer angel){
            if(angel==null) {
                return null;
            }
            for (OrientatioFlatAngleEnum orientatioFlatAngleEnum : OrientatioFlatAngleEnum.values()) {
                if (angel == orientatioFlatAngleEnum.value) {
                    return orientatioFlatAngleEnum;
                }
            }
            return null;
        }

}
