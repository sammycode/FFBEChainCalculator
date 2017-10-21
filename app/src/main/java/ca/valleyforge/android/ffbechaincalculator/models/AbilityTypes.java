package ca.valleyforge.android.ffbechaincalculator.models;

/**
 * The Ability Types
 */
public enum AbilityTypes {

    /**
     * Physical based damage,
     * damage modified by ATK, damage reduced with DEF
     */
    Physical,

    /**
     * Magic based damage,
     * damage modified by MAG, damage reduced with SPR
     */
    Magic,

    /**
     * Hybrid based damage,
     * damage modified by both ATK and MAG, damage reduced by both DEF and SPR
     */
    Hybrid

}
