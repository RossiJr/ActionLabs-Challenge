package br.com.actionlabs.carboncalc.enums;

public enum TransportationType {
    CAR,
    MOTORCYCLE,
    PUBLIC_TRANSPORT,
    BICYCLE;

    public static TransportationType fromString(String type) {
        for (TransportationType t : TransportationType.values()) {
            if (t.name().equalsIgnoreCase(type)) {
                return t;
            }
        }
        return null;
    }
}
