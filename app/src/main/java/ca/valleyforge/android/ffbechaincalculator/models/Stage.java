package ca.valleyforge.android.ffbechaincalculator.models;

import android.database.Cursor;

import java.util.Hashtable;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Stage Model
 */
public class Stage {

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
     * The Badguy ID Index
     */
    private int _badguyIdIndex;

    //TODO: Add more column indexes here as needed

    /**
     * The Stage Name
     */
    private String _name;

    /**
     * The Badguy Id
     */
    private int _badguyId;

    /**
     * The Stage Cache
     */
    private static Hashtable<Integer, Stage> _stageCache = new Hashtable<>();

    /**
     * Initialize Stage
     */
    public Stage() {}

    /**
     * Initialize Stage
     * @param name The Stage Name
     * @param badguyId The Badguy ID
     */
    public Stage(String name, int badguyId) {
        _name = name;
        _badguyId = badguyId;
    }

    /**
     * Initialize Stage (From Cursor)
     * @param cursor The Cursor
     * @param position The Position within the cursor
     */
    public Stage(Cursor cursor, int position) {
        assingColumnIndexes(cursor);
        assignFieldValues(cursor, position);
        _stageCache.put(_recordIdentifier, this);
    }

    /**
     * Assign Column Indexes
     * @param cursor The Cursor
     */
    private void assingColumnIndexes(Cursor cursor) {
        _idIndex = cursor.getColumnIndex(FfbeChainContract.Stages._ID);
        _nameIndex = cursor.getColumnIndex(FfbeChainContract.Stages.COLUMN_NAME);
        _badguyIdIndex = cursor.getColumnIndex(FfbeChainContract.Stages.COLUMN_BADGUY_ID);
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
        _badguyIdIndex = cursor.getInt(_badguyIdIndex);
    }

    /**
     * Gets the Number of Cached Stages
     * @return The Number of Cached Stages
     */
    public static int getCachedStageCount() {
        if (_stageCache != null) {
            return _stageCache.size();
        } else
        {
            return 0;
        }
    }

    /**
     * Get Cached Stage
     * @param stageId The Stage Identifier
     * @return The Cached Stage, null if the Stage doesn't exist
     */
    public static Stage getCachedStage(int stageId) {
        return _stageCache.get(stageId);
    }

    /**
     * Gets The Record Identifier
     * @return The Record Identifier
     */
    public int getIdentifier() {
        return _recordIdentifier;
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
     * Gets the Badguy ID
     * @return The Badguy ID
     */
    public int getBadguyId() {
        return _badguyId;
    }

    /**
     * Sets the Badguy ID
     * @param badGuyId The Badguy ID
     */
    public void setBadguyId(int badGuyId) {
        _badguyId = badGuyId;
    }

    /**
     * Gets the Badguy
     * @return The Badguy
     */
    public Unit getBadGuy() {

        //TODO: Play with this, need to make sure we can lazily load it

        return Unit.getCachedUnit(_badguyId);
    }

}
