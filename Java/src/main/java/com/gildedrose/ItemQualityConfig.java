package com.gildedrose;

import java.util.Properties;

public class ItemQualityConfig {

    private static ItemQualityConfig instance;
    private final Properties properties;

    private ItemQualityConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        //Preferably load this from a config file

        final int defaultDegradation = 1;

        properties.setProperty("quality_degradation", String.valueOf(defaultDegradation));
        properties.setProperty("quality_degradation_expired", String.valueOf(2 * defaultDegradation));
        properties.setProperty("minimum_quality_allowed", "0");
        properties.setProperty("maximum_quality_allowed", "50");
        properties.setProperty("legendary_quality", "80");
    }

    public static ItemQualityConfig getInstance() {
        if (instance == null) {
            instance = new ItemQualityConfig();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
