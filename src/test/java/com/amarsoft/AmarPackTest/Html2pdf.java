package com.amarsoft.AmarPackTest;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/12/23 17:00
 */
@SpringBootTest
public class Html2pdf {



    @org.junit.jupiter.api.Test
    public void test1() throws IOException, DocumentException {
//        ITextRenderer renderer = new ITextRenderer();
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        fontResolver.addFont("/Users/hehe/share/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        OutputStream os = new FileOutputStream("/Users/hehe/Desktop/iTextPDF.pdf");
//        String htmlstr = HttpHandler.sendGet("http://localhost:10086/test/iTextPDF.html");//HttpHandler.sendGet只是单纯获得指定网页的html字符串内容
//        renderer.setDocumentFromString(htmlstr);
//        renderer.layout();
//        renderer.createPDF(os);
    }




}
