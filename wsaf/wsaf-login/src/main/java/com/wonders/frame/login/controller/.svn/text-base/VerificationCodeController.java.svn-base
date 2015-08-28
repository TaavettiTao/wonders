package com.wonders.frame.login.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 验证码生成器
 */
@Controller
@RequestMapping("/verificationCode")
public class VerificationCodeController{
	
	@RequestMapping("/createImage.jpeg")
	@ResponseBody
	public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		BufferedImage  image = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, 80, 30);
		Random r = new Random();
		String number = getNumber(4);
		HttpSession session = request.getSession();
		session.setAttribute("sessionCode", number);
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.setFont(new Font(null,Font.ITALIC,22));
		g.drawString(number, 4, 24);
		for(int i = 0;i < 8;i ++){
			g.drawLine(r.nextInt(80), r.nextInt(30), 
					r.nextInt(80), r.nextInt(30));
		}
		response.setContentType("image/jpeg");
		OutputStream ops = response.getOutputStream();
		javax.imageio.ImageIO.write(image, "jpeg", ops);
		ops.close();
		
	}
	
	private String getNumber(int size) {
		String number = "";
		String str = "ABCDEFGHIJKLMN" +
				"OPQRSTUVWXYZ0123456789";
		Random r = new Random();
		for(int i = 0;i < size;i ++){
			number += str.charAt(
					r.nextInt(str.length()));
		}
		return number;
	}

}
