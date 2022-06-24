package com.lance.pdfdemo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

public class Itext5HtmlToPDFUtil {
    public static void toPdf(String html, String simpleFileName) throws Exception {
        //第一步，创建一个 iTextSharp.text.Document对象的实例：
        Document document = new Document();
        //第二步，为该Document创建一个Writer实例：
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\pdf\\ + " + simpleFileName));
        //第三步，打开当前Document
        document.open();
        //第四步，为当前Document添加内容：

        ByteArrayInputStream fis = new ByteArrayInputStream(html.getBytes());

        // 4.2 该为加载html文件生成pdf方式
        //FileInputStream fis = new FileInputStream("D:\\test\\HelloWorld.html");
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, fis,
                null, Charset.defaultCharset(), new FontProviderUtil());
        //第五步，关闭Document
        document.close();
        //System.out.println("OK!");

    }
}