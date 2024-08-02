package org.example.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "women")
public class Woman extends Person {

    public Woman() {
        super();
    }

    // קונסטרוקטור של האישה
    public Woman(int id, String status, String firstName, String lastName, int age, float height, String location, String style, String seeking,
                 String community, String headCovering, String device) {
        super(id, status, firstName, lastName, age, height, location, style, seeking, community, headCovering, device);
    }
}
