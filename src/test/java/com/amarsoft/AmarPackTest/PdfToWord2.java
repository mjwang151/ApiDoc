
package com.amarsoft.AmarPackTest;

import java.io.*;
import com.aspose.words.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */

@SpringBootTest
public class PdfToWord2 {

    @org.junit.jupiter.api.Test
    public void test(){
        String wordFile = "F:/20210625.doc";
        String pdfFile = "F:/20210625.pdf";
        System.out.println("开始转换...");
        // 开始时间
        long start = System.currentTimeMillis();
        try {
            doc2pdf(wordFile,pdfFile);

            long end = System.currentTimeMillis();
            System.out.println("转换成功，用时：" + (end - start) + "ms");
        }catch(Exception e) {
            System.out.println("转换失败"+e.getMessage());
        }
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Test.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String Address,String targetAddress) {

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(targetAddress);  //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(Address);                    //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

