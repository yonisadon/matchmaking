package org.example.DTO;

public class MatchDtoForMen {
    private int menId;
    private String menFirstName;
    private String menLastName;

    private int menAge;
    private float menHeight;
    private String menStatus;
    private String menStyle;

//    private int womenId;
//    private String womenFirstName;
//    private String womenLastName;
//
//    private int womenAge;
//    private float womenHeight;
//    private String womenStatus;
//    private String womenStyle;

    // בנאי מלא
    public MatchDtoForMen(int menId, String menFirstName, String menLastName, int menAge, float menHeight, String menStatus, String menStyle
                            //int womenId, String womenFirstName, String womenLastName, int womenAge, float womenHeight, String womenStatus, String womenStyle
    ) {
        this.menId = menId;
        this.menFirstName = menFirstName;
        this.menLastName = menLastName;
        this.menAge = menAge;
        this.menHeight = menHeight;
        this.menStatus = menStatus;
        this.menStyle = menStyle;
//        this.womenId = womenId;
//        this.womenFirstName = womenFirstName;
//        this.womenLastName = womenLastName;
//        this.womenAge = womenAge;
//        this.womenHeight = womenHeight;
//        this.womenStatus = womenStatus;
//        this.womenStyle = womenStyle;

    }

    // בנאי ריק
    public MatchDtoForMen() {
    }

    // Getters ו-Setters
    public int getMenId() {
        return menId;
    }

    public void setMenId(int menId) {
        this.menId = menId;
    }

    public String getMenFirstName() {
        return menFirstName;
    }

    public void setMenFirstName(String menFirstName) {
        this.menFirstName = menFirstName;
    }

    public String getMenLastName() {
        return menLastName;
    }

    public void setMenLastName(String menLastName) {
        this.menLastName = menLastName;
    }
    public int getMenAge() {
        return menAge;
    }

    public void setMenAge(int menAge) {
        this.menAge = menAge;
    }

    public float getMenHeight() {
        return menHeight;
    }

    public void setMenHeight(float menHeight) {
        this.menHeight = menHeight;
    }

    public String getMenStatus() {
        return menStatus;
    }

    public void setMenStatus(String menStatus) {
        this.menStatus = menStatus;
    }

    public String getMenStyle() {
        return menStyle;
    }

    public void setMenStyle(String menStyle) {
        this.menStyle = menStyle;
    }

//    public int getWomenId() {
//        return womenId;
//    }
//
//    public void setWomenId(int womenId) {
//        this.womenId = womenId;
//    }
//
//    public String getWomenFirstName() {
//        return womenFirstName;
//    }
//
//    public void setWomenFirstName(String womenFirstName) {
//        this.womenFirstName = womenFirstName;
//    }
//
//    public String getWomenLastName() {
//        return womenLastName;
//    }
//
//    public void setWomenLastName(String womenLastName) {
//        this.womenLastName = womenLastName;
//    }
//
//    public int getWomenAge() {
//        return womenAge;
//    }
//
//    public void setWomenAge(int womenAge) {
//        this.womenAge = womenAge;
//    }
//
//    public float getWomenHeight() {
//        return womenHeight;
//    }
//
//    public void setWomenHeight(float womenHeight) {
//        this.womenHeight = womenHeight;
//    }
//
//    public String getWomenStatus() {
//        return womenStatus;
//    }
//
//    public void setWomenStatus(String womenStatus) {
//        this.womenStatus = womenStatus;
//    }
//
//    public String getWomenStyle() {
//        return womenStyle;
//    }
//
//    public void setWomenStyle(String womenStyle) {
//        this.womenStyle = womenStyle;
//    }
}

