package org.example.service;
//
//import org.apache.poi.ss.usermodel.*;
//
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.example.job.MatchResult;
//import org.springframework.stereotype.Service;
//import java.io.ByteArrayOutputStream;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class MatchService {
//
////    public void exportMatchesToExcel(List<MatchResult> matchResults, String fileName) throws IOException {
//public void exportMatchesToExcel(List<MatchResult> matchResults, ByteArrayOutputStream outputStream) throws IOException {
//
//    Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Matches");
//
//    // יצירת כותרת
//    Row headerRow = sheet.createRow(0);
//    headerRow.createCell(0).setCellValue("Entity");
//    headerRow.createCell(1).setCellValue("Matches");
//
//    // הוספת תוצאות התאמות לדפים
//    int rowNum = 1;
//    for (MatchResult result : matchResults) {
//        Row row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(result.getEntity()); // שם הגבר
//        row.createCell(1).setCellValue(String.join(", ", result.getMatches())); // שמות הנשים
//    }
//
//    // כתיבה לקובץ ByteArrayOutputStream
//    workbook.write(outputStream);
//    workbook.close();
//}
//}
////        int rowNum = 0;
////        Row headerRow = sheet.createRow(rowNum++);
////        headerRow.createCell(0).setCellValue("Entity");
////        headerRow.createCell(1).setCellValue("Matches");
////
////        for (MatchResult result : matchResults) {
////            Row row = sheet.createRow(rowNum++);
////            row.createCell(0).setCellValue(result.getEntity().toString());
////            row.createCell(1).setCellValue(result.getMatches().toString());
////        }
////
////        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
////            workbook.write(fileOut);
////        }
////    }
//}
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.example.DTO.MatchDto;
import org.example.DTO.MatchDtoForMen;
import org.example.DTO.MatchDtoForWomen;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelExportService {

    public void generateExcelReport(List<MatchDto> matches, ByteArrayOutputStream outputStream, String type) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("דוח התאמות");
        sheet.setRightToLeft(true); // הפיכת הגיליון לימין לשמאל

        // יצירת סגנון כללי
        XSSFCellStyle headerStyle = createHeaderStyle(workbook);

        // יצירת כותרות עמודות על בסיס סוג הדוח
        XSSFRow headerRow = sheet.createRow(0);
        if (Objects.equals(type, "men")) {
            createMenHeaders(headerRow, headerStyle);
        } else if (Objects.equals(type, "women")) {
            createWomenHeaders(headerRow, headerStyle);
        }

        // מילוי נתונים לפי סוג הדוח
        int rowNum = 1;
        for (MatchDto match : matches) {
            XSSFRow row = sheet.createRow(rowNum++);
            fillRowData(row, match, type);
        }

        // התאמת גודל עמודות
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }

        // שמירת הדוח ל-OutputStream
        workbook.write(outputStream);
        workbook.close();
    }

    // יצירת כותרות לגברים
    private void createMenHeaders(XSSFRow headerRow, XSSFCellStyle style) {
        headerRow.createCell(0).setCellValue("מזהה גבר");
        headerRow.createCell(1).setCellValue("שם גבר");
        headerRow.createCell(2).setCellValue("משפחה גבר");
        headerRow.createCell(3).setCellValue("גיל גבר");
        headerRow.createCell(4).setCellValue("גובה גבר");
        headerRow.createCell(5).setCellValue("סטטוס גבר");
        headerRow.createCell(6).setCellValue("סגנון גבר");
        headerRow.createCell(7).setCellValue("מזהה אישה");
        headerRow.createCell(8).setCellValue("שם אישה");
        headerRow.createCell(9).setCellValue("משפחה אישה");
        headerRow.createCell(10).setCellValue("גיל אישה");
        headerRow.createCell(11).setCellValue("גובה אישה");
        headerRow.createCell(12).setCellValue("סטטוס אישה");
        headerRow.createCell(13).setCellValue("סגנון אישה");

        // החלת הסטייל על הכותרות
        for (int i = 0; i <= 13; i++) {
            headerRow.getCell(i).setCellStyle(style);
        }
    }

    // יצירת כותרות לנשים
    private void createWomenHeaders(XSSFRow headerRow, XSSFCellStyle style) {
        headerRow.createCell(0).setCellValue("מזהה אישה");
        headerRow.createCell(1).setCellValue("שם אישה");
        headerRow.createCell(2).setCellValue("משפחה אישה");
        headerRow.createCell(3).setCellValue("גיל אישה");
        headerRow.createCell(4).setCellValue("גובה אישה");
        headerRow.createCell(5).setCellValue("סטטוס אישה");
        headerRow.createCell(6).setCellValue("סגנון אישה");
        headerRow.createCell(7).setCellValue("מזהה גבר");
        headerRow.createCell(8).setCellValue("שם גבר");
        headerRow.createCell(9).setCellValue("משפחה גבר");
        headerRow.createCell(10).setCellValue("גיל גבר");
        headerRow.createCell(11).setCellValue("גובה גבר");
        headerRow.createCell(12).setCellValue("סטטוס גבר");
        headerRow.createCell(13).setCellValue("סגנון גבר");

        // החלת הסטייל על הכותרות
        for (int i = 0; i <= 13; i++) {
            headerRow.getCell(i).setCellStyle(style);
        }
    }

    // מילוי נתונים לשורה
    private void fillRowData(XSSFRow row, MatchDto match, String type) {
        if (Objects.equals(type, "men")) {
            MatchDtoForMen men = match.getMen();

            row.createCell(0).setCellValue(men.getMenId());
            row.createCell(1).setCellValue(men.getMenFirstName());
            row.createCell(2).setCellValue(men.getMenLastName());
            row.createCell(3).setCellValue(men.getMenAge());
            row.createCell(4).setCellValue(men.getMenHeight());
            row.createCell(5).setCellValue(men.getMenStatus());
            row.createCell(6).setCellValue(men.getMenStyle());
            if (match.getWomen() != null) {
                MatchDtoForWomen women = match.getWomen();
                row.createCell(7).setCellValue(women.getWomenId());
                row.createCell(8).setCellValue(women.getWomenFirstName());
                row.createCell(9).setCellValue(women.getWomenLastName());
                row.createCell(10).setCellValue(women.getWomenAge());
                row.createCell(11).setCellValue(women.getWomenHeight());
                row.createCell(12).setCellValue(women.getWomenStatus());
                row.createCell(13).setCellValue(women.getWomenStyle());
            }
        } else if (Objects.equals(type, "women")) {
            MatchDtoForWomen women = match.getWomen();
            row.createCell(0).setCellValue(women.getWomenId());
            row.createCell(1).setCellValue(women.getWomenFirstName());
            row.createCell(2).setCellValue(women.getWomenLastName());
            row.createCell(3).setCellValue(women.getWomenAge());
            row.createCell(4).setCellValue(women.getWomenHeight());
            row.createCell(5).setCellValue(women.getWomenStatus());
            row.createCell(6).setCellValue(women.getWomenStyle());

            if (match.getMen() != null) {
                MatchDtoForMen men = match.getMen();
                row.createCell(7).setCellValue(men.getMenId());
                row.createCell(8).setCellValue(men.getMenFirstName());
                row.createCell(9).setCellValue(men.getMenLastName());
                row.createCell(10).setCellValue(men.getMenAge());
                row.createCell(11).setCellValue(men.getMenHeight());
                row.createCell(12).setCellValue(men.getMenStatus());
                row.createCell(13).setCellValue(men.getMenStyle());
            }
        }
    }

    // יצירת סטייל לכותרות
    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT); // יישור מימין לשמאל
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Arial");
        style.setFont(font);

        return style;
    }


