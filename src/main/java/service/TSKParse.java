package service;


import customer.exception.CustomException;
import model.DieCategory;
import model.OrientatioFlatAngleEnum;
import model.WaferMapData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * className: TSKData
 * package: service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 10:23
 */
@Service
public class TSKParse {
    private String operatorName;//0
    private String deviceName;//20
    private Short waferSize;//36 2
    private byte[] machineNo;//38 2
    private Integer XIndexingSize;//40 4
    private Integer YIndexingSize;//44 4
    private Short orientationFlatAngle;//48 2
    private String orientationFlatAngleName;
    private byte finalEditingMachineType;//50 1
    private byte mapVersion;//51 1
    private Integer mapDataAreaRowSize; //52 2
    private Integer mapDataAreaColSize;// 54 2
    private Integer mapDataForm;// 56 4
    private String waferId;// 60 21
    private byte nemberOfProbing;// 81 1
    private String lotNo;//82 18  need
    private Short cassetteNo;//100 2
    private Short slotNo;//102 2
    private byte XCoordinatesIncreaseDirection;//104 1
    private byte YCoordinatesIncreaseDirection;//105 1
    private byte refeDir;//106 1
    private byte reserved0;//107 1
    private int targetX;//108 4
    private int targetY;//112 4
    private short refpx;//116 2
    private short refpy;//118 2
    private byte probingSP;//120 1
    private byte probingDir;//121 1
    private short reserved1;//122 2
    private int distanceX;//124 4
    private int distanceY;//128 4
    private int coordinatorX;//132 4
    private int coordinatorY;//136 4
    private int firstDirX;//140 4
    private int firstDirY;//144 4

    private String startYear;//148 2
    private String startMonth;//150 2
    private String startDay;//152 2
    private String startHour;//154 2
    private String startMinute;//156 2
    private String startTimeReserved;//158 2
    private String endYear;//160 2
    private String endMonth;//162 2
    private String endDay;//164 2
    private String endHour;//166 2
    private String endMinute;//168 2
    private String endTimeReserved;//170 2
    private String loadYear;//172 2
    private String loadMonth;//174 2
    private String loadDay;//176 2
    private String loadHour;//178 2
    private String loadMinute;//180 2
    private String loadTimeReserved;//182 2
    private String unloadYear;//184 2
    private String unloadMonth;//186 2
    private String unloadDay;//188 2
    private String unloadHour;//190 2
    private String unloadMinute;//192 2
    private String unloadTimeReserved;//194 2
    private int machineNo1;//196 4
    private int machineNo2;//200 4
    private int specialChar;//204 4
    private byte testingEnd;//208 1
    private byte reserved2;//209 1
    private Short totalTestedDice;//210 2
    private Short totalPassDice;//212 2
    private Short totalFailDice;//214 2
    // 记录 die 测试数据起始指针
    private int dieSP;//216 4
    private int lineCategoryNo;//220 4
    private int lineCategoryAddr;//224 4
    private short configuration;//228 2
    private short maxMultiSite;//230 2
    private short maxCategories;//232 2
    private short reserved3;//234 2
    private List<WaferMapData> waferMapDataList = new ArrayList<>();

    private byte[] bufferhead1_20 = new byte[20];
    private byte[] bufferhead2_32 = new byte[32];
    private int bufferhead_total;
    private int bufferhead_pass;
    private int bufferhead_fail;
    private byte[] bufferhead3_44 = new byte[44];
    private byte[] bufferhead4_64 = new byte[64];

    List arryfirstbyte1_1 = new ArrayList<Byte>();
    List arryfirstbyte2_1 = new ArrayList<Byte>();
    List arrysecondbyte1_1 = new ArrayList<Byte>();
    List arrysecondbyte2_1 = new ArrayList<Byte>();
    List arrythirdbyte1_1 = new ArrayList<Byte>();
    List arrythirdbyte2_1 = new ArrayList<Byte>();

    Byte[] firstbyte1_1;
    Byte[] firstbyte2_1;
    ;
    Byte[] secondbyte1_1;
    Byte[] secondbyte2_1;
    ;
    Byte[] thirdbyte1_1;
    Byte[] thirdbyte2_1;

