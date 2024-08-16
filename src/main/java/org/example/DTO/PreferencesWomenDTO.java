package org.example.DTO;


public class PreferencesWomenDTO  extends PreferencesBaseDTO {

    //public PreferencesWomenDTO() {
    //    super();
    //}
    private int idPreferencesWomen;
    private int womenId; // הוספת שדה ל-ID של Women

    // Getters and Setters
    public int getIdPreferencesWomen() {
        return idPreferencesWomen;
    }

    public void setIdPreferencesWomen(int idPreferencesWomen) {
        this.idPreferencesWomen = idPreferencesWomen;
    }

    public int getWomenId() {
        return womenId;
    }

    public void setWomenId(int womenId) {
        this.womenId = womenId;
    }
}
