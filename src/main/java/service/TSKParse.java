package service;


import customer.exception.CustomException;
import model.DieCategory;
import model.OrientatioFlatAngleEnum;
import model.WaferMapData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    //20 bits
    private String operatorName;
    //16 bits
    private String deviceName;
    private Short waferSize;
    private byte[] machineNo;
    private Integer XIndexingSize;
    private Integer YIndexingSize;


    private Short orientationFlatAngle;
    private String orientationFlatAngleName;
    private byte finalEditingMachineType;
    private byte mapVersion;
    private Integer mapDataAreaRowSize;
    private Integer mapDataAreaColSize;
    private Integer mapDataForm;
    //21 bits
    private String waferId;
    private byte nemberOfProbing;
    //18 bits
    private String lotNo;
    private Short cassetteNo;
    private Short slotNo;
    private byte XCoordinatesIncreaseDirection;
    private byte YCoordinatesIncreaseDirection;
    private byte refeDir;
    private byte reserved0;
    private int targetX;
    private int targetY;
    private short refpx;
    private short refpy;
    private byte probingSP;
    private byte probingDir;
    private short reserved1;
    private int distanceX;
    private int distanceY;
    private int coordinatorX;
    private int coordinatorY;
    private int firstDirX;
    private int firstDirY;

    private String startYear;
    private String startMonth;
    private String startDay;
    private String startHour;
    private String startMinute;
    private String startTimeReserved;
    private String endYear;
    private String endMonth;
    private String endDay;
    private String endHour;
    private String endMinute;
    private String endTimeReserved;
    private String loadYear;
    private String loadMonth;
    private String loadDay;
    private String loadHour;
    private String loadMinute;
    private String loadTimeReserved;
    private String unloadYear;
    private String unloadMonth;
    private String unloadDay;
    private String unloadHour;
    private String unloadMinute;
    private String unloadTimeReserved;
    private int machineNo1;
    private int machineNo2;
    private int specialChar;
    private byte testingEnd;
    private byte reserved2;
    private Short totalTestedDice;
    private Short totalPassDice;
    private Short totalFailDice;
    // 记录 die 测试数据起始指针
    private int dieSP;
    private int lineCategoryNo;
    private int lineCategoryAddr;
    private short configuration;
    private short maxMultiSite;
    private short maxCategories;
    private short reserved3;
    private List<WaferMapData> waferMapDataList = new ArrayList<>();

    private byte[] bufferhead1_20 = new byte[20];
    private byte[] bufferhead2_32 = new byte[32];
    private int bufferhead_total;
    private int bufferhead_pass;
    private int bufferhead_fail;
    private byte[] bufferhead3_44 = new byte[44];
    private byte[] bufferhead4_64 = new byte[64];

    List<Byte> extendResultPerDieList = new ArrayList<Byte>();

    //die的First Word
    List<Byte> arryfirstbyte1_1 = new ArrayList<Byte>();
    List<Byte> arryfirstbyte2_1 = new ArrayList<Byte>();
    //die的Second Word
    List<Byte> arrysecondbyte1_1 = new ArrayList<Byte>();
    List<Byte> arrysecondbyte2_1 = new ArrayList<Byte>();
    //die的Second Word
    List<Byte> arrythirdbyte1_1 = new ArrayList<Byte>();
    List<Byte> arrythirdbyte2_1 = new ArrayList<Byte>();

    Byte[] firstbyte1_1;
    Byte[] firstbyte2_1;
    Byte[] secondbyte1_1;
    Byte[] secondbyte2_1;
    Byte[] thirdbyte1_1;
    Byte[] thirdbyte2_1;

    public Short getOrientationFlatAngle() {
        return orientationFlatAngle;
    }

    public TSKParse read(String file) {
        byte[] bytes = new byte[1024];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            //解析tsk head info
            dis.read(bytes, 0, 20);
            operatorName = new String(bytes).substring(0, 20).trim();
            dis.read(bytes, 0, 16);
            deviceName = new String(bytes).substring(0, 16).trim();

            waferSize = dis.readShort();

            dis.read(bytes, 0, 2);
            machineNo = new byte[2];
            System.arraycopy(bytes, 0, machineNo, 0, 2);
            XIndexingSize = dis.readInt();
            YIndexingSize = dis.readInt();

            orientationFlatAngle = dis.readShort();


            OrientatioFlatAngleEnum orientatioFlatAngleEnum = OrientatioFlatAngleEnum.getByOrientatioFlatAngle(Integer.valueOf(orientationFlatAngle));

            orientationFlatAngleName = orientatioFlatAngleEnum.name();

            finalEditingMachineType = dis.readByte();
            mapVersion = dis.readByte();

            mapDataAreaRowSize = dis.readUnsignedShort();// 记录行数
            mapDataAreaColSize = dis.readUnsignedShort();// 记录列数

            mapDataForm = dis.readInt();

            dis.read(bytes, 0, 21);
            waferId = new String(bytes).substring(0, 21).trim();
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

            dieSP = dis.readInt();
            lineCategoryNo = dis.readInt();
            lineCategoryAddr = dis.readInt();
            configuration = dis.readShort();
            maxMultiSite = dis.readShort();
            maxCategories = dis.readShort();
            reserved3 = dis.readShort();


            int sumDie = mapDataAreaRowSize * mapDataAreaColSize;

            //解析每颗die
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

            byte buffer;
            // 持续读取数据直到文件末尾
            while (dis.available() > 0) {
                // 处理读取到的数据，例如输出到控制台
                buffer = dis.readByte();
                extendResultPerDieList.add(buffer);
            }

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
                    throw new CustomException("ERR-002", "对位点不匹配");
                }

            }
        }

