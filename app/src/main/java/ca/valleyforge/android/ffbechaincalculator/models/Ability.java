package ca.valleyforge.android.ffbechaincalculator.models;

/**
 * The Ability
 */
public class Ability {

    /**
     * The Ability Type
     */
    private AbilityTypes _abilityType;

    /**
     * The Ability Name
     */
    private String _name;

    /**
     * The Damage Modifier
     */
    private float _damageModifier;

    /**
     * The Ignore defense modifier
     */
    private float _ignoreDefenseModifier;

    /**
     * The Ignore Spirit Modifier
     */
    private float _ignoreSpiritModifier;

    /**
     * The Number of hits
     */
    private int _numberOfHits;

    /**
     * Initialize Ability
     */
    public Ability(AbilityTypes abilityType, String name, float damageModifier, float ignoreDefenseModifier, float ignoreSpiritModifier, int numberOfHits) {
        _abilityType = abilityType;
        _name = name;
        _damageModifier = damageModifier;
        _ignoreDefenseModifier = ignoreDefenseModifier;
        _ignoreSpiritModifier = ignoreSpiritModifier;
        _numberOfHits = numberOfHits;
    }

    /**
     * Gets the Ability Type
     * @return The Ability Type
     */
    public AbilityTypes getAbilityType() {
        return _abilityType;
    }

    /**
     * Sets the Ability Type
     * @param abilityType The Ability Type
     */
    public void setAbilityType(AbilityTypes abilityType) {
        _abilityType = abilityType;
    }

    /**
     * Gets the Name
     * @return The Name
     */
    public String getName() {
        return _name;
    }

    /**
     * Sets the Name
     * @param name The Name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Gets the Damage Modifier
     * @return The Damage Modifier
     */
    public float getDamageModifier() {
        return _damageModifier;
    }

    /**
     * Sets the Damage Modifier
     * @param damageModifier
     */
    public void setDamageModifier(float damageModifier) {
        _damageModifier = damageModifier;
    }

    /**
     * Gets Ignore Defense Modifier
     * @return The Ignore Defense Modifier
     */
    public float getIgnoreDefenseModifier() {
        return _ignoreDefenseModifier;
    }

    /**
     * Sets Ignore Defense Modifier
     * @param ignoreDefenseModifier The Ignore Defense Modifier
     */
    public void setIgnoreDefenseModifier(float ignoreDefenseModifier) {
        _ignoreSpiritModifier = ignoreDefenseModifier;
    }

    /**
     * Gets Ignore Spirit Modifier
     * @return The Ignore Spirit Modifier
     */
    public float getIgnoreSpiritModifier() {
        return _ignoreSpiritModifier;
    }

    /**
     * Sets Ignore Spirit Modifier
     * @param ignoreSpiritModifier The Ignore Spirit Modifier
     */
    public void setInoreSpiritModifier(float ignoreSpiritModifier) {
        _ignoreSpiritModifier = ignoreSpiritModifier;
    }

    /**
     * Gets Number of Hits
     * @return The Number of Hits
     */
    public int getNumberOfHits() {
        return _numberOfHits;
    }

    /**
     * Sets Number of Hits
     * @param numberOfHits The Number of hits
     */
    public void setNumberOfHits(int numberOfHits) {
        _numberOfHits = numberOfHits;
    }

    //TODO: Wireup Cursor Bling...

}
