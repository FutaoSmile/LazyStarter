//package com.lazy.tools.excel.easypoi;
//
//import cn.afterturn.easypoi.excel.ExcelExportUtil;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import com.lazy.tools.excel.data.Data;
//import com.lazy.tools.excel.data.User;
//import org.apache.commons.io.FileUtils;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * @author futao
// * Created on 2019/11/18.
// */
//public class ExcelExportTest {
//
//    @Test
//    public void test() throws IOException {
//        Workbook sheets = ExcelExportUtil.exportBigExcel(new ExportParams(), User.class, Data.get());
//        File file = new File("excel2" + ".xls");
//        if (file.exists()) {
//            file.deleteOnExit();
//        }
//        sheets.write(FileUtils.openOutputStream(file));
//
//    }
//
//}
