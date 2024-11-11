package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "women")
public class Women extends Person {


    @OneToMany(mappedBy = "women", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PreferencesWomen> preferencesWomen = new HashSet<>();

    public Women() {
        super();
    }

    // קונסטרוקטור של האישה
    public Women(String status, String firstName, String lastName, int age, float height, String location, String style, String seeking,
                 String community, String headCovering, String device,  LocalDate dateOfBirth, String profilePictureUrl, String additionalPictureUrl,
                 String phone, String work, String studies) {
        super(0, status, firstName, lastName, age, height, location, style, seeking, community, headCovering, device, dateOfBirth ,
                profilePictureUrl, additionalPictureUrl, phone, work, studies);
    }

    public Set<PreferencesWomen> getPreferencesWomen() {
        return preferencesWomen;
    }

    public void setPreferencesWomen(Set<PreferencesWomen> preferencesWomen) {
        this.preferencesWomen = preferencesWomen;
    }
}
