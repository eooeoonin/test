//package com.winsmoney.jajaying.boss.service;
//
//import java.awt.FontMetrics;
//import java.io.FileOutputStream;
//import javax.swing.JLabel;
//
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfGState;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class TestwaterMark {
//    private static int interval = -5;
//
//    public static void waterMark(String inputFile, String outputFile, String waterMarkName) {
//        try {
//            PdfReader reader = new PdfReader(inputFile);
//            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
//            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
//            Rectangle pageRect = null;
//            PdfGState gs = new PdfGState();
//            gs.setFillOpacity(0.3f);
//            gs.setStrokeOpacity(0.4f);
//            int total = reader.getNumberOfPages() + 1;
//            JLabel label = new JLabel();
//            FontMetrics metrics;
//            int textH = 0;
//            int textW = 0;
//            label.setText(waterMarkName);
//            metrics = label.getFontMetrics(label.getFont());
//            textH = metrics.getHeight();
//            textW = metrics.stringWidth(label.getText());
//            PdfContentByte under;
//            for (int i = 1; i < total; i++) {
//                pageRect = reader.getPageSizeWithRotation(i);
//                under = stamper.getOverContent(i);
//                under.saveState();
//                under.setGState(gs);
//                under.beginText();
//                under.setFontAndSize(base, 20);
//                // 水印文字成30度角倾斜
//                // 你可以随心所欲的改你自己想要的角度
//                for (int height = interval + textH; height < pageRect.getHeight();
//                     height = height + textH * 8) {
//                    for (int width = interval + textW; width < pageRect.getWidth() + textW;
//                         width = width + textW * 5) {
//                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW, height - textH, 30);
//                    }
//                }
//                // 添加水印文字
//                under.endText();
//            }
//            //说三遍
//            // 一定不要忘记关闭流
//            // 一定不要忘记关闭流
//            // 一定不要忘记关闭流
//            stamper.close();
//            reader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        waterMark("F:/a.pdf", "F:/7.pdf", "家家盈");
//    }
//}
//
