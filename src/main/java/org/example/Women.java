package org.example;

public class Women {

    private int personID;
    private String firstName;
    private String lastName;
    private int age;
    private String location;
    private String style;
    private String seeking;

    // קונסטרקטור
    public Women(int personID, String firstName, String lastName, int age, String location, String style, String seeking) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.location = location;
        this.style = style;
        this.seeking = seeking;
    }

    // Getters ו- Setters
    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeeking() {
        return seeking;
    }

    public void setSeeking(String seeking) {
        this.seeking = seeking;
    }
}
