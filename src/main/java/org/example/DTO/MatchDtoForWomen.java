package org.example.DTO;

public class MatchDtoForWomen {




        private int womenId;
        private String womenFirstName;
        private String womenLastName;
        private int womenAge;
        private float womenHeight;
        private String womenStatus;
        private String womenStyle;

        // בנאי מלא
        public MatchDtoForWomen(
                int womenId, String womenFirstName, String womenLastName, int womenAge, float womenHeight, String womenStatus, String womenStyle
                ) {
            this.womenId = womenId;
            this.womenFirstName = womenFirstName;
            this.womenLastName = womenLastName;
            this.womenAge = womenAge;
            this.womenHeight = womenHeight;
            this.womenStatus = womenStatus;
            this.womenStyle = womenStyle;

        }

        // בנאי ריק
        public MatchDtoForWomen() {
        }

        // Getters ו-Setters

        public int getWomenId() {
            return womenId;
        }

        public void setWomenId(int womenId) {
            this.womenId = womenId;
        }

        public String getWomenFirstName() {
            return womenFirstName;
        }

        public void setWomenFirstName(String womenFirstName) {
            this.womenFirstName = womenFirstName;
        }

        public String getWomenLastName() {
            return womenLastName;
        }

        public void setWomenLastName(String womenLastName) {
            this.womenLastName = womenLastName;
        }

        public int getWomenAge() {
            return womenAge;
        }

        public void setWomenAge(int womenAge) {
            this.womenAge = womenAge;
        }

        public float getWomenHeight() {
            return womenHeight;
        }

        public void setWomenHeight(float womenHeight) {
            this.womenHeight = womenHeight;
        }

        public String getWomenStatus() {
            return womenStatus;
        }

        public void setWomenStatus(String womenStatus) {
            this.womenStatus = womenStatus;
        }

        public String getWomenStyle() {
            return womenStyle;
        }

        public void setWomenStyle(String womenStyle) {
            this.womenStyle = womenStyle;
        }
    }



