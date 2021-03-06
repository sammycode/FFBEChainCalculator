package ca.valleyforge.android.ffbechaincalculator.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Hashtable;

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
    private String _abilityType;

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
     * The Ability Cache
     */
    private static Hashtable<Integer, Ability> _abilitiesCache = new Hashtable<>();

    /**
     * Initialize Ability
     */
    public Ability() {}

    /**
     * Initialize Ability
     */
    public Ability(AbilityTypes abilityType, String name, float damageModifier, float ignoreDefenseModifier, float ignoreSpiritModifier, int numberOfHits) {
        _abilityType = getAbilityTypeCode(abilityType);
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
        assignFieldValues(cursor, position);

        //Cache Ability
        _abilitiesCache.put(_recordIdentifier, this);

    }

    /**
     * Assign the Column Indexes
     * @param cursor The Cursor
     */
    private void assignColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.Abilities._ID);
        _abilityTypeIndex = cursor.getColumnIndex(FfbeChainContract.Abilities.COLUMN_ABILITY_TYPE);
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
        _abilityType = cursor.getString(_abilityTypeIndex);
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
     * Gets the Number of Cached Abilities
     * @return The Number of Cached Abilities
     */
    public static int getCachedAbilitiesCount() {
        if (_abilitiesCache != null) {
            return _abilitiesCache.size();
        } else
        {
            return 0;
        }
    }

    /**
     * Get Cached Ability
     * @param abilityId The Ability Identifier
     * @return The Cached Ability, null if the Ability doesn't exist
     */
    public static Ability getCachedAbility(int abilityId) {
        return _abilitiesCache.get(abilityId);
    }

    /**
     * Gets the Ability Type
     * @return The Ability Type
     */
    public AbilityTypes getAbilityType() {

        switch (_abilityType) {
            case FfbeChainContract.Abilities.ABILITY_TYPE_PHYSICAL:
                return AbilityTypes.Physical;
            case FfbeChainContract.Abilities.ABILITY_TYPE_MAGIC:
                return AbilityTypes.Magic;
            case FfbeChainContract.Abilities.ABILITY_TYPE_HYBRID:
                return AbilityTypes.Hybrid;
            default:
                return AbilityTypes.Unknown;
        }

    }

    /**
     * Sets the Ability Type
     * @param abilityType The Ability Type
     */
    public void setAbilityType(AbilityTypes abilityType) {
        _abilityType = getAbilityTypeCode(abilityType);
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

    /**
     * Gets Ability Type from Code
     * @param abilityTypeCode
     * @return
     */
    private static AbilityTypes getAbilityTypeFromCode(String abilityTypeCode) {
        switch (abilityTypeCode) {
            case FfbeChainContract.Abilities.ABILITY_TYPE_PHYSICAL:
                return AbilityTypes.Physical;
            case FfbeChainContract.Abilities.ABILITY_TYPE_MAGIC:
                return AbilityTypes.Magic;
            case FfbeChainContract.Abilities.ABILITY_TYPE_HYBRID:
                return AbilityTypes.Hybrid;
            default:
                return AbilityTypes.Unknown;
        }
    }

    /**
     * Gets Ability Type Code from Ability Type
     * @param abilityType The Ability Type
     * @return The Ability Type Code
     */
    private static String getAbilityTypeCode(AbilityTypes abilityType) {
        switch (abilityType) {
            case Physical:
                return FfbeChainContract.Abilities.ABILITY_TYPE_PHYSICAL;
            case Magic:
                return FfbeChainContract.Abilities.ABILITY_TYPE_MAGIC;
            case Hybrid:
                return FfbeChainContract.Abilities.ABILITY_TYPE_HYBRID;
            default:
                return "UNKNOWN";
        }
    }

}
