package com.lance.pdfdemo.freemarker;

import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Locale;

@Slf4j
public class HtmlUtil {

    private freemarker.template.Configuration freemarker = null;

    // 字体路径，放在资源目录下
    private static final String FONT_PATH = "simsun.ttc";

    private HtmlUtil() {
        freemarker = new freemarker.template.Configuration();
        java.io.InputStream is = null;
        try {
            is = getClass().getResourceAsStream("/templates/index.ftl");
            freemarker.setSettings(is);
            freemarker.setEncoding(Locale.CHINA, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }


    public static void file2Pdf(File htmlFile, String pdfFile) {
        try (OutputStream os = new FileOutputStream(pdfFile)) {
            String url = htmlFile.toURI().toURL().toString();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            // 获取字体绝对路径，ApplicationContextUtil是我自己写的类
            //String fontPath = ApplicationContextUtil.classpath(FONT_PATH);
            //fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            // 抛出自定义异常
            log.error("error, ", e);
        }
    }

    public static void html2Pdf(String html, String pdfFile) throws IOException {


        File pdfFilePath = new File("D:\\" + pdfFile);
        log.info("pdfFile={}", pdfFilePath);
        if (!pdfFilePath.exists()) {
            log.info("create file");
            pdfFilePath.createNewFile();
        }

        try (OutputStream os = new FileOutputStream(pdfFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            // 获取字体绝对路径，ApplicationContextUtil是我自己写的类
            String fontPath = getPath(FONT_PATH);
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            //添加图片-方式2:render方式添加图片
            renderer.getSharedContext().setBaseURL("https://img1.bdstatic.com/static/common/img/icon_cf1b905.png");

            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            // 抛出自定义异常
            log.error("error, ", e);
        }
    }

    public static String getPath(String fontFileName) throws URISyntaxException {
        URL resourceUrl = HtmlUtil.class.getClassLoader().getResource(fontFileName);
        File file = Paths.get(resourceUrl.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        log.info("absolutePath={}", absolutePath);
        return absolutePath;
    }
}