package org.example.model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "preferences_men")
public class PreferencesMen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPreferencesMen")
    private int idPreferencesMen;

    @ManyToOne
    @JoinColumn(name = "idMen", nullable = false)
    @JsonBackReference
    private Men men;

    @Column(name = "preferred_region")
    private String preferredRegion;

    @Column(name = "preferred_community")
    private String preferredCommunity;

    @Column(name = "handkerchief_or_wig")
    private String handkerchiefOrWig;

    @Column(name = "smoker_or_non_smoker")
    private String smokerOrNonSmoker;

    @Column(name = "kosher_or_non_kosher_device")
    private String kosherOrNonKosherDevice;

    @Column(name = "preferred_status")
    private String preferredStatus;

    // Constructors, getters, and setters

    public PreferencesMen() {
    }

    public PreferencesMen(Men men, String preferredRegion, String preferredCommunity, String handkerchiefOrWig, String smokerOrNonSmoker, String kosherOrNonKosherDevice, String preferredStatus) {
        this.men = men;
        this.preferredRegion = preferredRegion;
        this.preferredCommunity = preferredCommunity;
        this.handkerchiefOrWig = handkerchiefOrWig;
        this.smokerOrNonSmoker = smokerOrNonSmoker;
        this.kosherOrNonKosherDevice = kosherOrNonKosherDevice;
        this.preferredStatus = preferredStatus;
    }

    public int getIdPreferencesMen() {
        return idPreferencesMen;
    }

    public void setIdPreferencesMen(int idPreferencesMen) {
        this.idPreferencesMen = idPreferencesMen;
    }

    public Men getMen() {
        return men;
    }

    public void setMen(Men men) {
        this.men = men;
    }

    public String getPreferredRegion() {
        return preferredRegion;
    }

    public void setPreferredRegion(String preferredRegion) {
        this.preferredRegion = preferredRegion;
    }

    public String getPreferredCommunity() {
        return preferredCommunity;
    }

    public void setPreferredCommunity(String preferredCommunity) {
        this.preferredCommunity = preferredCommunity;
    }

    public String getHandkerchiefOrWig() {
        return handkerchiefOrWig;
    }

    public void setHandkerchiefOrWig(String handkerchiefOrWig) {
        this.handkerchiefOrWig = handkerchiefOrWig;
    }

    public String getSmokerOrNonSmoker() {
        return smokerOrNonSmoker;
    }

    public void setSmokerOrNonSmoker(String smokerOrNonSmoker) {
        this.smokerOrNonSmoker = smokerOrNonSmoker;
    }

    public String getKosherOrNonKosherDevice() {
        return kosherOrNonKosherDevice;
    }

    public void setKosherOrNonKosherDevice(String kosherOrNonKosherDevice) {
        this.kosherOrNonKosherDevice = kosherOrNonKosherDevice;
    }

    public String getPreferredStatus() {
        return preferredStatus;
    }

    public void setPreferredStatus(String preferredStatus) {
        this.preferredStatus = preferredStatus;
    }
}

