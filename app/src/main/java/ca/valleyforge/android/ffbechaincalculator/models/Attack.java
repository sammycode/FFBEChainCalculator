package ca.valleyforge.android.ffbechaincalculator.models;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Hashtable;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Attack Model
 *
 * This model is used directly in calculations,
 * as a result, I am storing references to instances to attackers and abilities, rather than
 * record identifiers.
 *
 * Just going to figure out how exactly I want to abstract this from the database.
 *
 * Update: I think the proper way to aproach this, is to store the record identifier here,
 * and to add some get accessors, which should be able to lookup the underlying record model
 * using cache mechanisms.
 *
 * In order to properly support this, the models should cache new records as needed, without
 * clearing the cache every time.  This can be done using the cursor constructor, which would
 * replace the model in the cache by ID, where the ID is the record identifier.  This should
 * ensure an up-to-date model whenever we need it, and without having to make excessive calls
 * to the content provider.
 *
 */
public class Attack {

    /**
     * The Record Identifier
     */
    private int _recordIdentifier;

    /**
     * The ID Index
     */
    private int _idIndex;

    /**
     * The Stage ID Index
     */
    private int _stageIdIndex;

    /**
     * The Attacker ID Index
     */
    private int _attackerIdIndex;

    /**
     * The Ability ID index
     */
    private int _abilityIdIndex;

    /**
     * The Stage ID
     */
    private int _stageId;

    /**
     * The Attacker ID
     */
    private int _attackerId;

    /**
     * The Ability ID
     */
    private int _abilityId;

    /**
     * The Attacker
     */
    private Unit _attacker;

    /**
     * The Ability
     */
    private Ability _ability;

    /**
     * The Attack Cache
     */
    private static Hashtable<Integer, Attack> _attachCache = new Hashtable<>();

    /**
     * Initialize the Attack
     */
    public Attack() {}

    /**
     * Initialize the Attack
     * @param attackerId The Attacker ID
     * @param abilityId The Ability ID
     */
    public Attack(int attackerId, int abilityId) {
        _attackerId = attackerId;
        _abilityId = abilityId;
    }

    /**
     * Initialize the Attack (From Cursor)
     * @param cursor The Cursor
     * @param position The Position within Cursor
     */
    public Attack(Cursor cursor, int position) {
        assignColumnIndexes(cursor);
        assignFieldValues(cursor, position);
    }

    /**
     * Assigns Column Indexes
     * @param cursor The Cursor
     */
    private void assignColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.Attacks._ID);
        _stageIdIndex = cursor.getColumnIndex(FfbeChainContract.Attacks.COLUMN_STAGE_ID);
        _attackerIdIndex = cursor.getColumnIndex(FfbeChainContract.Attacks.COLUMN_UNIT_ID);
        _abilityIdIndex = cursor.getColumnIndex(FfbeChainContract.Attacks.COLUMN_ABILITY_ID);
    }

    /**
     * Assign Field Values (From Cursor)
     * @param cursor The Cursor
     * @param position The Position in Cursor
     */
    private void assignFieldValues(Cursor cursor, int position) {
        cursor.moveToPosition(position);
        _stageId = cursor.getInt(_stageIdIndex);
        _attackerId = cursor.getInt(_attackerIdIndex);
        _abilityId = cursor.getInt(_abilityIdIndex);
    }

    /**
     * Gets Content Values
     * This can be used to update or insert into a cursor, from the content provider
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FfbeChainContract.Attacks.COLUMN_STAGE_ID, _stageId);
        values.put(FfbeChainContract.Attacks.COLUMN_UNIT_ID, _attackerId);
        values.put(FfbeChainContract.Attacks.COLUMN_ABILITY_ID, _abilityId);
        return values;
    }

    /**
     * Gets the Number of Cached Attacks
     * @return The Number of Cached Attacks
     */
    public static int getCachedAttackCount() {
        if (_attachCache != null) {
            return _attachCache.size();
        } else
        {
            return 0;
        }
    }

    /**
     * Get Cached Attack
     * @param attackId The Attack Identifier
     * @return The Cached Attack, null if the Attack doesn't exist
     */
    public static Attack getCachedAttack(int attackId) {
        return _attachCache.get(attackId);
    }

    /**
     * Gets the Stage ID
     * @return The Stage ID
     */
    public int getStageId() {
        return _stageId;
    }

    /**
     * Sets the Stage ID
     * @param stageId The Stage ID
     */
    public void setStageId(int stageId) {
        _stageId = stageId;
    }

    public Stage getStage() {
        //TODO: Implement Stage cache, so we can pull this from there (maybe)
        return null;
    }

    /**
     * Gets the Attacker ID
     * @return The Attacker ID
     */
    public int getAttackerId() {
        return _attackerId;
    }

    /**
     * Sets the Attacker ID
     * @param attackerId The Attacker ID
     */
    public void set_attackerId(int attackerId) {
        _abilityId = attackerId;
    }

    /**
     * Get Attacker
     * @return The Attacker
     */
    public Unit getAttacker() {
        return Unit.getCachedUnit(_attackerId);
    }

    /**
     * Gets the Ability ID
     * @return The Ability ID
     */
    public int getAbilityId() {
        return _abilityId;
    }

    /**
     * Sets the Ability ID
     * @param abilityId The Ability ID
     */
    public void setAbilityId(int abilityId) {
        _abilityId = abilityId;
    }

    /**
     * Gets the Ability
     * @return The Ability
     */
    public Ability getAbility() {
        return Ability.getCachedAbility(_abilityId);
    }

}
