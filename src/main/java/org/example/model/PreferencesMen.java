package org.example.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private float preferredHeightTo;
    private float preferredHeightFrom;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private String preferredWork;
    private String preferredStudies;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public PreferencesMen() {
    }

    public PreferencesMen(Men men, String preferredRegion, String preferredCommunity, String handkerchiefOrWig, String preferredStyle, String kosherOrNonKosherDevice, String preferredStatus, int preferredAgeFrom, int preferredAgeTo, Float preferredHeightFrom,
                          float preferredHeightTo, String preferredWork, String preferredStudies) {

        this.men = men;
        this.preferredRegion = preferredRegion;
        this.preferredCommunity = preferredCommunity;
        this.handkerchiefOrWig = handkerchiefOrWig;
        this.preferredStyle = preferredStyle;
        this.kosherOrNonKosherDevice = kosherOrNonKosherDevice;
        this.preferredStatus = preferredStatus;
        this.preferredAgeFrom = preferredAgeFrom;
        this.preferredAgeTo   = preferredAgeTo;
        this.preferredHeightFrom = preferredHeightFrom;
        this.preferredHeightTo = preferredHeightTo;
        this.preferredWork = preferredWork;
        this.preferredStudies = preferredStudies;
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

    public String getPreferredWork() {
        return preferredWork;
    }

    public void setPreferredWork(String preferredWork) {
        this.preferredWork = preferredWork;
    }

    public String getPreferredStudies() {
        return preferredStudies;
    }

    public void setPreferredStudies(String preferredStudies) {
        this.preferredStudies = preferredStudies;
    }
}

