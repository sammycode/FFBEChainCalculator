package ca.valleyforge.android.ffbechaincalculator.models;

import android.content.ContentValues;
import android.database.Cursor;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Ability
 */
public class Ability {

    /**
     * The Record Identifier
     */
    private int _recordIdentifier;

    /**
     * The ID Index
     */
    private int _idIndex;

    /**
     * The Ability Type Index
     */
    private int _abilityTypeIndex;

    /**
     * The Name Index
     */
    private int _nameIndex;

    /**
     * The Damage Modifier Index
     */
    private int _damageModifierIndex;

    /**
     * The Ignore Defense Modifier Index
     */
    private int _ignoreDefenseModifierIndex;

    /**
     * The Ignore Spirit Modifier Index
     */
    private int _ignoreSpiritModifierIndex;

    /**
     * The Number of Hits Index
     */
    private int _numberOfHitsIndex;

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
    public Ability() {}

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
     * Initialize Ability (From Cursor)
     * @param cursor The Cursor
     * @param  position The Position Within the Cursor
     */
    public Ability(Cursor cursor, int position) {
        assignColumnIndexes(cursor);
        //TODO: Assign Field Values
        //TODO: Build ContentValues Method
    }

    /**
     * Assign the Column Indexes
     * @param cursor The Cursor
     */
    private void assignColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.Abilities._ID);
        _nameIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_NAME);
        _damageModifierIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_DAMAGE_MODIFIER);
        _ignoreDefenseModifierIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_IGNORE_DEFENSE_MODIFIER);
        _ignoreSpiritModifierIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_IGNORE_SPIRIT_MODIFIER);
        _numberOfHitsIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_NUMBER_OF_HITS);
    }

    /**
     * Assign Field Values from Cursor
     * @param cursor The Cursor
     * @param position The Position in the Cursor
     */
    private void assignFieldValues(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        _recordIdentifier = cursor.getInt(_idIndex);
        _name = cursor.getString(_nameIndex);
        _damageModifier = cursor.getFloat(_damageModifierIndex);
        _ignoreDefenseModifier = cursor.getFloat(_ignoreDefenseModifierIndex);
        _ignoreSpiritModifier = cursor.getFloat(_ignoreSpiritModifierIndex);
        _numberOfHits = cursor.getInt(_numberOfHitsIndex);
    }

    /**
     * Gets Content Values
     * to be used to insert or update into a cursor, from the content provider
     * @return The Content Values
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FfbeChainContract.Abilities.COLUMN_NAME, _name);
        values.put(FfbeChainContract.Abilities.COLUMN_DAMAGE_MODIFIER, _damageModifier);
        values.put(FfbeChainContract.Abilities.COLUMN_IGNORE_DEFENSE_MODIFIER, _ignoreDefenseModifier);
        values.put(FfbeChainContract.Abilities.COLUMN_IGNORE_SPIRIT_MODIFIER, _ignoreSpiritModifier);
        values.put(FfbeChainContract.Abilities.COLUMN_NUMBER_OF_HITS, _numberOfHits);
        return values;
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
     * @param damageModifier The Damage Modifier
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
