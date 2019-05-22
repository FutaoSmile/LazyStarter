package com.lazyer.tools.excel;

import com.lazyer.foundation.foundation.exception.ApplicationException;
import com.lazyer.foundation.model.Constant;
import com.lazyer.foundation.model.ErrorMessage;
import com.lazyer.foundation.utils.ServiceTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 向excel中写入内容/生成excel
 *
 * @author futao
 * Created on 2019-05-17.
 */
@Slf4j
public class ExcelWriter {

    /**
     * 导出excel到文件
     *
     * @param filePath    文件名
     * @param sheetName   sheetName
     * @param columnHeads 列头
     * @param data        数据
     */
    public static void export2File(String filePath, String sheetName, String[] columnHeads, List<List<Object>> data) {
        SXSSFWorkbook workbook = genExcel(sheetName, columnHeads, data);
        try {
            workbook.write(FileUtils.openOutputStream(new File(filePath + ".xlsx")));
        } catch (IOException e) {
            log.error("导出excel失败", e);
            throw ApplicationException.ae(ErrorMessage.ApplicationErrorMessage.EXPORT_EXCEL_FAIL);
        }
        log.info("导出成功...");
    }


    /**
     * 数据写入到HttpServletResponse
     *
     * @param fileName
     * @param sheetName
     * @param columnHeads
     * @param data
     * @param response
     */
    public static void export2Response(String fileName, String sheetName, String[] columnHeads, List<List<Object>> data, HttpServletResponse response) {

        //设置响应头
        response.setCharacterEncoding(Constant.UTF8_ENCODE);
        response.setContentType("application/vnd.ms-excel");

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", Constant.UTF8_ENCODE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SXSSFWorkbook workbook = genExcel(sheetName, columnHeads, data);
        //创建一个输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("导出excel异常:{}", e.getMessage(), e);
            throw ApplicationException.ae(ErrorMessage.ApplicationErrorMessage.EXPORT_EXCEL_FAIL);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流异常:{}", e.getMessage(), e);
                    throw ApplicationException.ae(ErrorMessage.ApplicationErrorMessage.CLOSE_OUTPUT_STREAM_FAIL);
                }
            }
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("关闭输出流异常:{}", e.getMessage(), e);
                throw ApplicationException.ae(ErrorMessage.ApplicationErrorMessage.CLOSE_OUTPUT_STREAM_FAIL);
            }
        }
    }

    /**
     * 通过getter方法获取数据并且写入到HttpServletResponse
     *
     * @param fileName
     * @param sheetName
     * @param columnHeads
     * @param methods
     * @param objs
     * @param response
     */
    public static void exportByGetter2Response(String fileName, String sheetName, String[] columnHeads, Method[] methods, List<?> objs, HttpServletResponse response) {
        export2Response(fileName, sheetName, columnHeads, exportByGetter(columnHeads, methods, objs), response);
    }

    /**
     * 通过getter方法获取数据并写入到excel
     *
     * @param filePath
     * @param sheetName
     * @param columnHeads
     * @param methods
     * @param objs
     */
    public static void exportByGetter2File(String filePath, String sheetName, String[] columnHeads, Method[] methods, List<?> objs) {
        export2File(filePath, sheetName, columnHeads, exportByGetter(columnHeads, methods, objs));
    }

    private static List<List<Object>> exportByGetter(String[] columnHeads, Method[] methods, List<?> objs) {
        if (columnHeads == null) {
            columnHeads = new String[methods.length];
            for (int i = 0; i < methods.length; i++) {
                columnHeads[i] = ServiceTools.getFieldName(methods[i]);
            }
        }
        List<List<Object>> dataList = new ArrayList<>();
        objs.forEach(obj -> {
                    ArrayList<Object> rowData = new ArrayList<>();
                    for (int i = 0; i < methods.length; i++) {
                        try {
                            rowData.set(i, methods[i].invoke(obj));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            log.error("执行方法异常");
                            throw ApplicationException.ae(ErrorMessage.ApplicationErrorMessage.INVOKE_METHOD_FAIL);
                        }
                    }
                    dataList.add(rowData);
                }
        );
        return dataList;

    }


    /**
     * 将数据写入Excel
     *
     * @param sheetName   sheetName
     * @param columnHeads 列头
     * @param data        数据
     * @return
     */
    private static SXSSFWorkbook genExcel(String sheetName, String[] columnHeads, List<List<Object>> data) {
        //创建poi导出数据对象
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        //创建sheet页
        SXSSFSheet sheet = workbook.createSheet(sheetName);

        //创建表头
        SXSSFRow headRow = sheet.createRow(0);
        if (columnHeads != null && columnHeads.length >= 1) {
            for (int i = 0; i < columnHeads.length; i++) {
                headRow.createCell(i).setCellValue(columnHeads[i]);
            }
        }

        data.forEach(objArray -> {
            SXSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int i = 0; i < objArray.size(); i++) {
                dataRow.createCell(i).setCellValue(String.valueOf(objArray.get(i)));
            }
        });
        return workbook;
    }
}
