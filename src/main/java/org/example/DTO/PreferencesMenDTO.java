package org.example.DTO;

public class PreferencesMenDTO extends PreferencesBaseDTO {

    private int idPreferencesMen;
    private int menId; // הוספת שדה ל-ID של Men

    // Getters and Setters
    public int getIdPreferencesMen() {
        return idPreferencesMen;
    }

    public void setIdPreferencesMen(int idPreferencesMen) {
        this.idPreferencesMen = idPreferencesMen;
    }

    public int getMenId() {
        return menId;
    }

    public void setMenId(int menId) {
        this.menId = menId;
    }
}
