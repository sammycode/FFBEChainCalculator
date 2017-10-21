package ca.valleyforge.android.ffbechaincalculator.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Final Fantasy Brave Exvius - Chain Calculation - Content Provider
 */
public class FfbeChainContentProvider extends ContentProvider {

    /**
     * The Units Uri Match Identifier
     */
    public static final int UNITS = 100;

    /**
     * The Unit with ID Uri Match Identifier
     */
    public static final int UNIT_WITH_ID = 101;

    /**
     * The Chain Rules Uri Match Identifier
     */
    public static final int CHAIN_RULES = 200;

    /**
     * The Chain Rule with ID Uri Match Identifier
     */
    public static final int CHAIN_RULE_WITH_ID = 201;

    /**
     * The Chain Rule with Name Uri Match Identifier
     */
    public static final int CHAIN_RULE_WITH_NAME = 202;

    /**
     * The Abilities Uri Match Identifier
     */
    public static final int ABILITIES = 300;

    /**
     * The Ability with ID Uri Match Identifier
     */
    public static final int ABILITY_WITH_ID = 301;

    /**
     * The Stages Uri Match Identifier
     */
    public static final int STAGES = 400;

    /**
     * The Stage with ID Uri Match Identifier
     */
    public static final int STAGE_WITH_ID = 401;

    /**
     * The Stage Units Uri Match Identifier
     */
    public static final int STAGE_UNITS = 500;

    /**
     * The Stage Unit with ID Uri Match Identifier
     */
    public static final int STAGE_UNIT_WITH_ID = 501;

    /**
     * The Attacks Uri Match Identifier
     */
    public static final int ATTACKS = 600;

    /**
     * The Attack with ID Uri Match Identifier
     */
    public static final int ATTACK_WITH_ID = 601;

    /**
     * The Chains Uri Match Identifier
     */
    public static final int CHAINS = 700;

    /**
     * The Chain with ID Uri Match Identifier
     */
    public static final int CHAIN_WITH_ID = 701;

    /**
     * The Chain Hits Uri Match Identifier
     */
    public static final int CHAIN_HITS = 800;

    /**
     * The Chain Hit with ID Uri Match Identifier
     */
    public static final int CHAIN_HIT_WITH_ID = 801;

    /**
     * The Uri Matcher
     */
    private static final UriMatcher _uriMatcher = buildUriMatcher();

    /**
     * The FFBE Chain Calculator Database Helper
     */
    private FfbeChainDbHelper _ffbeChainDbHelper;

