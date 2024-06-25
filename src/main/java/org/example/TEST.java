//package org.example;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class TEST {
//    public static class FillingInDetails {
//
//        public void FillingInDetailsForWebSite() {
//
//            // יצירת אובייקט מהמחלקה שמייצגת את הנתונים של גברים
//            Scanner scanner = new Scanner(System.in);
//
//    //            System.out.println("personID:");
//    //            int personID = scanner.nextInt();
//    //            scanner.nextLine(); // consume newline
//
//                System.out.println("Enter status:");// single or divorcee
//                String status = scanner.nextLine();
//
//                System.out.println("Enter first name:");
//                String firstName = scanner.nextLine();
//
//                System.out.println("Enter last name:");
//                String lastName = scanner.nextLine();
//
//                System.out.println("Enter age:");
//                int age = scanner.nextInt();
//                scanner.nextLine(); // consume newline
//
//                System.out.println("Enter height:");//גובה
//                float height = scanner.nextFloat();
//                scanner.nextLine(); // consume newline
//
//                System.out.println("Enter location:");
//                String location = scanner.nextLine();
//
//                System.out.println("Enter style:");//סגנון
//                String style = scanner.nextLine();
//
//                System.out.println("Enter seeking:");//מלל חופשי
//                String seeking = scanner.nextLine();
//
//            Men men = new Men(status, firstName, lastName, age, height, location, style, seeking);
//
//            // השמת הערכים של הנתונים של הגברים
//            men.setFirstName(firstName);
//            men.setLastName(lastName);
//            men.setAge(age);
//            men.setLocation(location);
//            men.setStyle(style);
//            men.setSeeking(seeking);
//
//            // קריאה לפונקציה שמכניסה את הרשומה למסד הנתונים
//            DatabaseManager dbManager = new DatabaseManager();
//            dbManager.insertMenRecord(men);
//        }
//    }
//
//    public static class DatabaseManager {
//        private Connection connection;
//        //private String url = "jdbc:mysql://localhost:3306/mydatabase";
//        private String url = "jdbc:mysql://localhost:3306/mydatabase?useUnicode=true&characterEncoding=UTF-8";
//        private String username = "root"; // השם של משתמש במסד הנתונים
//        private String password = "!S054910Yoni"; // הסיסמה של משתמש במסד הנתונים
//
//        // קונסטרקטור
//        public DatabaseManager() {
//            try {
//                connection = DriverManager.getConnection(url, username, password);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // פונקציה להכנסת רשומה חדשה לטבלת הגברים
//        public void insertMenRecord(Men men) {
//            String query = "INSERT INTO men (Status, FirstName, LastName, Age, Height, Location, Style, Seeking) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//            try {
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                //preparedStatement.setInt(1, men.getPersonID());
//                preparedStatement.setString(1, men.getStatus());
//                preparedStatement.setString(2, men.getFirstName());
//                preparedStatement.setString(3, men.getLastName());
//                preparedStatement.setInt(4, men.getAge());
//                preparedStatement.setFloat(5, men.getHeight());
//                preparedStatement.setString(6, men.getLocation());
//                preparedStatement.setString(7, men.getStyle());
//                preparedStatement.setString(8, men.getSeeking());
//
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//        // פונקציה להכנסת רשומה חדשה לטבלת הנשים
//        public void insertWomenRecord(Women women) {
//            String query = "INSERT INTO women (PersonID, FirstName, LastName, Age, Location, Style, Seeking) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            try {
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setInt(1, women.getPersonID());
//                preparedStatement.setString(2, women.getFirstName());
//                preparedStatement.setString(3, women.getLastName());
//                preparedStatement.setInt(4, women.getAge());
//                preparedStatement.setString(5, women.getLocation());
//                preparedStatement.setString(6, women.getStyle());
//                preparedStatement.setString(7, women.getSeeking());
//
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public static class Women {
//
//        private int personID;
//        private String firstName;
//        private String lastName;
//        private int age;
//        private String location;
//        private String style;
//        private String seeking;
//
//        // קונסטרקטור
//        public Women(int personID, String firstName, String lastName, int age, String location, String style, String seeking) {
//            this.personID = personID;
//            this.firstName = firstName;
//            this.lastName = lastName;
//            this.age = age;
//            this.location = location;
//            this.style = style;
//            this.seeking = seeking;
//        }
//
//        // Getters ו- Setters
//        public int getPersonID() {
//            return personID;
//        }
//
//        public void setPersonID(int personID) {
//            this.personID = personID;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public int getAge() {
//            return age;
//        }
//
//        public void setAge(int age) {
//            this.age = age;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//
//        public String getStyle() {
//            return style;
//        }
//
//        public void setStyle(String style) {
//            this.style = style;
//        }
//
//        public String getSeeking() {
//            return seeking;
//        }
//
//        public void setSeeking(String seeking) {
//            this.seeking = seeking;
//        }
//    }
//
//    public static class Men {
//
//            private int personID;
//            private String status;
//            private String firstName;
//            private String lastName;
//            private int age;
//            private float height;
//            private String location;
//            private String style;
//            private String seeking;
//
//            // קונסטרקטור
//            public Men(String status, String firstName, String lastName, int age,float height, String location, String style, String seeking) {
//                //this.personID = personID;
//                this.status = status;
//                this.firstName = firstName;
//                this.lastName = lastName;
//                this.age = age;
//                this.height = height;
//                this.location = location;
//                this.style = style;
//                this.seeking = seeking;
//            }
//
//            // Getters ו- Setters
//            public int getPersonID() {
//                return personID;
//            }
//
//            public void setPersonID(int personID) {
//                this.personID = personID;
//            }
//
//            public String getStatus() {
//            return status;
//        }
//
//            public void setStatus(String status) {
//            this.status = status;
//        }
//
//            public String getFirstName() {
//                return firstName;
//            }
//
//            public void setFirstName(String firstName) {
//                this.firstName = firstName;
//            }
//
//            public String getLastName() {
//                return lastName;
//            }
//
//            public void setLastName(String lastName) {
//                this.lastName = lastName;
//            }
//
//            public int getAge() {
//                return age;
//            }
//
//            public float getHeight() {
//                return height;
//            }
//
//            public void setHeight(float height) {
//                this.height = height;
//            }
//
//            public void setAge(int age) {
//                this.age = age;
//            }
//
//            public String getLocation() {
//                return location;
//            }
//
//            public void setLocation(String location) {
//                this.location = location;
//            }
//
//            public String getStyle() {
//                return style;
//            }
//
//            public void setStyle(String style) {
//                this.style = style;
//            }
//
//            public String getSeeking() {
//                return seeking;
//            }
//
//            public void setSeeking(String seeking) {
//                this.seeking = seeking;
//            }
//        }
//}
