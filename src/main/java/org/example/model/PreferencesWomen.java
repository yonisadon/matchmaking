package org.example.model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "preferences_women")
public class PreferencesWomen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPreferencesWomen")
    private int idPreferencesWomen;

    @ManyToOne
    @JoinColumn(name = "idWomen", nullable = false)
    @JsonBackReference
    private Women women;

    @Column(name = "preferred_region")
    private String preferredRegion;

    @Column(name = "preferred_community")
    private String preferredCommunity;

    @Column(name = "handkerchief_or_wig")
    private String handkerchiefOrWig;

    @Column(name = "style")
    private String preferredStyle;

    @Column(name = "kosher_or_non_kosher_device")
    private String kosherOrNonKosherDevice;

    @Column(name = "preferred_status")
    private String preferredStatus;

    @Column(name = "preferred_age_from")
    private Integer preferredAgeFrom;

    @Column(name = "preferred_age_to")
    private Integer preferredAgeTo;

    @Column(name = "preferred_height_from")
    private float preferredHeightFrom;

    @Column(name = "preferred_height_to")
    private float preferredHeightTo;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public PreferencesWomen() {
    }

    public PreferencesWomen(Women women, String preferredRegion, String preferredCommunity, String handkerchiefOrWig, String preferredStyle, String kosherOrNonKosherDevice, String preferredStatus, int preferredAgeFrom, int preferredAgeTo, float preferredHeightFrom,
                            float preferredHeightTo) {

        this.women = women;
        this.preferredRegion = preferredRegion;
        this.preferredCommunity = preferredCommunity;
        this.handkerchiefOrWig = handkerchiefOrWig;
        this.preferredStyle = preferredStyle;
        this.kosherOrNonKosherDevice = kosherOrNonKosherDevice;
        this.preferredStatus = preferredStatus;
        this.preferredAgeFrom = preferredAgeFrom;
        this.preferredAgeTo = preferredAgeTo;
        this.preferredHeightFrom = preferredHeightFrom;
        this.preferredHeightTo = preferredHeightTo;
    }

    public int getIdPreferencesWomen() {
        return idPreferencesWomen;
    }

    public void setIdPreferencesWomen(int idPreferencesWomen) {
        this.idPreferencesWomen = idPreferencesWomen;
    }

    public Women getWomen() {
        return women;
    }

    public void setWomen(Women women) {
        this.women = women;
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

    public String getPreferredStyle() {
        return preferredStyle;
    }

    public void setPreferredStyle(String preferredStyle) {
        this.preferredStyle = preferredStyle;
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

    public int getPreferredAgeFrom() {
        return preferredAgeFrom;
    }

    public void setPreferredAgeFrom(int preferredAgeFrom) {
        this.preferredAgeFrom = preferredAgeFrom;
    }

    public int getPreferredAgeTo() {
        return preferredAgeTo;
    }

    public void setPreferredAgeTo(int preferredAgeTo) {
        this.preferredAgeTo = preferredAgeTo;
    }

    public float getPreferredHeightFrom() {
        return preferredHeightFrom;
    }
    public void setPreferredHeightFrom(float preferredHeightFrom) {
        this.preferredHeightFrom = preferredHeightFrom;
    }
    public float getPreferredHeightTo() {
        return preferredHeightTo;
    }

    public void setPreferredHeightTo(float preferredHeightTo) {
        this.preferredHeightTo = preferredHeightTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

