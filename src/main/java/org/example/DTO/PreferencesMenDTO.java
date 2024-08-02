package org.example.DTO;

public class PreferencesMenDTO {

    private int idPreferencesMen;
    private String preferredRegion;
    private String preferredCommunity;
    private String handkerchiefOrWig;
    private String preferredStyle;
    private String kosherOrNonKosherDevice;
    private String preferredStatus;
    private Integer preferredAgeFrom;
    private Integer preferredAgeTo;
    private float preferredHeightTo;
    private float preferredHeightFrom;

    private int menId; // הוספת שדה ל-ID של Men

    // Getters and Setters
    public int getIdPreferencesMen() {
        return idPreferencesMen;
    }

    public void setIdPreferencesMen(int idPreferencesMen) {
        this.idPreferencesMen = idPreferencesMen;
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

    public int getMenId() {
        return menId;
    }

    public void setMenId(int menId) {
        this.menId = menId;
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
}

