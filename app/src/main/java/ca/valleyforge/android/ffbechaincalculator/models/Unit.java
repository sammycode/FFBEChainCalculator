package ca.valleyforge.android.ffbechaincalculator.models;

import android.content.ContentValues;
import android.database.Cursor;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Unit
 */
public class Unit {

    /**
     * The Record Identifier, if loaded from cursor
     */
    private int _recordIdentifier;

    /**
     * The ID Index
     */
    private int _idIndex;

    /**
     * The Unit Class Index
     */
    private int _unitClassIndex;

    /**
     * The Name Index
     */
    private int _nameIndex;

    /**
     * The Level Index
     */
    private int _levelIndex;

    /**
     * The Attack Power Index
     */
    private int _attackPowerIndex;

    /**
     * The Magic Power Index
     */
    private int _magicPowerIndex;

    /**
     * The Defense Rating Index
     */
    private int _defenseRatingIndex;

    /**
     * The Spirit Rating Index
     */
    private int _spiritRatingIndex;

    /**
     * The Defense Broken Index
     */
    private int _defenseBrokenIndex;

    /**
     * The Spirit Broken Index
     */
    private int _spiritBrokenIndex;

    /**
     * The Name
     */
    private String _name;

    /**
     * The Unit Class Index
     */
    private String _unitClass;

    /**
     * The Level
     */
    private float _level;

    /**
     * The Attack Power
     */
    private float _attackPower;

    /**
     * The Magic Power
     */
    private float _magicPower;

    /**
     * The Defense Rating
     */
    private float _defenseRating;

    /**
     * The Spirit Rating
     */
    private float _spiritRating;

    /**
     * The Defense Broken Percent
     */
    private float _defenseBrokenPercent;

    /**
     * The Spirit Broken Percent
     */
    private float _spiritBrokenPercent;

    /**
     * Initialize Unit
     */
    public Unit() {}

    /**
     * Initialize Unit
     * @param name The Unit Name
     * @param level The Unit Level
     * @param attackPower The Unit Attack Power
     * @param magicPower The Unit Magic Power
     * @param defenseRating The Unit Defense Rating
     * @param spiritRating The Unit Spirit Rating
     * @param defenseBrokenPercent The Unit Defense Broken Percent
     * @param spiritBrokenPercent The Unit Spirit Broken Percent
     */
    public Unit(String name, float level, float attackPower, float magicPower, float defenseRating, float spiritRating, float defenseBrokenPercent, float spiritBrokenPercent) {
        _name = name;
        _level = level;
        _attackPower = attackPower;
        _magicPower = magicPower;
        _defenseRating = defenseRating;
        _spiritRating = spiritRating;
        _defenseBrokenPercent = defenseBrokenPercent;
        _spiritBrokenPercent = spiritBrokenPercent;
    }

    /**
     * Initialize Unit (From Cursor)
     *
     * @param cursor The Cursor
     * @param position The Position within the cursor
     */
    public Unit(Cursor cursor, int position) {
        assignColumnIndexes(cursor);
        assignFieldValues(cursor, position);
    }

