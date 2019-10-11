package com.lazy.tools.test.excel;

import com.lazy.tools.excel.ExcelWriter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author futao
 * Created on 2019-05-23.
 */
public class ExcelTest {

    @Test
    public void test1() {
        ArrayList<List<Object>> data = new ArrayList<>();

        ArrayList<Object> rowData = new ArrayList<>();
        rowData.add("futao");
        rowData.add(18);
        rowData.add("杭州市");
        rowData.add("18797811992");

        data.add(rowData);//添加一行数据
        data.add(rowData);//添加一行数据
        data.add(rowData);//添加一行数据
        data.add(rowData);//添加一行数据
        ExcelWriter.export2File("./excelTest", "sheet1", new String[]{"用户名", "年龄", "地址", "手机号"}, data);
    }
}
