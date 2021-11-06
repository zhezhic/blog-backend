package xyz.zhezhi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhezhi
 * @className: GenerateAvatarByFName
 * @description: 生成头像工具类
 * @date 2021/8/18 上午11:01
 * @version：1.0
 */
@Component
public class GenerateAvatar {
    private static String avatar;
    @Value("${my-config.file.upload.avatar}")
    public void setAvatar(String avatar) {
        GenerateAvatar.avatar = avatar;
    }
    private static final int WIDTH = 36;
    private static final int HEIGHT = 36;
    public static void name(String name,String id) throws IOException {
        //截取名称首字
        name = name.substring(0,1).toUpperCase();
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        //设置背景色
        graphics.setColor(new Color(2, 136, 209));
        graphics.fillRect(0,0,WIDTH,HEIGHT);
        //画名字
        Font font = new Font("Source Han Sans CN", Font.BOLD, 12);
        //抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //文字居中
        FontMetrics metrics = graphics.getFontMetrics(font);
        int x = (WIDTH-metrics.stringWidth(name))/2;
        int y = (HEIGHT-metrics.getHeight())/2+metrics.getAscent();
        graphics.setFont(font);
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawString(name,x,y);
        //生成到本地
        ImageIO.write(bufferedImage, "png", new FileOutputStream(avatar+id+".png"));
    }
}
