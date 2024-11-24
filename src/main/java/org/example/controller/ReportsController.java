package org.example.controller;

import org.example.DTO.MatchDto;
import org.example.service.ExcelExportService;
import org.example.service.MenServiceImpl;
import org.example.service.WomenServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/reports")
public class ReportsController {
    private final ExcelExportService excelExportService;
    private final MenServiceImpl menService;
    private final WomenServiceImpl womenService;

    public ReportsController(ExcelExportService excelExportService, MenServiceImpl menService, WomenServiceImpl womenService) {
        this.excelExportService = excelExportService;
        this.menService = menService;
        this.womenService = womenService;
        //this.matchingService = matchingService;
    }

    @GetMapping("/generate-matches-report")
    public ResponseEntity<byte[]> generateMatchesReport(@RequestParam String type) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.out.println(type);
        try {
            List<MatchDto> matches;

            if (type.equalsIgnoreCase("men")) {
                // פנייה לשירות גברים כדי לקבל את נתוני ההתאמה
                matches = menService.findMatchesForMen(); // לא נדרש להוסיף OutputStream פה
                excelExportService.generateExcelReport(matches, outputStream, type); // יצירת הדו"ח וכתיבה ל-OutputStream
            } else if (type.equalsIgnoreCase("women")) {
                // פנייה לשירות נשים כדי ליצור את הדוח
                 matches = womenService.findMatchesForWomen(); // אפשר להוסיף את הלוגיקה הזאת
                excelExportService.generateExcelReport(matches, outputStream, type); // יצירת דו"ח עבור נשים
            } else {
                // אם type לא תקין, מחזירים שגיאה
                return ResponseEntity.badRequest().body("סוג חיפוש לא תקין".getBytes());
            }

            byte[] excelFile = outputStream.toByteArray();

            // מחזירים את קובץ האקסל כתגובה
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=matches_report.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excelFile);

        } catch (Exception e) {
            // במקרה של שגיאה ביצירת הדוח, מחזירים שגיאה
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("שגיאה ביצירת הדוח".getBytes());
        }
    }


}


//    @GetMapping("/generate-matches-report")
//    public ResponseEntity<byte[]> generateMatchesReport(@RequestParam String type) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try {
//            if (type.equalsIgnoreCase("men")) {
//                menService.generateReportForMen(outputStream);
//            } else if (type.equalsIgnoreCase("women")) {
//                //womenService.generateReportForWomen(outputStream);
//            } else {
//                return ResponseEntity.badRequest().body("סוג חיפוש לא תקין".getBytes());
//            }
//
//            byte[] excelFile = outputStream.toByteArray();
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=matches_report.xlsx")
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(excelFile);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("שגיאה ביצירת הדוח".getBytes());
//        }
//    }

//    @GetMapping("/generate-matches-report")
//    public ResponseEntity<String> generateMatchesReport(@RequestParam String type) {

//    @GetMapping("/generate-matches-report")
//    public ResponseEntity<byte[]> generateMatchesReport(@RequestParam String type) throws IOException {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            if (type.equalsIgnoreCase("men")) {
//                //menService.generateReportForMen();
//                menService.generateReportForMen(outputStream);  // מעביר את OutputStream כדי לכתוב אליו
//
//            } else if (type.equalsIgnoreCase("women")) {
//                //womenService.generateReportForWomen();
//                System.out.println("");
//            } else {
//                return ResponseEntity.badRequest().body("סוג חיפוש לא תקין".getBytes());
//            }
//            byte[] excelFile = outputStream.toByteArray();
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=matches_report.xlsx")
//                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                    .body(excelFile);  // שולחים את הקובץ כתגובה
//
//            // שליחת קובץ אקסל לדפדפן
////            return ResponseEntity.ok()
////                    .header("Content-Disposition", "attachment; filename=matches_report.xlsx")
////                    .body(outputStream.toByteArray());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("שגיאה ביצירת הדוח".getBytes());
//        }
//    }

