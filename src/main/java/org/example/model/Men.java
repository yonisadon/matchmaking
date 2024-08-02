package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "men")
public class Men extends Person{

//    @OneToMany(mappedBy = "men", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private Set<PreferencesMen> preferences = new HashSet<>();
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private int id;
//
//    private String status;
//    @Column(name = "first_name")
//    private String firstName;
//    @Column(name = "last_name")
//    private String lastName;
//    private int age;
//    private float height;
//    private String location;
//    private String style;
//    private String seeking;
//
//    public Men() {
//        super();
//    }
//
//    public Men( String status, String firstName, String lastName, int age, float height, String location, String style, String seeking) {
//        //this.id = id;
//        this.status = status;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.height = height;
//        this.location = location;
//        this.style = style;
//        this.seeking = seeking;
//    }

    @OneToMany(mappedBy = "men", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PreferencesMen> preferences = new HashSet<>();

    public Men() {
        super();
    }

    public Men(String status, String firstName, String lastName, int age, float height, String location, String style, String seeking,
               String community, String headCovering, String device) {
        super(0, status, firstName, lastName, age, height, location, style, seeking, community, headCovering, device);
    }

    // Getters ו- Setters


//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public float getHeight() {
//        return height;
//    }
//
//    public void setHeight(float height) {
//        this.height = height;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getStyle() {
//        return style;
//    }
//
//    public void setStyle(String style) {
//        this.style = style;
//    }
//
//    public String getSeeking() {
//        return seeking;
//    }
//
//    public void setSeeking(String seeking) {
//        this.seeking = seeking;
//    }
//
    public Set<PreferencesMen> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<PreferencesMen> preferences) {
        this.preferences = preferences;
    }
}






