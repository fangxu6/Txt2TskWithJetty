package service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * className: TxtParse
 * package: service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 20:08
 */
@Service
public class TxtParse {
    int txtPass = 0;
    int txtFail = 0;
    List txtData;
    List degTxtData = new ArrayList();
    List txtNewData;
    Object[][] TxtMap;

    //-----Sinf 头文件----//////
    String txtDevice;
    String txtLot;
    int txtSlot;
    String txtWaferID;
    String txtFlat;
    int txtRowct = 0;
    int txtColct = 0;


    public TxtParse read(String file) {
        if (this.txtData == null) {
            this.txtData = new ArrayList();
        } else {
            this.txtData.clear();
        }

        try (FileReader txt_1 = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(txt_1)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 处理每一行文本内容
                this.Parse(line);
            }

            //解析txt文件获得图谱的行数和列数
            if (txtRowct > 0 && txtColct > 0) {
            } else {
                // MessageBox.Show("SINF格式不正确!");
//                if (MessageBox.Show("TXT格式不正确!", "确认", MessageBoxButtons.YesNo) == DialogResult.Yes) {
//                    Environment.Exit(0);
//                }
                System.out.println("error");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void Parse(String line) {
        try {
            if (line.indexOf(':') >= 0) {
                String[] strs = line.split(":");

                switch (strs[0]) {
                    case "Device":
                        this.txtDevice = strs[1].trim();
                        break;
                    case "Lot NO":
                        this.txtLot = strs[1].trim();
                        break;
                    case "Slot NO":
                        this.txtSlot = Integer.valueOf(strs[1].trim());
                        break;
                    case "Wafer ID":
                        this.txtWaferID = strs[1].trim();
                        break;
                    case "Flat Dir":
                        this.txtFlat = strs[1].trim();
                        break;
                    case "Wafer Row Number":
                        this.txtRowct = Integer.valueOf(strs[1].trim());
                        break;
                    case "Wafer Column Number":
                        this.txtColct = Integer.valueOf(strs[1].trim());
                        break;
                    case "Pass Die":
                        this.txtPass = Integer.valueOf(strs[1].trim());
                        break;
                    case "Fail Die":
                        this.txtFail = Integer.valueOf(strs[1].trim());
                        break;
                }
            } else {
                this.ParseDies(line);
            }
        } catch (Exception ee) {
            throw ee;
        }
    }

    private void ParseDies(String s) {
        for (int i = 0; i < s.length(); i++) {
            txtData.add(s.charAt(i));
        }
    }

    // txt图谱翻转，和tsk图谱一致
    public void rotate(int degree) {
        int count = txtColct * txtRowct;

        for (int i = 0; i < count; i++) {
            degTxtData.add(".");
        }

        if (degree == 180)
        {
            int x = -1, y = -1, xr = -1, yr = -1;

            for (int i = 0; i < count; i++) {
                try {
                    // 计算 x,y 坐标
                    x = i % txtColct;
                    y = i / txtColct;

                    xr = (txtColct) - 1 - x;
                    yr = (txtRowct) - 1 - y;

                    degTxtData.set(yr * txtColct + xr, txtData.get(i));
                } catch (Exception ee) {
//                    String msg = ee.Message;
                }
            }


        } else if (degree == 90)////TXT转270
        {
            int x = -1, y = -1, xr = -1, yr = -1;

            for (int i = 0; i < count; i++) {
                // 计算 x,y 坐标
                x = i % txtColct;
                y = i / txtColct;

                xr = y;
                yr = (txtColct - 1) - x;

                degTxtData.set(yr * txtRowct + xr, txtData.get(i));
            }

            // 交换行数与列数
            x = txtColct;
            txtColct = txtRowct;
            txtRowct = x;
        } else if (degree == 270)////TXT转90
        {

            int x = -1, y = -1, xr = -1, yr = -1;
            for (int i = 0; i < count; i++) {
                // 计算 x,y 坐标
                x = i % txtColct;
                y = i / txtColct;

                xr = (txtRowct - 1) - y;
                yr = x;

                degTxtData.set(yr * txtRowct + xr, txtData.get(i));
            }

            // 交换行数与列数
            x = txtColct;
            txtColct = txtRowct;
            txtRowct = x;
        } else if (degree == 0)////TXT不转角度
        {
            for (int i = 0; i < count; i++) {

                degTxtData.set(i, txtData.get(i));
            }
        }

        this.TxtMap = new Object[this.txtRowct][this.txtColct];

        for (int i = 0; i < this.txtRowct; i++) {
            for (int j = 0; j < this.txtColct; j++) {
                this.TxtMap[i][j] = degTxtData.get(j + i * txtColct);
            }
        }
    }
}