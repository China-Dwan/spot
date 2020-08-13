package com.business.exportimport.exportpdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.business.exportimport.exportpdf.utils.ExcelConvertPDF;

import java.io.FileOutputStream;
import java.util.Collections;

@RestController
@RequestMapping("/guest/users")
@Slf4j
public class PDFController {

    @RequestMapping("/pdf")
    public void ss(@RequestBody MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        String pathOfPdf = "d://test1.pdf";
        FileOutputStream fos = new FileOutputStream(pathOfPdf);
        ExcelConvertPDF.ExcelConvertPDF(Collections.singletonList(workbook), fos, null);
    }

}