    /**
     * Assigns the Column Indexes
     * @param cursor The Cursor
     */
    private void assignColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.Units._ID);
        _unitClassIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_CLASS);
        _nameIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_NAME);
        _levelIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_LEVEL);
        _attackPowerIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_ATTACK_POWER);
        _magicPowerIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_MAGIC_POWER);
        _defenseRatingIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_DEFENSE_RATING);
        _spiritRatingIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_RATING);
        _defenseBrokenIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_DEFENCE_BROKEN);
        _spiritBrokenIndex = cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_BROKEN);
    }

    /**
     * Assign Field Values from Cursor
     * @param cursor The Cursor
     * @param position The Position within the cursor
     */
    private void assignFieldValues(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        _recordIdentifier = cursor.getInt(_idIndex);
        _name = cursor.getString(_nameIndex);
        _unitClass = cursor.getString(_unitClassIndex);
        _level = cursor.getFloat(_levelIndex);
        _attackPower = cursor.getFloat(_attackPowerIndex);
        _magicPower = cursor.getFloat(_magicPowerIndex);
        _defenseRating = cursor.getFloat(_defenseRatingIndex);
        _spiritRating = cursor.getFloat(_spiritRatingIndex);
        _defenseBrokenPercent = cursor.getFloat(_defenseBrokenIndex);
        _spiritBrokenPercent = cursor.getFloat(_spiritBrokenIndex);
    }

    /**
     * Gets Content Values
     * This can be used to update or insert into a cursor, from the content provider
     * @return The Content Values
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FfbeChainContract.Units.COLUMN_UNIT_CLASS, _unitClass);
        values.put(FfbeChainContract.Units.COLUMN_NAME, _name);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_LEVEL, _level);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_ATTACK_POWER, _attackPower);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_MAGIC_POWER, _magicPower);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_DEFENSE_RATING, _defenseRating);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_RATING, _spiritRating);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_DEFENCE_BROKEN, _defenseBrokenPercent);
        values.put(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_BROKEN, _spiritBrokenPercent);
        return values;
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
     * Gets the Unit Class
     * @return The Unit Class
     */
    public String getUnitClass() {
        return _unitClass;
    }

    /**
     * Sets the Unit Class
     * @param unitClass The Unit Class
     */
    public void setUnitClass(String unitClass) {
        _unitClass = unitClass;
    }

    /**
     * Gets the Level
     * @return The Level
     */
    public float getLevel() {
        return _level;
    }

    /**
     * Sets the Level
     * @param level The Level
     */
    public void setLevel(float level) {
        _level = level;
    }

    /**
     * Gets the Unit Attack Power
     * @return The Unit Attack Power
     */
    public float getAttackPower() {
        return _attackPower;
    }

    /**
     * Sets the Unit Attack Power
     * @param attackPower The Unit Attack Power
     */
    public void setAttackPower(float attackPower) {
        _attackPower = attackPower;
    }

    /**
     * Gets the Unit Magic Power
     * @return The Unit Magic Power
     */
    public float getMagicPower() {
        return _magicPower;
    }

    /**
     * Sets the Unit Magic Power
     * @param magicPower The Unit Magic Power
     */
    public void setMagicPower(float magicPower) {
        _magicPower = magicPower;
    }

    /**
     * Gets the Unit Defense Rating
     * @return The Unit Defense Rating
     */
    public float getDefenseRating() {
        return _defenseRating;
    }

    /**
     * Sets the Unit Defense Rating
     * @param defenseRating The Unit Defense Rating
     */
    public void setDefenseRating(float defenseRating) {
        _defenseRating = defenseRating;
    }

    /**
     * Gets Unit Spirit Rating
     * @return The Unit Spirit Rating
     */
    public float getSpiritRating() {
        return _spiritRating;
    }

    /**
     * Sets the Unit Spirit Rating
     * @param spiritRating The Unit Spirit Rating
     */
    public void setSpiritRating(float spiritRating) {
        _spiritRating = spiritRating;
    }

    /**
     * Gets the Unit Defense Broken Percent
     * @return The Unit Defense Broken Percent
     */
    public float getDefenseBrokenPercent() {
        return _defenseBrokenPercent;
    }

    /**
     * Sets the Unit Defense Brokeen Percent
     * @param defenseBrokenPercent The Unit Defense Broken Percent
     */
    public void setDefenseBrokenPercent(float defenseBrokenPercent) {
        _defenseBrokenPercent = defenseBrokenPercent;
    }

    /**
     * Gets the Unit Spirit Broken Percent
     * @return The Unit Spirit Broken Percent
     */
    public float getSpiritBrokenPercent() {
        return _spiritBrokenPercent;
    }

    /**
     * Sets the Unit Spirit Broken Percent
     * @param spiritBrokenPercent The Unit Spirit Broken Percent
     */
    public void setSpiritBrokenPercent(float spiritBrokenPercent) {
        _spiritBrokenPercent = spiritBrokenPercent;
    }

}
