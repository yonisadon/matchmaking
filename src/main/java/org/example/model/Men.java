package org.example.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "men")
public class Men extends Person {

    public Men() {
        super();
    }

    public Men(String status, String firstName, String lastName, int age, float height, String location, String style, String seeking) {
        super(status, firstName, lastName, age, height, location, style, seeking);
    }
}


