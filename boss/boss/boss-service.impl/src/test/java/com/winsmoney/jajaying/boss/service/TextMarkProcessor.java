//package com.winsmoney.jajaying.boss.service;
//
//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//
//import java.io.FileOutputStream;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import javax.imageio.ImageIO;
//import javax.swing.*;
//
///**
// * 添加文字水印
// *
// * @author Ricky Fung
// */
//public class TextMarkProcessor {
//
//    public static void main(String[] args) throws Exception {
//
//        System.out.println("开始水印：");
//
////        TextMarkProcessor.addWatermark("f:/a.pdf", "f:/b.pdf", "家家盈", "jpg");
//        TextMarkProcessor.setWaterMarkForPDF("f:/a.pdf", "f:/b.pdf");
//
//        System.out.println("水印完成。");
//
//    }
//
//
//
//    /**
//
//     * @description
//
//     * @paramsourceImgPath 源图片路径
//
//     * @paramtarImgPath 保存的图片路径
//
//     * @paramwaterMarkContent 水印内容
//
//     * @paramfileExt 图片格式
//
//     * @returnvoid
//
//     */
//
//    public static void addWatermark(String sourceImgPath, String tarImgPath, String waterMarkContent,String fileExt){
//
//        Font font = new Font("宋体", Font.BOLD, 50);//水印字体，大小
//
//        Color markContentColor = Color.red;//水印颜色
//
//        Integer degree = 45;//设置水印文字的旋转角度
//
//        float alpha = 0.2f;//设置水印透明度
//
//        OutputStream outImgStream = null;
//
//        try{
//
//            File srcImgFile = new File(sourceImgPath);//得到文件
//
//            java.awt.Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
//
//            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
//
//            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
//
//// 加水印
//
//            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
//
//            Graphics2D g = bufImg.createGraphics();//得到画笔
//
//            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
//
//            g.setColor(markContentColor); //设置水印颜色
//
//            g.setFont(font); //设置字体
//
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
//
//            if(null!= degree) {
//
//                g.rotate(Math.toRadians(degree));//设置水印旋转
//
//            }
//
//            JLabel label = new JLabel(waterMarkContent);
//
//            FontMetrics metrics = label.getFontMetrics(font);
//
//            int width = metrics.stringWidth(label.getText());//文字水印的宽
//
//            int rowsNumber = srcImgHeight/width;// 图片的高 除以 文字水印的宽 ——> 打印的行数(以文字水印的宽为间隔)
//
//            int columnsNumber = srcImgWidth/width;//图片的宽 除以 文字水印的宽 ——> 每行打印的列数(以文字水印的宽为间隔)
//            //防止图片太小而文字水印太长，所以至少打印一次
//            if(rowsNumber < 1){
//                rowsNumber = 1;
//            }
//
//            if(columnsNumber < 1){
//                columnsNumber = 1;
//            }
//
//            for(int j=0;j<rowsNumber;j++){
//
//                for(int i=0;i<columnsNumber;i++){
//                    g.drawString(waterMarkContent, i*width + j*width, -i*width + j*width);//画出水印,并设置水印位置
//                }
//            }
//
//            g.dispose();// 释放资源
//            // 输出图片
//            outImgStream = new FileOutputStream(tarImgPath);
//
//            ImageIO.write(bufImg, fileExt, outImgStream);
//
//        } catch(Exception e) {
//
//            e.printStackTrace();
//
//            e.getMessage();
//
//        } finally{
//
//            try{
//
//                if(outImgStream != null){
//
//                    outImgStream.flush();
//
//                    outImgStream.close();
//
//                }
//
//            } catch(Exception e) {
//
//                e.printStackTrace();
//
//                e.getMessage();
//
//            }
//
//        }
//
//    }
//
//
//    /**
//     * pdf文件添加 图片水印
//     *
//     * @param sourceFilePath    源文件路径
//     * @param fileWaterMarkPath 水印生成文件路径
//     * @return
//     * @throws Exception
//     */
//    public static void setWaterMarkForPDF(String sourceFilePath, String fileWaterMarkPath) throws Exception {
//        PdfReader reader = new PdfReader(sourceFilePath);
//        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileWaterMarkPath));
//
//        int total = reader.getNumberOfPages() + 1;
//        PdfContentByte content;
//        Image img = Image.getInstance("F:/11.jpg");
//        img.setAbsolutePosition(30, 100);
//        for (int i = 1; i < total; i++) {
//            content = stamp.getOverContent(i);// 在内容上方加水印
//            content.addImage(img);
//        }
//        stamp.close();
//        reader.close();
//    }
//
//}