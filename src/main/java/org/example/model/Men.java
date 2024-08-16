package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "men")
public class Men extends Person{


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

    public Set<PreferencesMen> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<PreferencesMen> preferences) {
        this.preferences = preferences;
    }
}






