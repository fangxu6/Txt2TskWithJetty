package controller;

import customer.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.TSKParse;
import service.TxtParse;

/**
 * className: TxtTskController
 * package: controller
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 20:05
 */
@Controller
public class TxtTskController {

    @Autowired
    TSKParse tskParse;

    @Autowired
    TxtParse txtParse;

    @GetMapping("/home")
    public ResponseEntity home() {
        throw new CustomException("ERR-001", "file not found");
//        return new ResponseEntity("Hello World!", HttpStatus.OK);
    }

    @PostMapping("/txtTskParse")
    public ResponseEntity home(
            @RequestParam("txtUrl") String txtUrl,
            @RequestParam("tskUrl") String tskUrl,
            @RequestParam("retTskUrl") String retTskUrl,
            @RequestParam("slotNo") String slotNo) {
        //tsk文件解析
        TSKParse tskParse = new TSKParse();
        TSKParse tskData = tskParse.read(tskUrl);
        //txt文件解析
        TxtParse txtParse = new TxtParse();
        TxtParse txtData = txtParse.read(txtUrl);
        //txt图谱旋转角度
        int degree = Integer.parseInt(txtData.getTxtFlat())-tskData.getOrientationFlatAngle();
        txtParse.rotate(degree);
        //txt图谱补全和校验
        tskData.buBian(txtData);
        //生成新的tsk文件
        tskData.createNewTSK(txtData,retTskUrl,slotNo);



        return new ResponseEntity("file created!", HttpStatus.OK);
    }
}