    public Short getTotalTestedDice() {
        return totalTestedDice;
    }

    public Short getTotalPassDice() {
        return totalPassDice;
    }

    public Short getTotalFailDice() {
        return totalFailDice;
    }

    public String getLotNo() {
        return lotNo;
    }

    public List<WaferMapData> getWaferMapDataList() {
        return waferMapDataList;
    }

    public String getWaferId() {
        return waferId;
    }

    public String getStartTime() {
        return String.join("-", "20" + startYear, startMonth, startDay) + " " + String.join(":", startHour, startMinute, "00");
    }

    public String getEndTime() {
        return String.join("-", "20" + endYear, endMonth, endDay) + " " + String.join(":", endHour, endMinute, "00");
    }


    public Short getOrientationFlatAngle() {
        return orientationFlatAngle;
    }

    public String getOrientationFlatAngleName() {
        return orientationFlatAngleName;
    }

    public TSKParse read(String file) {
        byte[] bytes = new byte[1024];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            dis.read(bytes, 0, 20);
            operatorName = new String(bytes).substring(0, 20).trim();
            dis.read(bytes, 0, 16);
            deviceName = new String(bytes).substring(0, 16).trim();

            waferSize = dis.readShort();

            dis.read(bytes, 0, 2);
            machineNo = new byte[2];
            System.arraycopy(bytes, 0, machineNo, 0, 2);
//            dis.readFully(bytes, 0, 4);
            XIndexingSize = dis.readInt();
            YIndexingSize = dis.readInt();

            orientationFlatAngle = dis.readShort();


            OrientatioFlatAngleEnum orientatioFlatAngleEnum = OrientatioFlatAngleEnum.getByOrientatioFlatAngle(Integer.valueOf(orientationFlatAngle));

            orientationFlatAngleName = orientatioFlatAngleEnum.name();

            finalEditingMachineType = dis.readByte();
            mapVersion = dis.readByte();

            mapDataAreaRowSize = dis.readUnsignedShort();// 记录行数
            mapDataAreaColSize = dis.readUnsignedShort();// 记录列数

//            dis.read(bytes, 0, 4);
            mapDataForm = dis.readInt();
//            mapDataForm = new String(bytes).substring(0, 16).trim();

            dis.read(bytes, 0, 21);
            waferId = new String(bytes).substring(0, 21).trim();
//            dis.read(bytes, 0, 1);
            nemberOfProbing = dis.readByte();
            dis.read(bytes, 0, 18);
            lotNo = new String(bytes).substring(0, 18).trim();

            cassetteNo = dis.readShort();
            slotNo = dis.readShort();
            XCoordinatesIncreaseDirection = dis.readByte();
            YCoordinatesIncreaseDirection = dis.readByte();
            refeDir = dis.readByte();
            reserved0 = dis.readByte();
            targetX = dis.readInt();
            targetY = dis.readInt();
            refpx = dis.readShort();
            refpy = dis.readShort();
            probingSP = dis.readByte();
            probingDir = dis.readByte();
            reserved1 = dis.readShort();
            distanceX = dis.readInt();
            distanceY = dis.readInt();
            coordinatorX = dis.readInt();
            coordinatorY = dis.readInt();
            firstDirX = dis.readInt();
            firstDirY = dis.readInt();

            dis.read(bytes, 0, 2);
            startYear = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            startMonth = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            startDay = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            startHour = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            startMinute = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            startTimeReserved = new String(bytes).substring(0, 2).trim();

            dis.read(bytes, 0, 2);
            endYear = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            endMonth = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            endDay = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            endHour = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            endMinute = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            endTimeReserved = new String(bytes).substring(0, 2).trim();

            dis.read(bytes, 0, 2);
            loadYear = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            loadMonth = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            loadDay = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            loadHour = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            loadMinute = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            loadTimeReserved = new String(bytes).substring(0, 2).trim();

            dis.read(bytes, 0, 2);
            unloadYear = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            unloadMonth = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            unloadDay = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            unloadHour = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            unloadMinute = new String(bytes).substring(0, 2).trim();
            dis.read(bytes, 0, 2);
            unloadTimeReserved = new String(bytes).substring(0, 2).trim();

            machineNo1 = dis.readInt();
            machineNo2 = dis.readInt();
            specialChar = dis.readInt();
            testingEnd = dis.readByte();
            reserved2 = dis.readByte();

            totalTestedDice = dis.readShort();
            totalPassDice = dis.readShort();
            totalFailDice = dis.readShort();
            //216 236
            dieSP = dis.readInt();
            lineCategoryNo = dis.readInt();
            lineCategoryAddr = dis.readInt();
            configuration = dis.readShort();
            maxMultiSite = dis.readShort();
            maxCategories = dis.readShort();
            reserved3 = dis.readShort();


            int sumDie = mapDataAreaRowSize * mapDataAreaColSize;

            for (int i = 0; i < sumDie; i++) {
                waferMapDataList.add(readDie(dis));
            }
            dis.read(bytes, 0, 20);
            System.arraycopy(bytes, 0, bufferhead1_20, 0, 20);

            dis.read(bytes, 0, 32);
            System.arraycopy(bytes, 0, bufferhead2_32, 0, 32);

            bufferhead_total = dis.readInt();
            bufferhead_pass = dis.readInt();
            bufferhead_fail = dis.readInt();
            dis.read(bytes, 0, 44);
            System.arraycopy(bytes, 0, bufferhead3_44, 0, 44);

            dis.read(bytes, 0, 64);
            System.arraycopy(bytes, 0, bufferhead4_64, 0, 64);
        } catch (FileNotFoundException e) {
            throw new CustomException("ERR-001", "file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private WaferMapData readDie(DataInputStream dis) throws IOException {
//FirstWord
        byte byte1 = dis.readByte();
        byte byte2 = dis.readByte();
        int XCord = (byte1 & 0x01) << 8 | byte2 & 0xff;
        int dieTestResult = (byte1 >> 6) & 0x03;
        //SecondWord
        byte byte3 = dis.readByte();
        byte byte4 = dis.readByte();
        int YCord = (byte1 & 0x01) << 8 | (byte4 & 0xff);
        int dieProperty = byte3 >> 6 & 0x3;
        int dummyData = byte3 >> 1 & 0x1;
        if ((byte3 >> 3 & 0x1) == 1) {
            YCord = YCord * (-1);
        }
        if ((byte3 >> 2 & 0x1) == 1) {
            XCord = XCord * (-1);
        }
        //ThirdWord
        byte byte5 = dis.readByte();
        byte byte6 = dis.readByte();
        int bin = byte6 & 0xff;

        arryfirstbyte1_1.add(byte1);
        arryfirstbyte2_1.add(byte2);
        arrysecondbyte1_1.add(byte3);
        arrysecondbyte2_1.add(byte4);
        arrythirdbyte1_1.add(byte5);
        arrythirdbyte2_1.add(byte6);

        WaferMapData die = new WaferMapData();

        switch (dieProperty) {
            case 0:
                if (dummyData == 1) {
                    die.setAttribute(DieCategory.SkipDie);
                } else {
                    die.setAttribute(DieCategory.SkipDie2);
                }
                break;
            case 1:
                switch (dieTestResult) {
                    case 0:
                        die.setAttribute(DieCategory.NoneDie);
                        break;
                    case 1:
                        die.setAttribute(DieCategory.PassDie);
                        die.setBin(bin);
                        break;
                    case 2:
                    case 3:
                        die.setAttribute(DieCategory.FailDie);
                        die.setBin(bin);
                        break;
                    default:
                        die.setAttribute(DieCategory.Unknow);
                        break;
                }
                break;
            case 2:
                die.setAttribute(DieCategory.MarkDie);
                break;
            default:
                die.setAttribute(DieCategory.Unknow);
                break;
        }
        die.setAddressXOfRecord(XCord);
        die.setAddressYOfRecord(YCord);
        return die;
    }

    public void buBian(TxtParse txtParse) {
        ///////------------------------------TXT图谱补边工作---------------------------//
        firstbyte1_1 = (Byte[]) this.arryfirstbyte1_1.toArray(new Byte[this.arryfirstbyte1_1.size()]);
        firstbyte2_1 = (Byte[]) this.arryfirstbyte2_1.toArray(new Byte[this.arryfirstbyte2_1.size()]);

        secondbyte1_1 = (Byte[]) this.arrysecondbyte1_1.toArray(new Byte[this.arrysecondbyte1_1.size()]);
        secondbyte2_1 = (Byte[]) this.arrysecondbyte2_1.toArray(new Byte[this.arrysecondbyte2_1.size()]);

        thirdbyte1_1 = (Byte[]) this.arrythirdbyte1_1.toArray(new Byte[this.arrythirdbyte1_1.size()]);
        thirdbyte2_1 = (Byte[]) this.arrythirdbyte2_1.toArray(new Byte[this.arrythirdbyte2_1.size()]);
        Object[][] TSKMap = new Object[this.mapDataAreaColSize][this.mapDataAreaRowSize];

        for (int i = 0; i < mapDataAreaColSize; i++) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {
                if ((secondbyte1_1[j + i * mapDataAreaRowSize] & 192) == 0)//Skip Die
                {
                    TSKMap[i][j] = ".";
                }

                if ((secondbyte1_1[j + i * mapDataAreaRowSize] & 192) == 128)//Mark Die
                {
                    TSKMap[i][j] = ".";
                }

                if ((secondbyte1_1[j + i * mapDataAreaRowSize] & 192) == 64)//Probe Die
                {
                    TSKMap[i][j] = "1";
                }

            }
        }

        int tskrowmin = 0, tskcolmin = 0, tskrowmax = 0, tskcolmax = 0;
        int flag = 0;
        for (int i = 0; i < mapDataAreaColSize; i++) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {
                if ((TSKMap[i][j].toString() != ".")) {
                    tskrowmin = i;
                    flag = 1;
                    break;

                }
            }
            if (flag == 1) {
                break;
            }
        }

