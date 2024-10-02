package com.example.currency_converter_android_app.models;


public class CurrencyModel {
        public String result;
        public String documentation;
        public String terms_of_use;
        public int time_last_update_unix;
        public String time_last_update_utc;
        public int time_next_update_unix;
        public String time_next_update_utc;
        public String base_code;
        public String target_code;
        public double conversion_rate;
        public double conversion_result;

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTermsOfUse() {
        return terms_of_use;
    }

    public int getTimeLastUpdateUnix() {
        return time_last_update_unix;
    }

    public String getTimeLastUpdateUtc() {
        return time_last_update_utc;
    }

    public int getTimeNextUpdateUnix() {
        return time_next_update_unix;
    }

    public String getTimeNextUpdateUtc() {
        return time_next_update_utc;
    }

    public String getBaseCode() {
        return base_code;
    }

    public String getTargetCode() {
        return target_code;
    }

    public double getConversionRate() {
        return conversion_rate;
    }

    public double getConversionResult() {
        return conversion_result;
    }
}