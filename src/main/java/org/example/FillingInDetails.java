package org.example;

import java.util.Scanner;

public class FillingInDetails {

    public void FillingInDetailsForWebSite() {

        // יצירת אובייקט מהמחלקה שמייצגת את הנתונים של גברים
        Scanner scanner = new Scanner(System.in);

//            System.out.println("personID:");
//            int personID = scanner.nextInt();
//            scanner.nextLine(); // consume newline

            System.out.println("Enter status:");// single or divorcee
            String status = scanner.nextLine();

            System.out.println("Enter first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter age:");
            int age = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Enter height:");//גובה
            float height = scanner.nextFloat();
            scanner.nextLine(); // consume newline

            System.out.println("Enter location:");
            String location = scanner.nextLine();

            System.out.println("Enter style:");//סגנון
            String style = scanner.nextLine();

            System.out.println("Enter seeking:");//מלל חופשי
            String seeking = scanner.nextLine();

        Men men = new Men(status, firstName, lastName, age, height, location, style, seeking);

        // השמת הערכים של הנתונים של הגברים
        men.setFirstName(firstName);
        men.setLastName(lastName);
        men.setAge(age);
        men.setLocation(location);
        men.setStyle(style);
        men.setSeeking(seeking);

        // קריאה לפונקציה שמכניסה את הרשומה למסד הנתונים
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.insertMenRecord(men);
    }
}

