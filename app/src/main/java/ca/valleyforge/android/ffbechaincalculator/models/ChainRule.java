package ca.valleyforge.android.ffbechaincalculator.models;

import android.content.ContentValues;
import android.database.Cursor;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Chain Rule Model
 */
public class ChainRule {

    /**
     * The Record Identifier
     */
    private int _recordIdentifier;

    /**
     * The ID Index
     */
    private int _idIndex;

    /**
     * The Name Index
     */
    private int _nameIndex;

    /**
     * The Damage Modifier Index
     */
    private int _damageModifierIndex;

    /**
     * The Hit Modifier Cap Index
     */
    private int _hitModifierCapIndex;

    /**
     * The Chain Rule Name
     */
    private String _name;

    /**
     * The Damage Modifier
     */
    private float _damageModifier;

    /**
     * The Hit Modifier Cap
     */
    private int _hitModifierCap;

    /**
     * Initialize ChainRule
     */
    public ChainRule() {}

    /**
     * Initialize ChainRule
     * @param name The Name
     * @param damageModifier The Damage Modifier
     * @param hitModifierCap The Hit Modifier Cap
     */
    public ChainRule(String name, float damageModifier, int hitModifierCap) {
        _name = name;
        _damageModifier = damageModifier;
        _hitModifierCap = hitModifierCap;
    }

    /**
     * Initialize ChainRule (From Cursor)
     * @param cursor The Cursor
     * @param position The Position within the cursor
     */
    public ChainRule(Cursor cursor, int position) {
        assignColumnIndexes(cursor);
        assignFieldValues(cursor, position);
    }

    /**
     * Assigns the Column Indexes
     * @param cursor The Cursor
     */
    private void assignColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.ChainRules._ID);
        _nameIndex = cursor.getColumnIndex(FfbeChainContract.ChainRules.COLUMN_NAME);
        _damageModifierIndex = cursor.getColumnIndex(FfbeChainContract.ChainRules.COLUMN_DAMAGE_MODIFIER);
        _hitModifierCapIndex = cursor.getColumnIndex(FfbeChainContract.ChainRules.COLUMN_HIT_MODIFIER_CAP);
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
        _damageModifier = cursor.getFloat(_damageModifierIndex);
        _hitModifierCap = cursor.getInt(_hitModifierCapIndex);
    }

    /**
     * Gets Content Values
     * to be used to update or insert into a cursor, from the content provider
     * @return The Content Values
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FfbeChainContract.ChainRules.COLUMN_NAME, _name);
        values.put(FfbeChainContract.ChainRules.COLUMN_DAMAGE_MODIFIER, _damageModifier);
        values.put(FfbeChainContract.ChainRules.COLUMN_HIT_MODIFIER_CAP, _hitModifierCap);
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
     * Gets the Hit Modifier Cap
     * @return The Hit Modifier Cap
     */
    public int getHitModifierCap() {
        return _hitModifierCap;
    }

    /**
     * Sets the Hit Modifier Cap
     * @param hitModifierCap The Hit Modifier Cap
     */
    public void setHitModifierCap(int hitModifierCap) {
        _hitModifierCap = hitModifierCap;
    }

}
