package com.wf.pc.test;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestPoi {
    public static void main(String[] args) throws IOException {
        /* ！使用POI版本：3.10-FINAL*/

        /* 建立新HSSFWorkbook对象*/
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet createSheet = workbook.createSheet("汇总页面");
        XSSFRow row = createSheet.createRow((short)0);

        /* 连接跳转*/
        XSSFCell likeCell = row.createCell((short)0);
        //  XSSFHyperlink link = new XSSFHyperlink(XSSFHyperlink.LINK_URL);// 无法实例化XSSFHyperlink对象
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFHyperlink  hyperlink = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        hyperlink.setAddress("https://baidurank.aizhan.com/baidu/www.zhihu.com/question/0/2/position/1/");
        likeCell.setHyperlink(hyperlink);
        // 点击进行跳转
        likeCell.setCellValue("1");

        /* 设置为超链接的样式*/
        XSSFCellStyle linkStyle = workbook.createCellStyle();
        XSSFFont cellFont = workbook.createFont();
        cellFont.setUnderline((byte) 1);
        cellFont.setColor(HSSFColor.BLUE.index);
        linkStyle.setFont(cellFont);
        likeCell.setCellStyle(linkStyle);

        /* 建立第二个sheet对象*/
        XSSFSheet sheet2 = workbook.createSheet("明细页面");  //建立新的sheet对象
        for (int i = 0; i < 30; i++) {
            XSSFRow row2 = sheet2.createRow((short)i);
            XSSFCell cell2 = row2.createCell((short)0);
            cell2.setCellValue("测试第"+(i+1)+"行");
        }

        /* 输出文件*/
        FileOutputStream fileOut = new FileOutputStream("D:\\汇总和明细.xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }
}
