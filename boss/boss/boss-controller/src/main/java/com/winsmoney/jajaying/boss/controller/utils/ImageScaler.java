package com.winsmoney.jajaying.boss.controller.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 暂不支持动态图
 * 
 * @author TaiL
 * @date 2014年4月11日
 */
public class ImageScaler {

	public static BufferedImage scale(final BufferedImage image, final double scale) {
		return scale(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
	}

	public static BufferedImage scaleW(final BufferedImage image, final int width) {
		return scale(image, width, (int) (image.getHeight() * ((double) width / image.getWidth())));
	}

	public static BufferedImage scaleH(final BufferedImage image, final int height) {
		return scale(image, (int) (image.getWidth() * ((double) height / image.getHeight())), height);
	}

	public static BufferedImage scale(final BufferedImage image, final int width, final int height) {
		final Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		int type = image.getType();
		if (type == BufferedImage.TYPE_CUSTOM) {
			type = BufferedImage.TYPE_INT_RGB;
		}
		final BufferedImage bufferedImage = new BufferedImage(width, height, type);
		final Graphics g = bufferedImage.getGraphics();
		g.drawImage(scaledImage, 0, 0, null);
		g.dispose();

		return bufferedImage;
	}

}
