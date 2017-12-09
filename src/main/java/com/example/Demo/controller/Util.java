package com.example.Demo.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

/**
 * Created by hz on 12/8/17.
 */
public class Util {

    public  static List<Map<String, Object>> importEx(String keyName, String keyType, String nullable, FileInputStream inputStream, String origName) {
        Integer startCol = 1;
        Integer startRow = 2;
        List<Map<String, Object>> list = Util.parseExcel(inputStream, origName, keyName, keyType,nullable, startRow, startCol);
        return list;
    }


    public static List<Map<String, Object>> parseExcel(FileInputStream inputStream,String origName,  String keyName, String keyType,String nullable, int startRow, int startCol) {

        String key[] = keyName.split(",");
        String type[] = keyType.toLowerCase().split(",");
        String typeOrg[] = keyType.split(",");
        String[] split = nullable.split(",");
        Workbook workbook = null;

        try {
            if (origName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (origName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                System.out.println("not excel file ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> workbook_lsit = new ArrayList<>();

        //  sheets
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet firstSheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = firstSheet.iterator();
            for (int j = 1; j < startRow; j++) {
                try {
                    rowIterator.next();
                }catch (Exception e){
                    return workbook_lsit;
                }
            }
            // loop  rows
            while (rowIterator.hasNext()) {
                Map<String, Object> rowMap = new HashMap<>();
                Row row = rowIterator.next();
                // cells
                for (int k = 0; k < type.length; k++) {

                    Cell cell = row.getCell(k + startCol, Row.CREATE_NULL_AS_BLANK);
                    String currentType = type[k];

                    switch (currentType) {
                        case "number":
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                rowMap.put(key[k], Integer.parseInt(cell.getStringCellValue()));
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                rowMap.put(key[k], cell.getNumericCellValue());
                            } else {
                                rowMap.put(key[k], 0);
                            }
                            break;
                        case "string":
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String stringCellValue = cell.getStringCellValue();
                                if(ValueUtil.isEmpity(stringCellValue) && "false".equals(split[k])){
                                    rowMap.put("erro", "字段"+key[k]+"不能为空");
                                }
                                rowMap.put("序号", (int) row.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                                rowMap.put(key[k], stringCellValue);
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                double numericCellValue = cell.getNumericCellValue();
                                if(ValueUtil.isEmpity(numericCellValue) && "false".equals(split[k])){
                                    rowMap.put("erro", "字段"+key[k]+"不能为空");
                                }
                                rowMap.put("序号", (int) row.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                                rowMap.put(key[k], (int)cell.getNumericCellValue());
                            } else {
                                String s = split[k];
                                if("false".equals(s)) {
                                    rowMap.put("erro", "字段"+key[k]+"不能为空");
                                }
                                rowMap.put("序号", (int) row.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                                rowMap.put(key[k], " ");
                            }
                            break;
                        case "boolean":
                            if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                                rowMap.put(key[k], cell.getBooleanCellValue());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                rowMap.put(key[k], cell.getStringCellValue());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                double num = cell.getNumericCellValue();
                                if (num == 0) {
                                    rowMap.put(key[k], false);
                                } else {
                                    rowMap.put(key[k], true);
                                }
                            } else {
                                rowMap.put(key[k], false);
                            }
                            break;
                        case "skip":
                            break;
                        default:
                            rowMap.put(key[k], typeOrg[k]);
                            break;
                    }
//    	        	  }
                } // end cells
                System.out.println(ValueUtil.toJson(rowMap));
                workbook_lsit.add(rowMap);
            } // end rows
        } // end sheet
        return workbook_lsit;
    }



}
