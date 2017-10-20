package ca.valleyforge.android.ffbechaincalculator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Final Fantasy Brave Exvius - Chain Calculation - SQLite Database Open Helper
 */
public class FfbeChainDbHelper extends SQLiteOpenHelper {

    /**
     * The Database Filename
     */
    public static final String DATABASE_NAME = "ffbe_chaincalc.db";

    /**
     * The Database Version
     */
    public static final int VERSION = 1;

    /**
     * SQL Query - Drop Units Table
     */
    private static final String DROP_UNITS_TABLE =
            "DROP TABLE IF EXISTS " + FfbeChainContract.Units.TABLE_NAME;

    /**
     * SQL Query - Create Units Table
     */
    private static final String CREATE_UNITS_TABLE =
            "CREATE TABLE " + FfbeChainContract.Units.TABLE_NAME + " ( " +
                    FfbeChainContract.Units._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FfbeChainContract.Units.COLUMN_UNIT_CLASS + " TEXT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_NAME + " TEXT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_LEVEL + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_ATTACK_POWER + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_MAGIC_POWER + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_DEFENSE_RATING + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_RATING + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_DEFENCE_BROKEN + " FLOAT NOT NULL, " +
                    FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_BROKEN + " FLOAT NOT NULL " +
                    " ); ";

    /**
     * Initialize the FFBE Chain Calculation OpenDatabase Helper
     * @param context The Activity Context
     */
    public FfbeChainDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Fires on Create SQLite Database
     * @param db    The Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_UNITS_TABLE);
    }

    /**
     * Fires on Upgrade SQLite Database -
     *  Since we arent at all concerned about upcrading versions of the database,
     *  schema changes will drop and recreate the existing database to bring it up to
     *  version.
     * @param db                The Database
     * @param currentVersion    The Current Version (Not Used)
     * @param newVersion        The New Version (not Used)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int currentVersion, int newVersion) {
        db.execSQL(DROP_UNITS_TABLE);
        onCreate(db);
    }
}
