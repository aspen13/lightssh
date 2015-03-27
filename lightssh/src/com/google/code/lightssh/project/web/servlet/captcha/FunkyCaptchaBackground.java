package com.google.code.lightssh.project.web.servlet.captcha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.BackgroundProducer;

public class FunkyCaptchaBackground implements BackgroundProducer{
	protected float perturbationlevel = 0.5f;
	protected Random myRandom = new SecureRandom();
	
	@Override
	public BufferedImage addBackground(BufferedImage image) {
		Color colorLeftUp = Color.yellow;//colorGeneratorLeftUp.getNextColor();
		Color colorLeftDown = Color.red;//colorGeneratorLeftDown.getNextColor();
		Color colorRightUp = Color.green;//colorGeneratorRightUp.getNextColor();
		Color colorRightDown = Color.black;//colorGeneratorRightDown.getNextColor();
		BufferedImage imageWithBackground = new BufferedImage(image.getWidth(),image.getHeight(), 4);
		
		Graphics2D graph = imageWithBackground.createGraphics();
		graph.setColor(Color.white);
		graph.fillRect(0, 0, image.getHeight(), image.getWidth());
		float height = image.getHeight();
		float width = image.getWidth();
		for (int j = 0; j < image.getHeight(); j++) {
			for (int i = 0; i < image.getWidth(); i++) {
				float leftUpRatio = (1.0F - (float) i / width)
						* (1.0F - (float) j / height);
				float leftDownRatio = (1.0F - (float) i / width)
						* ((float) j / height);
				float rightUpRatio = ((float) i / width)
						* (1.0F - (float) j / height);
				float rightDownRatio = ((float) i / width)
						* ((float) j / height);
				float red = ((float) colorLeftUp.getRed() / 255F) * leftUpRatio
						+ ((float) colorLeftDown.getRed() / 255F)
						* leftDownRatio
						+ ((float) colorRightUp.getRed() / 255F) * rightUpRatio
						+ ((float) colorRightDown.getRed() / 255F)
						* rightDownRatio;
				float green = ((float) colorLeftUp.getGreen() / 255F)
						* leftUpRatio
						+ ((float) colorLeftDown.getGreen() / 255F)
						* leftDownRatio
						+ ((float) colorRightUp.getGreen() / 255F)
						* rightUpRatio
						+ ((float) colorRightDown.getGreen() / 255F)
						* rightDownRatio;
				float blue = ((float) colorLeftUp.getBlue() / 255F)
						* leftUpRatio
						+ ((float) colorLeftDown.getBlue() / 255F)
						* leftDownRatio
						+ ((float) colorRightUp.getBlue() / 255F)
						* rightUpRatio
						+ ((float) colorRightDown.getBlue() / 255F)
						* rightDownRatio;
				if (myRandom.nextFloat() > perturbationlevel)
					graph.setColor(new Color(red, green, blue, 1.0F));
				else
					graph.setColor(new Color(compute(red), compute(green),
							compute(blue), 1.0F));
				graph.drawLine(i, j, i, j);
			}

		}

		// draw the transparent image over the background
		graph.drawImage(image, 0, 0, null);
		//graph.dispose();
		return imageWithBackground;
	}
	
	private float compute(float f) {
		float range = 1.0F - f >= f ? f : 1.0F - f;
		if (myRandom.nextFloat() > 0.5F)
			return f - myRandom.nextFloat() * range;
		else
			return f + myRandom.nextFloat() * range;
	}

}
