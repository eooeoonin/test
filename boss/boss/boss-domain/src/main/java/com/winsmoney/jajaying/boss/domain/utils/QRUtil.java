package com.winsmoney.jajaying.boss.domain.utils;

import com.google.zxing.*;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.winsmoney.jajaying.boss.domain.TemplateDomain;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by chenkai1 on 2017/9/5.
 */
public class QRUtil {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplateDomain.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final String QRCODE_PATH = "/static/qrcode/";

    /**
     * 保存二维码
     */
    public static void saveQRCode(String url, String activityId, String channelId) {
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String format = "png";// 二维码的图片格式
        HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码
            String marketRootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketTargetPath");
            File outputFile = new File(marketRootPath + File.separator + activityId + QRCODE_PATH + channelId + "." + format);
            File parentFile = outputFile.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            MatrixToImageWriter.writeToPath(bitMatrix, format, outputFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取二维码信息
     */
    private static void readQRCode(String path) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File(path);
//            File file = new File("D:/new2.png");
            BufferedImage image = null;
            image = ImageIO.read(file);
            HashMap hashMap = new HashMap();
            hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            Result result = formatReader.decode(binaryBitmap, hashMap);
            System.out.println("解析结果：" + result.toString());
            System.out.println("二维码格式类型：" + result.getBarcodeFormat());
            System.out.println("二维码文本：" + result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