    /**
     * Builds Uri Matcher
     * @return The Uri Matcher
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //Add Unit Content Uri Matches

        //Units
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_UNITS, UNITS);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_UNITS + "/#", UNIT_WITH_ID);

        //Chain Rules
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAIN_RULES, CHAIN_RULES);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAIN_RULES + "/#", CHAIN_RULE_WITH_ID);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAIN_RULES + "/name/*", CHAIN_RULE_WITH_NAME);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_ABILITIES, ABILITIES);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_ABILITIES + "/#", ABILITY_WITH_ID);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_STAGES, STAGES);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_STAGES + "/#", STAGE_WITH_ID);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_STAGE_UNITS, STAGE_UNITS);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_STAGE_UNITS + "/#", STAGE_UNIT_WITH_ID);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_ATTACKS, ATTACKS);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_ATTACKS + "/#", ATTACK_WITH_ID);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAINS, CHAINS);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAINS + "/#", CHAIN_WITH_ID);

        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAIN_HITS, CHAIN_HITS);
        uriMatcher.addURI(FfbeChainContract.AUTHORITY, FfbeChainContract.PATH_CHAIN_HITS + "/#", CHAIN_HIT_WITH_ID);

        return uriMatcher;
    }

    /**
     * Fires on Create Content Provider
     * @return True when Content Provider Created
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        _ffbeChainDbHelper = new FfbeChainDbHelper(context);
        return true;
    }

    /**
     * Query Content Provider
     * @param uri The Content Uri
     * @param projection The Projection
     * @param selection The Selection Expression
     * @param selectionArguments The Selection Arguments
     * @param sortOder The Sort Order Expression
     * @return The Cursor (Data Returned)
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArguments, @Nullable String sortOder) {
        final SQLiteDatabase db = _ffbeChainDbHelper.getReadableDatabase();
        int uriMatch = _uriMatcher.match(uri);
        Cursor results;

        String id;

        switch (uriMatch) {
            case UNITS:
                //If only content path is specified, allow user defined selection expression to be used
                results = db.query(
                        FfbeChainContract.Units.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);
                break;
            case UNIT_WITH_ID:

                //Extract the Unit ID from the URI, and build selection expression for query
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Units._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.Units.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);
                break;
            case CHAIN_RULES:
                results = db.query(
                        FfbeChainContract.ChainRules.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);
                break;
            case CHAIN_RULE_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainRules._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.ChainRules.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);
                break;

            case CHAIN_RULE_WITH_NAME:
                id = uri.getPathSegments().get(3);
                selection = FfbeChainContract.ChainRules.COLUMN_NAME + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.ChainRules.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);
                break;

            case ABILITIES:
                results = db.query(
                        FfbeChainContract.Abilities.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case ABILITY_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Abilities._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.Abilities.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case STAGES:

                results = db.query(
                        FfbeChainContract.Stages.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;
            case STAGE_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Stages._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.Stages.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case STAGE_UNITS:

                results = db.query(
                        FfbeChainContract.StageUnits.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;
            case STAGE_UNIT_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.StageUnits._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.StageUnits.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case ATTACKS:

                results = db.query(
                        FfbeChainContract.Attacks.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case ATTACK_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Attacks._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.Attacks.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case CHAINS:

                results = db.query(
                        FfbeChainContract.Chains.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case CHAIN_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Chains._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.Chains.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case CHAIN_HITS:

                results = db.query(
                        FfbeChainContract.ChainHits.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            case CHAIN_HIT_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainHits._ID + "=?";
                selectionArguments = new String[] { id };

                results = db.query(
                        FfbeChainContract.ChainHits.TABLE_NAME,
                        projection,
                        selection,
                        selectionArguments,
                        null,
                        null,
                        sortOder);

                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return results;
    }

    /**
     * Insert Content
     * @param uri The Content Uri
     * @param values The Content Values
     * @return The Uri for the newly inserted record
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = _ffbeChainDbHelper.getWritableDatabase();
        int uriMatch = _uriMatcher.match(uri);
        Uri results;

        long id;

        switch (uriMatch) {
            case UNITS:
                id = db.insert(FfbeChainContract.Units.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.Units.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            case CHAIN_RULES:
                id = db.insert(FfbeChainContract.ChainRules.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.ChainRules.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case ABILITIES:
                id = db.insert(FfbeChainContract.Abilities.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.Abilities.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case STAGES:
                id = db.insert(FfbeChainContract.Stages.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.Stages.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            case STAGE_UNITS:
                id = db.insert(FfbeChainContract.StageUnits.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.StageUnits.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            case ATTACKS:
                id = db.insert(FfbeChainContract.Attacks.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.Attacks.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            case CHAINS:
                id = db.insert(FfbeChainContract.Chains.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.Chains.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            case CHAIN_HITS:
                id = db.insert(FfbeChainContract.ChainHits.TABLE_NAME, null, values);
                if (id > 0)
                {
                    results = ContentUris.withAppendedId(FfbeChainContract.ChainHits.CONTENT_URI, id);
                }
                else
                {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return results;
    }

    /**
     * Delete Content
     * @param uri The Content Uru
     * @param selection The Selection Expression
     * @param selectionArguments The Selection Arguments
     * @return The number of rows deleted
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArguments) {
        final SQLiteDatabase db = _ffbeChainDbHelper.getWritableDatabase();
        int uriMatch = _uriMatcher.match(uri);
        int rowsDeleted;

        String id;

        switch (uriMatch) {
            case UNITS:
                //Delete rows with provided selection expression
                rowsDeleted = db.delete(FfbeChainContract.Units.TABLE_NAME, selection, selectionArguments);
                break;
            case UNIT_WITH_ID:
                //if unit identifier provided in uri, build selection expression to delete
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Units._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.Units.TABLE_NAME, selection, selectionArguments);

                break;
            case CHAIN_RULES:
                rowsDeleted = db.delete(FfbeChainContract.ChainRules.TABLE_NAME, selection, selectionArguments);
                break;
            case CHAIN_RULE_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainRules._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.ChainRules.TABLE_NAME, selection, selectionArguments);
                break;

            case ABILITIES:
                rowsDeleted = db.delete(FfbeChainContract.Abilities.TABLE_NAME, selection, selectionArguments);
                break;

            case ABILITY_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Abilities._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.Abilities.TABLE_NAME, selection, selectionArguments);
                break;

            case STAGES:

                rowsDeleted = db.delete(FfbeChainContract.Stages.TABLE_NAME, selection, selectionArguments);

                break;
            case STAGE_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Stages._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.Stages.TABLE_NAME, selection, selectionArguments);

                break;
            case STAGE_UNITS:

                rowsDeleted = db.delete(FfbeChainContract.StageUnits.TABLE_NAME, selection, selectionArguments);

                break;
            case STAGE_UNIT_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.StageUnits._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.StageUnits.TABLE_NAME, selection, selectionArguments);

                break;
            case ATTACKS:

                rowsDeleted = db.delete(FfbeChainContract.Attacks.TABLE_NAME, selection, selectionArguments);

                break;
            case ATTACK_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Attacks._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.Attacks.TABLE_NAME, selection, selectionArguments);

                break;
            case CHAINS:

                rowsDeleted = db.delete(FfbeChainContract.Chains.TABLE_NAME, selection, selectionArguments);

                break;
            case CHAIN_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Chains._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.Chains.TABLE_NAME, selection, selectionArguments);

                break;
            case CHAIN_HITS:

                rowsDeleted = db.delete(FfbeChainContract.ChainHits.TABLE_NAME, selection, selectionArguments);

                break;
            case CHAIN_HIT_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainHits._ID + "=?";
                selectionArguments = new String[] { id };

                rowsDeleted = db.delete(FfbeChainContract.ChainHits.TABLE_NAME, selection, selectionArguments);

                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return rowsDeleted;
    }

    /**
     * Update Content
     * @param uri The Content Uri
     * @param values The new content values
     * @param selection The Selection Expression
     * @param selectionArguments The Selection Arguments
     * @return The Number of Rows Updated
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArguments) {
        final SQLiteDatabase db = _ffbeChainDbHelper.getWritableDatabase();
        int uriMatch = _uriMatcher.match(uri);
        int rowsUpdated;

        String id;

        switch (uriMatch) {
            case UNITS:
                //Update records with provided selection expression
                rowsUpdated = db.update(FfbeChainContract.Units.TABLE_NAME, values, selection, selectionArguments);

                break;
            case UNIT_WITH_ID:
                //id unit identifier provided in uri, build selection expression to update records off of that (one unit)
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Units._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.Units.TABLE_NAME, values, selection, selectionArguments);

                break;
            case CHAIN_RULES:
                rowsUpdated = db.update(FfbeChainContract.ChainRules.TABLE_NAME, values, selection, selectionArguments);
                break;
            case CHAIN_RULE_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainRules._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.ChainRules.TABLE_NAME, values, selection, selectionArguments);

                break;
            case ABILITIES:
                rowsUpdated = db.update(FfbeChainContract.Abilities.TABLE_NAME, values, selection, selectionArguments);
                break;
            case ABILITY_WITH_ID:

                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Abilities._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.Abilities.TABLE_NAME, values, selection, selectionArguments);

                break;

            case STAGES:
                rowsUpdated = db.update(FfbeChainContract.Stages.TABLE_NAME, values, selection, selectionArguments);
                break;
            case STAGE_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Stages._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.Stages.TABLE_NAME, values, selection, selectionArguments);
                break;

            case STAGE_UNITS:
                rowsUpdated = db.update(FfbeChainContract.StageUnits.TABLE_NAME, values, selection, selectionArguments);
                break;
            case STAGE_UNIT_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.StageUnits._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.StageUnits.TABLE_NAME, values, selection, selectionArguments);
                break;

            case ATTACKS:
                rowsUpdated = db.update(FfbeChainContract.Attacks.TABLE_NAME, values, selection, selectionArguments);
                break;
            case ATTACK_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Attacks._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.Attacks.TABLE_NAME, values, selection, selectionArguments);
                break;

            case CHAINS:
                rowsUpdated = db.update(FfbeChainContract.Chains.TABLE_NAME, values, selection, selectionArguments);
                break;
            case CHAIN_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.Chains._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.Chains.TABLE_NAME, values, selection, selectionArguments);
                break;

            case CHAIN_HITS:
                rowsUpdated = db.update(FfbeChainContract.ChainHits.TABLE_NAME, values, selection, selectionArguments);
                break;
            case CHAIN_HIT_WITH_ID:
                id = uri.getPathSegments().get(1);
                selection = FfbeChainContract.ChainHits._ID + "=?";
                selectionArguments = new String[] { id };

                rowsUpdated = db.update(FfbeChainContract.ChainHits.TABLE_NAME, values, selection, selectionArguments);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return rowsUpdated;
    }

    /**
     * Gets MIME Type -
     * Currently not supported, however we may add this if we decide to add the ability to stick unit
     * avatars into the mix.  Who knows...
     * @param uri The Content Uri
     * @return The MIME Type
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not Supported");
    }

}
