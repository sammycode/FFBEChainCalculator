package ca.valleyforge.android.ffbechaincalculator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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
     * SQL Query - Drop Chain Rules Table
     */
    private static final String DROP_CHAIN_RULES_TABLE =
            "DROP TABLE IF EXISTS " + FfbeChainContract.ChainRules.TABLE_NAME;

    /**
     * SQL Query - Create Chain Rules Table
     */
    private static final String CREATE_CHAIN_RULES_TABLE =
            "CREATE TABLE " + FfbeChainContract.ChainRules.TABLE_NAME + " ( " +
                    FfbeChainContract.ChainRules._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FfbeChainContract.ChainRules.COLUMN_NAME + " TEXT NOT NULL, " +
                    FfbeChainContract.ChainRules.COLUMN_DAMAGE_MODIFIER + " FLOAT NOT NULL, " +
                    FfbeChainContract.ChainRules.COLUMN_HIT_MODIFIER_CAP + " INTEGER NOT NULL " +
                    " ); ";

    /**
     * SQL Query - Drop Abilities Table
     */
    private static final String DROP_ABILITIES_TABLE =
            "DROP TABLE IF EXISTS " + FfbeChainContract.Abilities.TABLE_NAME;

    /**
     * SQL Query - Create Abilities Table
     */
    private static final String CREATE_ABILITIES_TABLE =
            "CREATE TABLE " + FfbeChainContract.Abilities.TABLE_NAME + " ( " +
                    FfbeChainContract.Abilities._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FfbeChainContract.Abilities.COLUMN_NAME + " TEXT NOT NULL, " +
                    FfbeChainContract.Abilities.COLUMN_DAMAGE_MODIFIER + " FLOAT NOT NULL, " +
                    FfbeChainContract.Abilities.COLUMN_IGNORE_DEFENSE_MODIFIER + " FLOAT NOT NULL, " +
                    FfbeChainContract.Abilities.COLUMN_IGNORE_SPIRIT_MODIFIER + " FLOAT NOT NULL, " +
                    FfbeChainContract.Abilities.COLUMN_NUMBER_OF_HITS + " INTEGER NOT NULL, " +
                    FfbeChainContract.Abilities.COLUMN_ABILITY_TYPE + " TEXT NOT NULL " +
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
        //Create Table Structure
        db.execSQL(CREATE_UNITS_TABLE);
        db.execSQL(CREATE_CHAIN_RULES_TABLE);
        db.execSQL(CREATE_ABILITIES_TABLE);

        //Seed Data
        seedChainRules(db);
    }

    /**
     * Seed ChainRules Data
     * @param db The Database
     */
    private void seedChainRules(SQLiteDatabase db) {
        /*
            Prepare the insert statement to get the data passed in
         */
        String sqlInsertChainRule = "INSERT INTO "
                + FfbeChainContract.ChainRules.TABLE_NAME + " ( " +
                FfbeChainContract.ChainRules.COLUMN_NAME + ", " +
                FfbeChainContract.ChainRules.COLUMN_DAMAGE_MODIFIER + ", " +
                FfbeChainContract.ChainRules.COLUMN_HIT_MODIFIER_CAP + " ) VALUES (?,?,?);";
        SQLiteStatement insertChainRuleStatement = db.compileStatement(sqlInsertChainRule);

        //Begin transaction before starting seed process
        db.beginTransaction();


        insertChainRuleStatement.clearBindings();
        insertChainRuleStatement.bindString(1, "Normal");
        insertChainRuleStatement.bindDouble(2, 10);
        insertChainRuleStatement.bindLong(3, 30);
        insertChainRuleStatement.execute();

        insertChainRuleStatement.clearBindings();
        insertChainRuleStatement.bindString(1, "Element");
        insertChainRuleStatement.bindDouble(2, 30);
        insertChainRuleStatement.bindLong(3, 10);
        insertChainRuleStatement.execute();

        insertChainRuleStatement.clearBindings();
        insertChainRuleStatement.bindString(1, "Spark");
        insertChainRuleStatement.bindDouble(2, 50);
        insertChainRuleStatement.bindLong(3, 6);
        insertChainRuleStatement.execute();

        //Complete the transaction to save the data
        db.setTransactionSuccessful();
        db.endTransaction();
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
        db.execSQL(DROP_ABILITIES_TABLE);
        db.execSQL(DROP_CHAIN_RULES_TABLE);
        db.execSQL(DROP_UNITS_TABLE);
        onCreate(db);
    }
}
