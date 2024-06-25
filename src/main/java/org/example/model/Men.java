package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "Men") // שם הטבלה כפי שהיא מופיעה במסד הנתונים

public class Men {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;
    @Column(name = "FirstName")
    private String FirstName; // שינוי השם של השדה כך שיתאים למסד הנתונים
    private String lastName;
    private int age;
    private float height;
    private String location;
    private String style;
    private String seeking;

    // קונסטרקטורים, getters ו-setters

    public Men() {}

    public Men(String status, String FirstName, String lastName, int age, float height, String location, String style, String seeking) {
        this.status = status;
        this.FirstName = FirstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.location = location;
        this.style = style;
        this.seeking = seeking;
    }

    // Getters ו- Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

