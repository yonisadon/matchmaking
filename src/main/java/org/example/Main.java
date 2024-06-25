package org.example;


import java.util.Scanner;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@RestController
//public class Main {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//
//    // קבלת בקשת POST מהפרונט-אנד
//    @PostMapping("/men")
//    public void receiveMenData(@RequestBody Men men) {
//        // כאן תוכל להוסיף את הפעולות שתרצה לבצע עם הנתונים שהתקבלו מהפרונט-אנד
//        // לדוגמה, הכנסת הנתונים למסד נתונים או עיבוד נוסף
//        System.out.println("Received POST request with men data: " + men.toString());
//    }
//}

//public class Main {
//    public static void main(String[] args) {
//
//        FillingInDetails fillingInDetails = new FillingInDetails();
//        fillingInDetails.FillingInDetailsForWebSite();
//        //SpringApplication.run(Main.class, args);
//
//    }
//}


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}