        flag = 0;
        for (int i = mapDataAreaColSize - 1; i >= 0; i--) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {
                if ((TSKMap[i][j].toString() != ".")) {
                    tskrowmax = i;
                    flag = 1;
                    break;

                }

            }
            if (flag == 1) {
                break;

            }
        }

        flag = 0;
        for (int i = 0; i < mapDataAreaRowSize; i++) {
            for (int j = 0; j < mapDataAreaColSize; j++) {
                if ((TSKMap[j][i].toString() != ".")) {
                    tskcolmin = i;
                    flag = 1;

                }

            }
            if (flag == 1) {
                break;
            }
        }

        flag = 0;
        for (int i = mapDataAreaRowSize - 1; i >= 0; i--) {
            for (int j = 0; j < mapDataAreaColSize; j++) {
                if ((TSKMap[j][i].toString() != ".")) {
                    tskcolmax = i;
                    flag = 1;

                }

            }
            if (flag == 1) {
                break;
            }
        }

        Object[][] TxtNewMap = new Object[mapDataAreaColSize][mapDataAreaRowSize];
        for (int i = 0; i < mapDataAreaColSize; i++) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {

                TxtNewMap[i][j] = ".";
            }
        }

        for (int i = tskrowmin; i < tskrowmax + 1; i++) {
            for (int j = tskcolmin; j < tskcolmax + 1; j++) {

                TxtNewMap[i][j] = txtParse.TxtMap[i - tskrowmin][j - tskcolmin];
            }
        }

        if (txtParse.txtNewData == null) {
            txtParse.txtNewData = new ArrayList();
        } else {
            txtParse.txtNewData.clear();
        }

        for (int i = 0; i < mapDataAreaColSize; i++) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {

                txtParse.txtNewData.add(TxtNewMap[i][j].toString());

            }
        }
        ///////////////////////////对位点比对工作//////////////////////////////////////////////////

        int tskPass = 0;
        int tskFail = 0;
        int txtMark = 0;
        for (int i = 0; i < mapDataAreaColSize; i++) {
            for (int j = 0; j < mapDataAreaRowSize; j++) {
                if (TxtNewMap[i][j].toString().equals("1")) {
                    tskPass++;
                }

                if (TxtNewMap[i][j].toString().equals("X")) {
                    tskFail++;
                }

                if (TxtNewMap[i][j].toString().equals("R")) {
                    txtMark++;
                }

                if (TxtNewMap[i][j].toString().equals("R") && !TSKMap[i][j].toString().equals(".")) {
//                    if (MessageBox.Show("对位点不正确!", "确认", MessageBoxButtons.YesNo) == DialogResult.Yes)
//                    {
//                        Environment.Exit(0);
//                    }
                    // TODO
                    System.out.println("error and quit");

                }

            }
        }

