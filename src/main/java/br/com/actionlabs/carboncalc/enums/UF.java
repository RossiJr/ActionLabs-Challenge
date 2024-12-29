package br.com.actionlabs.carboncalc.enums;

public enum UF {
    AC,
    AL,
    AP,
    AM,
    BA,
    CE,
    DF,
    ES,
    GO,
    MA,
    MT,
    MS,
    MG,
    PA,
    PB,
    PR,
    PE,
    PI,
    RJ,
    RN,
    RS,
    RO,
    RR,
    SC,
    SP,
    SE,
    TO;

    public static UF fromString(String uf) {
        for (UF value : UF.values()) {
            if (value.name().equals(uf.toUpperCase())) {
                return value;
            }
        }
        return null;
    }
}