//    public void generateExcelReport(List<MatchDto> matches, ByteArrayOutputStream outputStream, String type) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("דוח-התאמות");
//        XSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.RIGHT);  // יישור מימין לשמאל
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        sheet.setRightToLeft(true);
//        // יצירת פונט
//        XSSFFont font = workbook.createFont();
//        font.setBold(true); // להפוך את הטקסט למודגש
//        font.setFontHeightInPoints((short) 12); // גודל הפונט
//        font.setFontName("Arial"); // שם הפונט (לדוגמה Arial)
//        style.setFont(font); // שייכת הפונט לסטייל
//
//        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        // יצירת כותרות עמודות
//        XSSFRow headerRow = sheet.createRow(0);
//
//
//        if (Objects.equals(type, "men")) {
//
//            headerRow.createCell(0).setCellValue("מזהה ID");
//            headerRow.createCell(1).setCellValue("שם גבר");
//            headerRow.createCell(2).setCellValue("משפחה גבר");
//            headerRow.createCell(3).setCellValue("גיל גבר");
//            headerRow.createCell(4).setCellValue("גובה גבר");
//            headerRow.createCell(5).setCellValue("סטטוס גבר");
//            headerRow.createCell(6).setCellValue("סגנון גבר");
//            headerRow.createCell(7).setCellValue("מזהה ID אישה");
//            headerRow.createCell(8).setCellValue("שם אישה");
//            headerRow.createCell(9).setCellValue("משפחה אישה");
//            headerRow.createCell(10).setCellValue("גיל אישה");
//            headerRow.createCell(11).setCellValue("גובה אישה");
//            headerRow.createCell(12).setCellValue("סטטוס אישה");
//            headerRow.createCell(13).setCellValue("סגנון אישה");
//        }
//        if (Objects.equals(type, "women")) {
//            headerRow.createCell(0).setCellValue("מזהה ID אישה");
//            headerRow.createCell(1).setCellValue("שם אישה");
//            headerRow.createCell(2).setCellValue("משפחה אישה");
//            headerRow.createCell(3).setCellValue("גיל אישה");
//            headerRow.createCell(4).setCellValue("גובה אישה");
//            headerRow.createCell(5).setCellValue("סטטוס אישה");
//            headerRow.createCell(6).setCellValue("סגנון אישה");
//            headerRow.createCell(7).setCellValue("מזהה ID גבר");
//            headerRow.createCell(8).setCellValue("שם גבר");
//            headerRow.createCell(9).setCellValue("משפחה גבר");
//            headerRow.createCell(10).setCellValue("גיל גבר");
//            headerRow.createCell(11).setCellValue("גובה גבר");
//            headerRow.createCell(12).setCellValue("סטטוס גבר");
//            headerRow.createCell(13).setCellValue("סגנון גבר");
//
//        }
//
//        // הגדרת היישור לכל הכותרות
//        for (int i = 0; i <= 13; i++) {
//            headerRow.getCell(i).setCellStyle(style); // יישור כותרות מימין לשמאל
//            System.out.println("Header Column " + i + ": " + headerRow.getCell(i).getStringCellValue());
//
//        }
//        System.out.println(matches);
//        // הוספת נתונים לכל שורה
//        int rowNum = 1;
//        for (MatchDto match : matches) {
//            XSSFRow row = sheet.createRow(rowNum++);
//            System.out.println("Men: " + match.getMenFirstName() + ", Women: " + match.getWomenFirstName());
//            // הגדרת סטייל של מימין לשמאל לתאים
//            XSSFCellStyle dataStyle = workbook.createCellStyle();
//            dataStyle.setAlignment(HorizontalAlignment.RIGHT);  // יישור מימין לשמאל
//            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            if (Objects.equals(type, "women")) {
//
//                row.createCell(0).setCellValue(match.getWomenId());
//                row.createCell(1).setCellValue(match.getWomenFirstName());
//                row.createCell(2).setCellValue(match.getWomenLastName());
//                row.createCell(3).setCellValue(match.getWomenAge());
//                row.createCell(4).setCellValue(match.getWomenHeight());
//                row.createCell(5).setCellValue(match.getWomenStatus());
//                row.createCell(6).setCellValue(match.getWomenStyle());
//                System.out.println("Adding data for women: " + match.getWomenId() + ", " + match.getWomenFirstName());
//                System.out.println("Adding data for men: " + match.getMenId() + ", " + match.getMenFirstName());
//
//
//                row.createCell(7).setCellValue(match.getMenId());
//                row.createCell(8).setCellValue(match.getMenFirstName());
//                row.createCell(9).setCellValue(match.getMenLastName());
//                row.createCell(10).setCellValue(match.getMenAge());
//                row.createCell(11).setCellValue(match.getMenHeight());
//                row.createCell(12).setCellValue(match.getMenStatus());
//                row.createCell(13).setCellValue(match.getMenStyle());
//            }
//            if (Objects.equals(type, "men")) {
//                row.createCell(0).setCellValue(match.getMenId());
//                row.createCell(1).setCellValue(match.getMenFirstName());
//                row.createCell(2).setCellValue(match.getMenLastName());
//                row.createCell(3).setCellValue(match.getMenAge());
//                row.createCell(4).setCellValue(match.getMenHeight());
//                row.createCell(5).setCellValue(match.getMenStatus());
//                row.createCell(6).setCellValue(match.getMenStyle());
//                row.createCell(7).setCellValue(match.getWomenId());
//                row.createCell(8).setCellValue(match.getWomenFirstName());
//                row.createCell(9).setCellValue(match.getWomenLastName());
//                row.createCell(10).setCellValue(match.getWomenAge());
//                row.createCell(11).setCellValue(match.getWomenHeight());
//                row.createCell(12).setCellValue(match.getWomenStatus());
//                row.createCell(13).setCellValue(match.getWomenStyle());
//                System.out.println("Adding data for women: " + match.getWomenId() + ", " + match.getWomenFirstName());
//                System.out.println("Adding data for women: " + match.getMenId() + ", " + match.getMenFirstName());
//            }
//        }
//
//        // שמירת הדו"ח ל-OutputStream
//        workbook.write(outputStream);
//        workbook.close();
//    }

}