//////////////////////////////PASS数比对///////////////////////////////////////

        if ((txtParse.txtPass + txtParse.txtFail) != (tskPass + tskFail)) {

//            if (MessageBox.Show("总颗数不匹配!", "确认", MessageBoxButtons.YesNo) == DialogResult.Yes)
//            {
//                Environment.Exit(0);
//            }
            System.out.println("error and quit");
        }
    }


    public void createNewTSK(TxtParse txtParse, String retTskUrl, String slotNo) {
        //------------------------------根据SINF生成新的TSK-MAP----------------------------//

        String fileName = retTskUrl+File.separator+"binaryfile.bin"; // 指定要创建的二进制文件的名称
/////--------------------Map版本为2，且无扩展信息TSK修改BIN信息代码-------------------////
//        if ((arry_1.Count == 0) && ((Convert.ToInt32(MapVersion_1) == 2)))
//        {
        for (int k = 0; k < mapDataAreaRowSize * mapDataAreaColSize; k++) {
            if (Objects.equals(txtParse.txtNewData.get(k).toString(), "."))//Skip Die
            {
                continue;

            } else {

                if (Objects.equals(txtParse.txtNewData.get(k).toString(), "1"))//sinf =pass 不改
                {
                    //  firstbyte1_1[k] = firstbyte1_1[k];
                    //  firstbyte2_1[k] = firstbyte2_1[k];
                    firstbyte1_1[k] = (byte) (firstbyte1_1[k] & 1);
                    firstbyte1_1[k] = (byte) (firstbyte1_1[k] | 0);//标记成untested
                    secondbyte1_1[k] = secondbyte1_1[k];
                    secondbyte2_1[k] = secondbyte2_1[k];
                    thirdbyte1_1[k] = thirdbyte1_1[k];
                    thirdbyte2_1[k] = thirdbyte2_1[k];

                }

                if (Objects.equals(txtParse.txtNewData.get(k).toString(), "X"))//sinf fail,需要改为fail属性，BIN也需要改
                {
                    firstbyte1_1[k] = (byte) (firstbyte1_1[k] & 1);
                    firstbyte1_1[k] = (byte) (firstbyte1_1[k] | 128);//标记成fail
                    firstbyte2_1[k] = firstbyte2_1[k];
                    secondbyte1_1[k] = secondbyte1_1[k];
                    secondbyte2_1[k] = secondbyte2_1[k];
                    thirdbyte1_1[k] = thirdbyte1_1[k];
                    thirdbyte2_1[k] = (byte) (thirdbyte2_1[k] & 192);
                    thirdbyte2_1[k] = (byte) (thirdbyte2_1[k] | 57);//换成想要的BIN58

                }


            }


        }
//        }
//
///////--------------------Map版本为2，且有扩展信息TSK修改BIN信息代码-------------------////
//        if ((arry_1.Count > 0) && ((Convert.ToInt32(MapVersion_1) == 2)))
//        {
//            for (int k = 0; k < row1_1 * col1_1; k++)
//            {
//                if (txtNewData[k].ToString() == ".")//Skip Die
//                {
//                    continue;
//
//                }
//
//                else
//                {
//
//                    if (txtNewData[k].ToString() == "1")//sinf =pass 不改
//                    {
//                        firstbyte1_1[k] = Convert.ToByte(firstbyte1_1[k] & 1);
//                        firstbyte1_1[k] = Convert.ToByte(firstbyte1_1[k] | 0);//标记成untested
//
//                        arry_1[4 * k] = arry_1[4 * k];
//                        arry_1[4 * k + 1] = arry_1[4 * k + 1];
//                        arry_1[4 * k + 2] = arry_1[4 * k + 2];
//                        arry_1[4 * k + 3] = arry_1[4 * k + 3];
//                    }
//
//                    if (txtNewData[k].ToString() == "X")//sinf fail,需要改为fail属性，BIN也需要改
//                    {
//                        firstbyte1_1[k] = Convert.ToByte(firstbyte1_1[k] & 1);
//                        firstbyte1_1[k] = Convert.ToByte(firstbyte1_1[k] | 128);//标记成fail
//
//                        thirdbyte1_1[k] = thirdbyte1_1[k];
//                        thirdbyte2_1[k] = Convert.ToByte(thirdbyte2_1[k] & 192);
//                        thirdbyte2_1[k] = Convert.ToByte(thirdbyte2_1[k] | 57);//换成想要的BIN58
//
//
//                        arry_1[4 * k] = arry_1[4 * k];//sitenum
//                        // arry_1[4 * k + 1] = arry_1[4 * k + 1];//cate
//                        arry_1[4 * k + 1] = Convert.ToByte(Convert.ToByte(arry_1[4 * k + 1]) & 192);
//                        arry_1[4 * k + 1] = Convert.ToByte(Convert.ToByte(arry_1[4 * k + 1]) | 57);//换成想要的BIN58
//
//
//                        arry_1[4 * k + 2] = arry_1[4 * k + 2];
//                        arry_1[4 * k + 3] = arry_1[4 * k + 3];
//
//                    }
//
//
//
//                }
//
//
//            }
//        }
//
//


//----------------------------TSK修改BIN信息-----------------------------------------------------

        try (DataOutputStream bw = new DataOutputStream(new FileOutputStream(fileName))) {

            System.out.println("Binary file created and data written successfully.");
            String str = String.format("%-20s", this.operatorName);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 20);

            str = String.format("%-16s", this.deviceName);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 16);

            byte[] buf;