//////////////////////////////PASS数比对///////////////////////////////////////

        if ((txtParse.txtPass + txtParse.txtFail) != (tskPass + tskFail)) {
            throw new CustomException("ERR-003", "总颗数不匹配!");
        }
    }


    public void createNewTSK(TxtParse txtParse, String retTskUrl, String slotNo) {
        //------------------------------根据SINF生成新的TSK-MAP----------------------------//

        String fileName = retTskUrl + File.separator + txtParse.txtWaferID;
        //--------------------Map版本为2，且无扩展信息TSK修改BIN信息代码-------------------//
        if (mapVersion == 2) {
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
        }

        //----------------------------TSK修改BIN信息-----------------------------------------------------
        try (DataOutputStream bw = new DataOutputStream(Files.newOutputStream(Paths.get(fileName)))) {

            System.out.println("Binary file created and data written successfully.");
            String str = String.format("%-20s", this.operatorName);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 20);

            str = String.format("%-16s", this.deviceName);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 16);

            //WaferSize
            bw.writeShort(this.waferSize);
            //MachineNo
            bw.write(this.machineNo);
            //IndexSizeX
            bw.writeInt(this.XIndexingSize);
            //IndexSizeY
            bw.writeInt(this.YIndexingSize);
            //FlatDir
            bw.writeShort(this.orientationFlatAngle);
            //MachineType
            bw.write(finalEditingMachineType);
            //MapVersion
            bw.write(mapVersion);
            //Row
            bw.writeShort(mapDataAreaRowSize);
            //Col
            bw.writeShort(mapDataAreaColSize);
            //MapDataForm
            bw.writeInt(mapDataForm);
            //NewWaferID
            str = String.format("%-21s", txtParse.txtWaferID);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 21);
            //ProbingNo
            bw.write(nemberOfProbing);

            //NewLotNo
            str = String.format("%-18s", txtParse.txtLot);
            bw.write(str.getBytes(StandardCharsets.US_ASCII), 0, 18);

            bw.writeShort(this.cassetteNo);
            //SN
            //Slot NO
            bw.writeShort(Short.parseShort(slotNo));
            //Idex
            bw.write(this.XCoordinatesIncreaseDirection);
            //Idey
            bw.write(this.YCoordinatesIncreaseDirection);
            //Rdsp
            bw.write(this.refeDir);
            //Reserved1
            bw.write(this.reserved0);
            //Tdpx
            bw.writeInt(this.targetX);
            //Tdpy
            bw.writeInt(this.targetY);

            //Rdcx
            bw.writeShort(this.refpx);
            //Rdcy
            bw.writeShort(this.refpy);
            //Psps
            bw.write(this.probingSP);
            //Pds
            bw.write(this.probingDir);
            //Reserved2
            bw.writeShort(this.reserved1);
            //DistanceX
            bw.writeInt(this.distanceX);
            //DistanceY
            bw.writeInt(this.distanceY);

            //CoordinatorX
            bw.writeInt(coordinatorX);
            //CoordinatorY
            bw.writeInt(coordinatorY);
            //Fdcx
            bw.writeInt(firstDirX);
            //Fdxy
            bw.writeInt(firstDirY);
            //WTSTIME
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

            //WTETIME
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

            //WLTIME
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
            //WULT
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
            //MachineNo1
            bw.writeInt(this.machineNo1);
            //MachineNo2
            bw.writeInt(this.machineNo2);
            //Specialchar
            bw.writeInt(specialChar);
            //TestEndInfo
            bw.write(testingEnd);
            //Reserved3
            bw.write(reserved2);
            //TotalDice
            short totalDice = (short) (txtParse.txtPass + txtParse.txtFail);
            bw.writeShort(totalDice);
            //TotalPassDice
            bw.writeShort(0);
            //TotalFailDice
            bw.writeShort(txtParse.txtFail);
            //DIAdress
            bw.writeInt(dieSP);
            //Numbercategory
            bw.writeInt(lineCategoryNo);
            //Linecategory
            bw.writeInt(lineCategoryAddr);
            //mapconfig
            bw.writeShort(configuration);
            //mmsite
            bw.writeShort(maxMultiSite);
            //mcategory
            bw.writeShort(maxCategories);
            //Reserved4
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

            //Total、pass、fail dies
//            bw.write(int2bytes((int) (txtParse.txtPass + txtParse.txtFail), ByteOrder.BIG_ENDIAN),0,4);
//            bw.write(int2bytes(0, ByteOrder.BIG_ENDIAN),0,4);
//            bw.write(int2bytes((int) (txtParse.txtFail), ByteOrder.BIG_ENDIAN),0,4);
            bw.writeInt(txtParse.txtPass + txtParse.txtFail);
            bw.writeInt(0);
            bw.writeInt(txtParse.txtFail);

            bw.write(bufferhead3_44);
            bw.write(bufferhead4_64);


////扩展信息 mapversion2.3//////////////////////////////////
            for (Byte buffer : extendResultPerDieList) {
                bw.write(buffer);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
//Operator Size20


//        if (MessageBox.Show("转换成功，是否打开?", "确定", MessageBoxButtons.YesNo) == DialogResult.Yes) {
//            Process.Start("D:\\MERGE\\");
//        }
    }

    public static byte[] int2bytes(int intVal, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.asIntBuffer().put(intVal);
        return buffer.array();
    }
}
