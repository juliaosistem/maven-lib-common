package com.juliaosystem.utils.excel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * clase para leer documentos excel
 * @author daniel juliao
 * @version 1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ExcelReaderUtil  {


        public static List<List<String>> readExcelFile(MultipartFile file) throws  IOException  {
            List<List<String>> data = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> rowData = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        default:
                            break;
                    }
                }
                data.add(rowData);
            }
            workbook.close();

            return data;
        }


}