//WaferSize
            bw.writeShort(this.waferSize);
//MachineNo
//            str = String.format("%-2s", this.machineNo);
            bw.write(this.machineNo);
//IndexSizeX
            bw.writeInt(this.XIndexingSize);
//IndexSizeY
            bw.writeInt(this.YIndexingSize);
//FlatDir
            // TODO
//            this.Reverse(ref FlatDir_1);
            bw.writeShort(this.orientationFlatAngle);
//MachineType
            bw.write(finalEditingMachineType);
//MapVersion
            bw.write(mapVersion);
//Row
            //todo
            bw.writeShort(mapDataAreaRowSize);
//            bw.write(mapDataAreaRowSize[0]);
//Col
            bw.writeShort(mapDataAreaColSize);
//            bw.write(col_1[0]);
//MapDataForm
            bw.writeInt(mapDataForm);

//NewWaferID
            str = String.format("%-21s", this.waferId);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 21);
//ProbingNo
            bw.write(nemberOfProbing);

//NewLotNo
            str = String.format("%-18s", this.lotNo);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 18);

            //TODO
            bw.writeShort(this.cassetteNo);
////SN
//            //Slot NO
            bw.writeShort(Integer.parseInt(slotNo));
////Idex
            bw.write(this.XCoordinatesIncreaseDirection);
