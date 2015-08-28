/** 
* @Title: FileUtil.java 
* @Package com.wonders.frame.core.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: FileUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class FileUtil {
	private final static Logger logger=LoggerFactory.getLogger(FileUtil.class);
	
	public static void downLoadFile(String fileName,InputStream is,HttpServletResponse response)throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {			
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();

		
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			logger.error("Exception Throwable", e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	/**
	 * 从服务器获取文件，并下载
	 * @param fileName
	 * @param filePath
	 * @param response
	 * @throws IOException
	 */
	public static void downLoadFile(String fileName,String filePath,HttpServletResponse response){
		try{
			InputStream is = new  FileInputStream(filePath);
			downLoadFile(fileName,is,response);
		} catch (final IOException e) {
			logger.error("Exception Throwable", e);
		}
	}
}
