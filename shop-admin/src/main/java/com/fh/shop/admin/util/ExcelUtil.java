package com.fh.shop.admin.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static XSSFWorkbook buildWorkBook(List dataList, String[] headers, String[] props, String title) {
        // 创建workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建sheet
        XSSFSheet sheet = workbook.createSheet(title);
        // 创建大标题
        buildTitle(workbook, sheet, title);
        // 创建头
        buildHeader(sheet, headers);
        // 创建数据行
        buildBody(dataList, workbook, sheet, props);
        // 返回workbook
        return workbook;
    }

    private static void buildTitle(XSSFWorkbook workbook, XSSFSheet sheet, String title) {
        XSSFRow row = sheet.createRow(13);
        XSSFCell titleCell = row.createCell(3);
        titleCell.setCellValue(title);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(13, 14, 3, 8);
        sheet.addMergedRegion(cellRangeAddress);
        // 创建大标题样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 22);
        font.setColor(HSSFColor.BLUE.index);
        titleStyle.setFont(font);
        titleStyle.setFillForegroundColor(HSSFColor.PINK.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCell.setCellStyle(titleStyle);
    }

    private static void buildBody(List dataList, XSSFWorkbook workbook, XSSFSheet sheet, String[] props) {
        // 创建样式
        XSSFCellStyle numStyle = workbook.createCellStyle();
        numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        short format = workbook.createDataFormat().getFormat("yyyy-mm-dd");
        dateStyle.setDataFormat(format);
        XSSFCellStyle dateTimeStyle = workbook.createCellStyle();
        short timeFormat = workbook.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss");
        dateTimeStyle.setDataFormat(timeFormat);
        try {
            for (int i = 0; i < dataList.size(); i++) {
                Object data = dataList.get(i);
                Class<?> dataClass = data.getClass();
                XSSFRow bodyRow = sheet.createRow(i + 16);
                for (int j = 0; j < props.length; j++) {
                    Field declaredField = dataClass.getDeclaredField(props[j]);
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(data);
                    Class<?> type = declaredField.getType();
                    if (type == java.lang.String.class) {
                        bodyRow.createCell(j+3).setCellValue((String)value);
                    }
                    if (type == java.math.BigDecimal.class) {
                        XSSFCell numCell = bodyRow.createCell(j+3);
                        numCell.setCellValue(new BigDecimal(String.valueOf(value)).doubleValue());
                        numCell.setCellStyle(numStyle);
                    }
                    if (type == java.util.Date.class) {
                        if (value != null) {
                            XSSFCell dateCell = bodyRow.createCell(j+3);
                            dateCell.setCellValue((Date) value);
                            dateCell.setCellStyle(dateStyle);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void buildHeader(XSSFSheet sheet, String[] headers) {
        // 创建标题行
        XSSFRow titleRow = sheet.createRow(15);
        for (int i = 0; i < headers.length; i++) {
            titleRow.createCell(i+3).setCellValue(headers[i]);
        }
    }


}
