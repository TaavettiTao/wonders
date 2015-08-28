package com.wonders.frame.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateTimeFormat extends SimpleDateFormat{
	
	private static final long serialVersionUID = -7888845703824488466L;

	@Override
	public Date parse(String source) throws ParseException{
		Date d;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{			
			d = sdf.parse(source);		
		}catch(ParseException e){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			d = sdf.parse(source);
		}
		return d;
	}
}
