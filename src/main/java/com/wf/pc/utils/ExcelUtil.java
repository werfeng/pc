package com.wf.pc.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wf.pc.common.UrlConstant;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class ExcelUtil {
    public static void output(JSONArray array, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xlsx");
        try(OutputStream outputStream = response.getOutputStream()) {
            ClassPathResource resource = new ClassPathResource("excel\\baiduzhishu.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());

            XSSFSheet sheet = workbook.getSheetAt(0);

            /* 设置为超链接的样式*/
            XSSFCellStyle linkStyle = workbook.createCellStyle();
            XSSFFont cellFont = workbook.createFont();
            cellFont.setUnderline((byte) 1);
            cellFont.setColor(HSSFColor.BLUE.index);

            XSSFCreationHelper creationHelper = workbook.getCreationHelper();

            for (int j = 0; j < array.size(); j++) {
                XSSFRow row = sheet.createRow(j + 1);
                JSONObject object = array.getJSONObject(j);
                for (int i = 0; i < 5; i++) {
                    String text = object.getString(UrlConstant.VK + i);
                    String href = object.getString(UrlConstant.VKH + i);
                    //创建单元格
                    XSSFCell likeCell = row.createCell(i);
                    //判断是否包含超链接
                    if (StringUtils.isNotEmpty(href)) {
                        XSSFHyperlink hyperlink = creationHelper.createHyperlink(XSSFHyperlink.LINK_URL);
                        hyperlink.setAddress(href);
                        likeCell.setHyperlink(hyperlink);
                        linkStyle.setFont(cellFont);
                        likeCell.setCellStyle(linkStyle);
                    }
                    likeCell.setCellValue(text);
                }
            }
            //输出
            workbook.write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
