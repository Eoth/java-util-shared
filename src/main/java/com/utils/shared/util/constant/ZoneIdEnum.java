package com.utils.shared.util.constant;

import java.time.ZoneId;

public enum ZoneIdEnum {
    EUROPE_PARIS("Europe/Paris"),
    EUROPE_LONDON("Europe/London"),
    AMERICA_NEW_YORK("America/New_York"),
    UTC("UTC");

    private final ZoneId zoneId;

    ZoneIdEnum(String zoneId) {
        this.zoneId = ZoneId.of(zoneId);
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

}
