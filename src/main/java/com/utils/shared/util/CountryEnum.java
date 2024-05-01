package com.utils.shared.util;

import java.util.logging.Level;

public enum CountryEnum implements KeyValue {
    FRANCE("FR", "FRANCE"),
    BELGIUM("BE", "BELGIUM"),
    LUXEMBOURG("LU", "LUXEMBOURG"),
    TOGO("TG", "TOGO");

    private final String key;
    private final String value;

    CountryEnum(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        final var countries = KeyValue.toKeysValues(CountryEnum.values());
        final var frLabel = KeyValue.getValueOfKey(CountryEnum.values(), "FR");
        LOGGER.log(Level.INFO, "keys values = {0} \n label = {1}", new Object[]{countries, frLabel});
    }
}
