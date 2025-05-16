package data;

import models.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ExcelWriter {
    private static final String FILEPATH = "journal/resources/TestData.xlsx";

    private static XSSFWorkbook loadWorkbook() throws IOException {
        File file = new File(FILEPATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                return new XSSFWorkbook(fis);
            }
        } else {
            return new XSSFWorkbook();
        }
    }

    public static void saveAllDataToExcel() {

        try (XSSFWorkbook workbook = loadWorkbook()) {
            int index = workbook.getSheetIndex("Scores");
            if (index != -1) {
                workbook.removeSheetAt(index);
            }

            saveScoresToSheet(workbook);

            try (FileOutputStream outputStream = new FileOutputStream(FILEPATH)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении данных в Excel", e);
        }
    }

    private static void saveScoresToSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Scores");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("ID студента");
        headerRow.createCell(2).setCellValue("ID предмета");
        headerRow.createCell(3).setCellValue("Оценка");

        int rowNum = 1;
        for (Score score : DataManager.getScores().values()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(score.getId());
            row.createCell(1).setCellValue(score.getStudentId());
            row.createCell(2).setCellValue(score.getSubjectId());
            row.createCell(3).setCellValue(score.getScore());
        }
    }
}