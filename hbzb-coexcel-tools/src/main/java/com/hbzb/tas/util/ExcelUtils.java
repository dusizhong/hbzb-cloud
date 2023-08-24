package com.hbzb.tas.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel Utils
 * @author dusizhong
 * @since 2020-08-28
 */
public class ExcelUtils {


    public static JSONObject readExcel(String filePath) {
        JSONObject jsonTable = new JSONObject();
        File file = new File(filePath);
        jsonTable.put("fileName", file.getName());
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 遍历sheet
            JSONArray jsonSheets = new JSONArray();
//            for(int n=0; n<workbook.getNumberOfSheets(); n++) {
            for(int n=0; n<1; n++) {
                Sheet sheet = workbook.getSheetAt(n);
                int totalRows = sheet.getLastRowNum();
                int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
                // 遍历row
                JSONArray myRows = new JSONArray();
                for(int i=0; i<=totalRows; i++) {
                    Row row = sheet.getRow(i);
                    JSONObject myRow = new JSONObject();
                    myRow.put("rowNum", row.getRowNum());
                    // 遍历cell
                    JSONArray myCells = new JSONArray();
                    for(Cell cell : row) {
                        JSONObject myCell = new JSONObject();
                        myCell.put("position", cell.getAddress().toString());
                        myCell.put("value", cell.toString());
                        // 设置计算列
                        if(cell.toString().equals("单位工程费汇总表")) {
                            if(cell.toString().equals("金额\n(元)")) {

                            }
                        }
                        myCells.add(myCell);
                    }
                    myRow.put("row", myCells);
                    myRows.add(myRow);
                }
                JSONObject jsonSheet = new JSONObject();
                jsonSheet.put("name", sheet.getSheetName());
                jsonSheet.put("totalRows", totalRows);
                jsonSheet.put("totalCols", totalCols);
                jsonSheet.put("content", myRows);
                jsonSheets.add(jsonSheet);
            }
            jsonTable.put("sheets", jsonSheets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonTable;
    }

    public static Map<Integer, List<String>> readExcel2(String filePath) {

        Map<Integer, List<String>> data = new HashMap<>();
        try {
            // 打开指定位置的Excel文件
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            // 打开Excel中的第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 读取Sheet中的数据
            int i = 0;
            for (Row row : sheet) { // 行
                data.put(i, new ArrayList<>());
                for (Cell cell : row) { // 单元格
//                    System.out.println(cell.getCellStyle().getBorderBottom().name());
                    switch (cell.getCellType()) {
                        case STRING:
                            data.get(i).add(cell.getAddress() + cell.getRichStringCellValue().getString());
                            break; // 字符串类型
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data.get(i).add(cell.getDateCellValue().toString());
                            } else {
                                System.out.println(cell.getAddress());
                                data.get(i).add(String.valueOf(cell.getNumericCellValue()));
                            }
                            break; // 数值类型
                        case BOOLEAN:
                            data.get(i).add(String.valueOf(cell.getBooleanCellValue()));
                            break; // 布尔类型
                        case FORMULA:
                            data.get(i).add(cell.getCellFormula());
                            break; // 公式类型
                        case BLANK:
                            data.get(i).add("");
                            break; // 空白类型
                    }
                }
                i++;
            }
            file.close();
            // 显示结果
            System.out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static ArrayList<List> readExcel3(String filePath) {
        ArrayList<List> list = new ArrayList<>();
        try {
            // 打开指定位置的Excel文件
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            // 打开Excel中的第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 读取Sheet中的数据
            for (Row row : sheet) { // 行
                ArrayList<String> item = new ArrayList<>();
                for (Cell cell : row) { // 单元格
//                    System.out.println(cell.getCellStyle().getBorderBottom().name());
                    switch (cell.getCellType()) {
                        case STRING:
                            item.add(cell.getAddress() + cell.getRichStringCellValue().getString());
                            break; // 字符串类型
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                item.add(cell.getDateCellValue().toString());
                            } else {
                                item.add(String.valueOf(cell.getNumericCellValue()));
                            }
                            break; // 数值类型
                        case BOOLEAN:
                            item.add(String.valueOf(cell.getBooleanCellValue()));
                            break; // 布尔类型
                        case FORMULA:
                            item.add(cell.getCellFormula());
                            break; // 公式类型
                        case BLANK:
                            item.add("");
                            break; // 空白类型
                    }
                }
                list.add(item);
            }
            file.close();
            // 显示结果
//            System.out.println(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static void compareExcel(String sourceFilePath, String targetFilePath) {

        System.out.println("开始读取文件......");
        JSONObject sourceFile = ExcelUtils.readExcel(sourceFilePath);
        JSONObject targetFile = ExcelUtils.readExcel(targetFilePath);
        System.out.println("源文件：" + sourceFile);
        System.out.println("查文件：" + targetFile);
        // if(!targetFile.get("fileName").equals(sourceFile.get("fileName"))) System.err.println("文件名不符！");
        System.out.println("完成");

        System.out.println("开始检查表格......");
        Boolean vaild = true;
        JSONArray sourceSheets = sourceFile.getJSONArray("sheets");
        JSONArray targetSheets = targetFile.getJSONArray("sheets");
        if(sourceSheets.size() != targetSheets.size()) System.err.println("sheet数不符！");

        for(int n=0; n<sourceSheets.size(); n++) {
            JSONObject sourceSheet = (JSONObject) sourceSheets.get(n);
            JSONObject targetSheet = (JSONObject) targetSheets.get(n);
            System.out.println("开始检查：" + sourceSheet.get("name"));
            if(!targetSheet.get("name").equals(sourceSheet.get("name"))) {
                System.err.println("sheet名称不符");
                vaild = false;
            }
            if(!targetSheet.get("totalRows").equals(sourceSheet.get("totalRows"))) {
                System.err.println("行数不符");
                vaild = false;
            }
            if(!targetSheet.get("totalCols").equals(sourceSheet.get("totalCols"))) {
                System.err.println("列数不符");
                vaild = false;
            }
            if(sourceSheet.getString("name").contains("单位工程费汇总表")) {
                System.out.println("检查公式：F=G+I+J");
            }
            // 检查内容
            JSONArray sourceContent = sourceSheet.getJSONArray("content");
            JSONArray targetContent = targetSheet.getJSONArray("content");
            for(int i=0; i<sourceContent.size(); i++) {
                JSONArray sourceRow = sourceContent.getJSONArray(i);
                JSONArray targetRow = targetContent.getJSONArray(i);
//                System.err.println(sourceRow);

                if(!targetRow.equals(sourceRow)) {
                    System.err.println(targetRow);
                    System.err.println(sourceRow);
                    System.err.println("检查失败" + (i+1));
                }

//                JSONObject sourceRow = sourceContent.getJSONObject(i);
//                JSONObject targetRow = targetContent.getJSONObject(i);
//                // 检查cell
//                for(int j=0; j<sourceRow.size(); j++) {
//                    if(sourceRow.get(""))i
//                }
//                if(!targetRow.equals(sourceRow)) {
//                    System.err.println("第行" + i + "不符");
//                }
            }
        }
        if(vaild) System.out.println("校验通过");
        else System.err.println("校验未通过");
    }




    public static JSONObject readSourceFile(String filePath) {
        JSONObject myFile = new JSONObject();
        File file = new File(filePath);
        myFile.put("fileName", file.getName());
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 遍历sheet
            JSONArray mySheets = new JSONArray();
//            for(int n=0; n<workbook.getNumberOfSheets(); n++) {
            for(int n=0; n<1; n++) {
                Sheet sheet = workbook.getSheetAt(n);
                // 遍历row
                JSONArray myRows = new JSONArray();
                for(Row row : sheet) {
                    JSONObject myRow = new JSONObject();
                    myRow.put("rowNum", row.getRowNum()+1);
                    // 遍历cell
                    JSONArray myCells = new JSONArray();
                    for(Cell cell : row) {
                        JSONObject myCell = new JSONObject();
                        myCell.put("position", cell.getAddress().toString());
//                        myCell.put("rowIndex", cell.getRowIndex());
//                        myCell.put("colIndex", cell.getColumnIndex());
                        myCell.put("value", cell.toString());
                        if(StringUtils.isEmpty(cell.toString())) myCell.put("needCheck", false);
                        else myCell.put("needCheck", true);
                        myCells.add(myCell);
                    }
                    myRow.put("cells", myCells);
                    myRows.add(myRow);
                }
                JSONObject mySheet = new JSONObject();
                mySheet.put("name", sheet.getSheetName());
                mySheet.put("content", myRows);
                mySheets.add(mySheet);
            }
            myFile.put("sheets", mySheets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myFile;
    }

    public static JSONObject readTargetFile(String filePath) {
        JSONObject myFile = new JSONObject();
        File file = new File(filePath);
        myFile.put("fileName", file.getName());
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            // 遍历sheet
            JSONArray mySheets = new JSONArray();
//            for(int n=0; n<workbook.getNumberOfSheets(); n++) {
            for(int n=0; n<1; n++) {
                Sheet sheet = workbook.getSheetAt(n);
                // 计算列定位
                Integer jeColIndex = -1;
                Integer rgfColIndex = -1;
                Integer clfColIndex = -1;
                Integer jxfColIndex = -1;
                // 遍历row
                JSONArray myRows = new JSONArray();
                for(Row row : sheet) {
                    JSONObject myRow = new JSONObject();
                    myRow.put("rowNum", row.getRowNum()+1);
                    // 遍历cell
                    JSONArray myCells = new JSONArray();
                    for(Cell cell : row) {
                        JSONObject myCell = new JSONObject();
                        myCell.put("position", cell.getAddress().toString());
                        myCell.put("value", cell.toString());
                        // 设置计算项  金额（元） = 人工费+材料费+机械费
                        if(sheet.getSheetName().contains("单位工程费汇总表")) {
                            // 获取计算项列值
                            if(cell.toString().contains("金额")) {
                                jeColIndex = cell.getColumnIndex();
                            } else if(cell.toString().equals("人工费")) {
                                rgfColIndex = cell.getColumnIndex();
                            } else if(cell.toString().equals("材料费")) {
                                clfColIndex = cell.getColumnIndex();
                            } else if(cell.toString().equals("机械费")) {
                                jxfColIndex = cell.getColumnIndex();
                            }
                            // 添加计算项
                            if(!cell.toString().contains("金额") && cell.getColumnIndex() == jeColIndex) myCell.put("item", "金额");
                            if(!cell.toString().equals("人工费") && cell.getColumnIndex() == rgfColIndex) myCell.put("item", "人工费");
                            if(!cell.toString().equals("材料费") && cell.getColumnIndex() == clfColIndex) myCell.put("item", "材料费");
                            if(!cell.toString().equals("机械费") && cell.getColumnIndex() == jxfColIndex) myCell.put("item", "机械费");
                        }
                        myCells.add(myCell);
                    }
                    myRow.put("cells", myCells);
                    myRows.add(myRow);
                }
                JSONObject mySheet = new JSONObject();
                mySheet.put("name", sheet.getSheetName());
                mySheet.put("content", myRows);
                mySheets.add(mySheet);
            }
            myFile.put("sheets", mySheets);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myFile;
    }

    public static void compareExcel2(String sourceFilePath, String targetFilePath) {

        System.out.println("开始读取文件......");
        JSONObject sourceFile = ExcelUtils.readSourceFile(sourceFilePath);
        JSONObject targetFile = ExcelUtils.readTargetFile(targetFilePath);
//        System.out.println("源文件：" + sourceFile);
//        System.out.println("目标文件：" + targetFile);
        System.out.println("读取完成");
        System.out.println("开始检查表格......");
        JSONArray sourceSheets = sourceFile.getJSONArray("sheets");
        JSONArray targetSheets = targetFile.getJSONArray("sheets");
        if(targetSheets.size() != sourceSheets.size()) System.err.println("sheet数量不符！");
        for(int n=0; n<sourceSheets.size(); n++) {
            // 检查sheet
            JSONObject sourceSheet = sourceSheets.getJSONObject(n);
            JSONObject targetSheet = targetSheets.getJSONObject(n);
            if(!targetSheet.get("name").equals(sourceSheet.get("name"))) System.err.println("第" + (n+1) + "个sheet名称不符！");
            // 检查row
            JSONArray sourceContent = sourceSheet.getJSONArray("content");
            JSONArray targetContent = targetSheet.getJSONArray("content");
            if(targetContent.size() != sourceContent.size()) System.err.println(targetSheet.get("name") + "总行数不符！");
            for(int i=0; i<sourceContent.size(); i++) {
                JSONObject sourceRow = sourceContent.getJSONObject(i);
                JSONObject targetRow = targetContent.getJSONObject(i);
                JSONArray sourceCells = sourceRow.getJSONArray("cells");
                JSONArray targetCells = targetRow.getJSONArray("cells");
                if(targetCells.size() != sourceCells.size()) System.err.println(targetSheet.get("name")+ "的第" + (i+1) +"行的列数不符！");

                float je = 0;
                float rgf = 0;
                float clf = 0;
                float jxf = 0;
                // 检查cell
                for(int j=0; j<sourceCells.size(); j++) {
                    JSONObject sourceCell = sourceCells.getJSONObject(j);
                    JSONObject targetCell = targetCells.getJSONObject(j);
                    if(sourceCell.get("needCheck").equals(true)) {
                        if(!targetCell.get("position").equals(sourceCell.get("position"))) System.err.println(targetSheet.get("name") + "的第" + i +"行的第" + j +"列的position不符！");
                        if(!targetCell.get("value").equals(sourceCell.get("value"))) System.err.println(targetSheet.get("name") + targetCell.getString("position") +"的value不符！");
                    }
                    // 检查计算
                    if(targetCell.get("item") != null && !StringUtils.isEmpty(targetCell.getString("value")) && !"/".equals(targetCell.getString("value"))) {
                        if (targetCell.getString("item").contains("金额")) {
                            je = Float.valueOf(targetCell.getString("value"));
                        } else if (targetCell.getString("item").equals("人工费")) {
                            rgf = Float.valueOf(targetCell.getString("value"));
                        } else if (targetCell.getString("item").equals("材料费")) {
                            clf = Float.valueOf(targetCell.getString("value"));
                        } else if (targetCell.getString("item").equals("机械费")) {
                            jxf = Float.valueOf(targetCell.getString("value"));
                        }
                    }
                }
                if(je != rgf+clf+jxf) {
                    System.out.print(je + " | ");
                    System.out.println(rgf+clf+jxf);
                    System.err.println(targetSheet.get("name") + "第" + targetRow.getString("rowNum") + "行计算有误！");
                }
            }
        }
        System.out.println("检查完成");
    }


    public void createExcel() {

        Workbook workbook = new XSSFWorkbook(); // 创建工作簿
        Sheet sheet = workbook.createSheet("Persons"); // 创建Sheet
        sheet.setColumnWidth(1, 4000);
        Row header = sheet.createRow(0); // 创建表头行

        CellStyle headerStyle = workbook.createCellStyle(); // 表头单元格样式
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont(); // 字体样式
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0); // 创建表头单元格
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1); // 创建表头单元格
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle(); // 普通单元格样式
        style.setWrapText(true);

        Row row = sheet.createRow(2); // 写入单元格
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1); // 写入单元格
        cell.setCellValue(20);
        cell.setCellStyle(style);

        // 最后写出到文件
        try {
            File file = new File("d:\\" + System.currentTimeMillis() + ".xlsx");
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) {
//        JSONObject table = ExcelUtils.readSourceFile("d:\\电气.xlsx");
//        System.out.println(table);
//        JSONObject table1 = ExcelUtils.readTargetFile("d:\\电气1.xlsx");
//        System.out.println(table1);
//        Map<Integer, List<String>> table2 = ExcelUtils.readExcel2("d:\\电气1.xlsx");
//        System.out.println(table2);
        ExcelUtils.compareExcel2("d:\\电气.xlsx", "d:\\电气1.xlsx");
    }
}
