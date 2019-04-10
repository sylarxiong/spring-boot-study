package com.example.qrcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QrCodeController {

    private static final int WIDTH = 400; // 二维码宽
    private static final int HEIGHT = 400; // 二维码高

    private static final int QR_COLOR = 0xFF000000; // 默认是黑色
    private static final int BG_WHITE = 0xFFFFFFFF; // 背景颜色

    private static final String FORMAT = "png";

    /**
     * 创建带logo的二维码
     */
    @GetMapping("/getLogoQrCode")
    public void qrCode(String content, String logoUrl, HttpServletResponse response) {
        Map hits = new HashMap();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
        //纠错等级，纠错等级越高存储信息越少
        hits.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距
        hits.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hits);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? QR_COLOR : BG_WHITE);
                }
            }
            // 构建绘图对象
            Graphics2D g = image.createGraphics();
            //通过输入流获取图片数据
            BufferedImage logo = ImageIO.read(new URL(logoUrl));
            {
                //图片抗锯齿设置
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
                //Ellipse2D.Double shape = new Ellipse2D.Double(WIDTH * 2 / 5, HEIGHT * 2 / 5, WIDTH * 2 / 10, HEIGHT * 2 / 10);
                //需要保留的区域
               // g.setClip(shape);
                // 开始绘制logo图片
                g.drawImage(logo, WIDTH * 2 / 5, HEIGHT * 2 / 5, WIDTH * 2 / 11, HEIGHT * 2 / 11, null);
                g.dispose();

            }
            //在圆图外面再画一个圆
            {
                //新创建一个graphics，这样画的圆不会有锯齿
                g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //画笔是4.5个像素，BasicStroke的使用可以查看下面的参考文档
                //使画笔时基本会像外延伸一定像素，具体可以自己使用的时候测试
                Stroke s = new BasicStroke(4.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                g.setStroke(s);
                g.setColor(Color.WHITE);
                g.drawRect(WIDTH * 2 / 5, HEIGHT * 2 / 5, WIDTH * 2 / 11, HEIGHT * 2 / 11);
                g.dispose();
            }
            logo.flush();
            ImageIO.write(image, FORMAT, response.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 创建二维码
     */
    @GetMapping("/getQrCode")
    public void qrCode(String content, HttpServletResponse response) {
        Map hits = new HashMap();
        hits.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
        //纠错等级，纠错等级越高存储信息越少
        hits.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距
        hits.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hits);
            MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, response.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
