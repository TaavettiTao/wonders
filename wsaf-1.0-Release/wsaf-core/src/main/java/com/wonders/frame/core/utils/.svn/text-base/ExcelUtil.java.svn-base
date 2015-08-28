package com.wonders.frame.core.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导出Excel文档工具类
 * @author mengjie
 * */
public class ExcelUtil<T> {
 
    /**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
private final static Logger logger=LoggerFactory.getLogger(ExcelUtil.class);
	
	public static enum ExcelStyle {TITLE_STYLE,VALUE_STYLE}
	
	public static void createWorkBook(String fileName,List<HashMap<String,String>> list,
			LinkedHashMap<String,String> hmFieldCnName,HttpServletResponse response){
        // 创建excel工作簿
        Workbook wb = createWorkBook(list,hmFieldCnName);
        //输出excel
        exportExcel(fileName,wb,response);
        
    }
	
	private static Workbook createWorkBook(List<HashMap<String,String>> list,
			LinkedHashMap<String,String> hmFieldCnName){
		Workbook wb = new HSSFWorkbook();
		
		HashMap<ExcelStyle,CellStyle> styles=initStyle(wb);

		// 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");
        
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<hmFieldCnName.keySet().size();i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
 
        // 创建第一行
        Row row = sheet.createRow((short) 0);

        //设置列名
        String[] keys=hmFieldCnName.keySet().toArray(new String[0]);
        for(int i=0;i<keys.length;i++){      
            Cell cell = row.createCell(i);
            cell.setCellValue(hmFieldCnName.get(keys[i]));
            cell.setCellStyle(styles.get(ExcelStyle.TITLE_STYLE));
        	
        }
        //设置每行每列的值
        for (int i = 1; i <= list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(int j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                String cellValue=list.get(i-1).get(keys[j]) == null?"": list.get(i-1).get(keys[j]).toString();

                cell.setCellValue(cellValue);
                
                cell.setCellStyle(styles.get(ExcelStyle.VALUE_STYLE));
            }
        }
        
        return wb;
	}
	
	private static HashMap<ExcelStyle,CellStyle> initStyle(Workbook wb){
		HashMap<ExcelStyle,CellStyle> styles=new HashMap<ExcelStyle,CellStyle>();
		// 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
 
        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
 
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
 
        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
 
        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put(ExcelStyle.TITLE_STYLE, cs);
        styles.put(ExcelStyle.VALUE_STYLE, cs2);
        
        return styles;
        
	}
	private static void exportExcel(String fileName,Workbook wb,HttpServletResponse response){
		
		try {			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		
			wb.write(os);
	
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			
			FileUtil.downLoadFile(fileName+".xls", is, response);
		} catch (final IOException e) {
			logger.error("Exception Throwable", e);
		}
	}
	 /**
     * 读出excel内容
     * @param 
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static List<String[]> getExcelData(File file) {
        try {
            // 文件二进制输入流
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            List<String[]> data = new ArrayList<String[]>();
            // 读取excel工作簿
//            Workbook wb = 
            Workbook wb = null;
                try {
                    wb = new HSSFWorkbook(in);
                } catch (Exception ex) {
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    wb = new XSSFWorkbook(is);
                }
            //取得sheet的数目     
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                // Excel表
                Sheet sheet = wb.getSheetAt(i);
                int rowCount = sheet.getPhysicalNumberOfRows();
                // excell的行
                for(int j=sheet.getFirstRowNum();j<rowCount;j++){
                    Row row = sheet.getRow(j);
                    if(row==null||row.getLastCellNum()<=0){
                        continue;
                    }
                    String[] strArray = new String[row.getLastCellNum()];
                    int count = 0;
                    for (short k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell == null) {
                            strArray[count++] = "";
                            continue;
                        }
                        String cellValue = "";
                        switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC://数值类型  
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                //2014-11-26：将cellValue=date.toString();替换为以下方式
                                cellValue=DateFormatUtil.timeFormat("yyyy-MM-dd HH:mm:ss", date);
                                //cellValue=date.toString();
                            } else {
                                cellValue = PHONENUMBER_DF.format(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING://单元格为STRING类型 
                            cellValue = cell.getRichStringCellValue().toString();
                            break;
                        case Cell.CELL_TYPE_FORMULA: //公式  
                            cellValue = String.valueOf(cell.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN: //公式  
                        	cellValue = String.valueOf(cell.getBooleanCellValue());//boolean型值
                        	break;
                        default:
                            cellValue = "";
                            break;
                        }
                        strArray[count++] = cellValue;
                    }
                    data.add(strArray);
                }
            }
            // 如果is不为空，则关闭InputSteam文件输入流
            if (in != null) {
                in.close();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static DecimalFormat PHONENUMBER_DF=new DecimalFormat("############.####");
}