////Idey
            bw.write(this.YCoordinatesIncreaseDirection);
////Rdsp
            bw.write(this.refeDir);
////Reserved1
            bw.write(this.reserved0);
////Tdpx
            bw.writeInt(this.targetX);
////Tdpy
            bw.writeInt(this.targetY);
//
////Rdcx
            bw.writeShort(this.refpx);
////Rdcy
            bw.writeShort(this.refpy);
////Psps
            bw.write(this.probingSP);
////Pds
            bw.write(this.probingDir);
////Reserved2
            bw.writeShort(this.reserved1);
////DistanceX
            bw.writeInt(this.distanceX);
////DistanceY
            bw.writeInt(this.distanceY);
//
////CoordinatorX
            bw.writeInt(coordinatorX);
////CoordinatorY
            bw.writeInt(coordinatorY);
////Fdcx
            bw.writeInt(firstDirX);
////Fdxy
            bw.writeInt(firstDirY);
////WTSTIME
            str = String.format("%-2s", this.startYear);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.startMonth);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.startDay);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.startHour);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.startMinute);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.startTimeReserved);
            bw.write(new byte[2]);
//            bw.write(str.getBytes(StandardCharsets.US_ASCII),0,2);

            ////WTETIME
            str = String.format("%-2s", this.endYear);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.endMonth);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.endDay);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.endHour);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.endMinute);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.endTimeReserved);
            bw.write(new byte[2]);
