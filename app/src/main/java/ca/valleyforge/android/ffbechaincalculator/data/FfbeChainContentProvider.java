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

        //TODO: Add additional matches as content types become available

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

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return results;
    }

    /**
     * Insert Content
     * @param uri The Content Uri
     * @param values The Content Values
     * @return
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

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        return rowsDeleted;
    }

    /**
     * Update Content
     * @param uri
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
