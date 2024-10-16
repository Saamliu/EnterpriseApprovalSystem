package org.example.util;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil {
    public static void excelExport(HttpServletResponse resp, String excelName, Class<?> clazz, String sheetName, List<?> list) throws IOException {
        // 写入到磁盘, 第一个参数给的是磁盘路径的字符串
        // EasyExcel.write("磁盘路径");
        // 设置响应的内容内省为excel
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setCharacterEncoding("utf-8");
        // 设置下载的文件名 (为了防止中文乱码，这里用URLEncoder.encode()对文件名进行编码)
        String fileName = URLEncoder.encode(excelName, "UTF-8").replaceAll("\\+", "%20");
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 写入到网络, 返回给前端, 写入到resp对象的输出流里面 (这样前端可以在他的输入流里面下载这个excel文件)
        EasyExcel.write(resp.getOutputStream(), clazz)
                .sheet(sheetName)
                .doWrite(list);
    }
}