//            bw.write(str.getBytes(StandardCharsets.US_ASCII),0,2);

////WLTIME
            str = String.format("%-2s", this.loadYear);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.loadMonth);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.loadDay);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.loadHour);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.loadMinute);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.loadTimeReserved);
            bw.write(new byte[2]);
//            bw.write(str.getBytes(StandardCharsets.US_ASCII),0,2);
////WULT
            str = String.format("%-2s", this.unloadYear);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.unloadMonth);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.unloadDay);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.unloadHour);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.unloadMinute);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            str = String.format("%-2s", this.unloadTimeReserved);
            if (StringUtils.isEmpty(this.unloadTimeReserved)) {
                bw.write(new byte[2]);
            } else {
                bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 2);
            }
////MachineNo1
            bw.writeInt(this.machineNo1);
////MachineNo2
            bw.writeInt(this.machineNo2);
////Specialchar
            bw.writeInt(specialChar);
////TestEndInfo
            bw.write(testingEnd);
////Reserved3
            bw.write(reserved2);
////Totaldice
////buf = BitConverter.GetBytes((short)(tskFail+tskPass));-----20221128
//            buf = BitConverter.GetBytes((short) (tskFail));
//            this.Reverse(ref buf);
//            bw.write(buf, 0, 2);
            bw.writeShort(txtParse.txtPass + txtParse.txtFail);
////TotalPdice
//// bw.write(TotalPdice_1);
//            buf = BitConverter.GetBytes((short) (0));
//            this.Reverse(ref buf);
            bw.writeShort(txtParse.txtFail);
////TotalFdice
//            buf = BitConverter.GetBytes((short) (tskFail));
//            this.Reverse(ref buf);
            bw.writeShort(txtParse.txtPass);
//// bw.write(TotalFdice_1);
////DIAdress
            bw.writeInt(dieSP);
////Numbercategory
            bw.writeInt(lineCategoryNo);
////Linecategory
            bw.writeInt(lineCategoryAddr);
////mapconfig
            bw.writeShort(configuration);
////mmsite
            bw.writeShort(maxMultiSite);
////mcategory
            bw.writeShort(maxCategories);
////Reserved4
            bw.writeShort(reserved3);

            for (int k = 0; k < mapDataAreaRowSize * mapDataAreaColSize; k++) {
                bw.write(firstbyte1_1[k]);
                bw.write(firstbyte2_1[k]);
                bw.write(secondbyte1_1[k]);
                bw.write(secondbyte2_1[k]);
                bw.write(thirdbyte1_1[k]);
                bw.write(thirdbyte2_1[k]);


            }
            bw.write(bufferhead1_20);
            bw.write(bufferhead2_32);
// buf = BitConverter.GetBytes((int)(tskFail + tskPass));////不能写total

            bw.writeInt(bufferhead_total);
            bw.writeInt(bufferhead_pass);
            bw.writeInt(bufferhead_fail);

            bw.write(bufferhead3_44);
            bw.write(bufferhead4_64);


//////扩展信息 mapversion2.3//////////////////////////////////
//            foreach( byte obj in arry_1)
//            {
//                bw.write(obj);
//
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
//Operator Size20


//        if (MessageBox.Show("转换成功，是否打开?", "确定", MessageBoxButtons.YesNo) == DialogResult.Yes) {
//            Process.Start("D:\\MERGE\\");
//        }
    }
}
