package ca.valleyforge.android.ffbechaincalculator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ca.valleyforge.android.ffbechaincalculator.adapters.UnitListAdapter;
import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Manage Units Activity
 *
 *
 * Since in this code, both team units and badguys are technically the same thing.
 * Since we are calculating damage using the same attributes, the only designation is the
 * unit_class.
 *
 * As such, this activity must be invoked with a unit_class extra provided, this
 * will serve as a filter, to enable this activity to be used for both purposes
 *
 */
public class ManageUnitsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The Log Tag
     */
    private static final String TAG = ManageUnitsActivity.class.getSimpleName();

    /**
     * The Unit Class Extra Key
     */
    public static final String EXTRA_UNIT_CLASS = "UNIT.CLASS";

    /**
     * The Unit (Teammate) Unit Class
     */
    public static final String UNIT_CLASS_UNIT = "UNIT";

    /**
     * The Badguy Unit Class
     */
    public static final String UNIT_CLASS_BADGUY = "BADGUY";

    /**
     * The Default Unit Class, if not provided, is unit, rather than badguy
     */
    private static final String DEFAULT_UNIT_CLASS = UNIT_CLASS_UNIT;

    /**
     * The Units List Loader
     */
    private static final int UNITS_LIST_LOADER = 0;

    /**
     * The Unit Lists Adapter
     */
    private UnitListAdapter _unitsListAdapater;

    /**
     * The Unit Class for filtering purposes
     */
    private String _unitClass;

    /**
     * The Unit Type TextView
     * This is the header item, which should show Units or BadGuys, depending on how the activity
     * was invoked
     */
    TextView _tvUnitType;

    /**
     * The Units Recycler View
     */
    RecyclerView _rvUnits;

    /**
     * Fires on Activity Create
     * @param savedInstanceState The Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_units);

        //Setup Navigation
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Assign Control References
        _tvUnitType = (TextView) findViewById(R.id.tv_unit_type);

        //We need to first determine which unit assets are intended to be managed
        //we get that from the intent that requested this activity, if none are provided
        //we will use the default
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_UNIT_CLASS))
        {
            _unitClass = intent.getStringExtra(EXTRA_UNIT_CLASS);
            Log.d(TAG, "Manging " + _unitClass + " units ");
        }
        else
        {
            _unitClass = DEFAULT_UNIT_CLASS;
            Log.d(TAG, "Unit Class not configured, defaulting to " + _unitClass + " units ");
        }
        _tvUnitType.setText(getUnitTypeFriendlyName(_unitClass));

        //Arm the Recycler View to display Units
        _rvUnits = (RecyclerView) findViewById(R.id.rv_units);
        _rvUnits.setLayoutManager(new LinearLayoutManager(this));

        _unitsListAdapater = new UnitListAdapter(this);
        _rvUnits.setAdapter(_unitsListAdapater);

        //Arm the Loader, to load the Recycler View
        getSupportLoaderManager().initLoader(UNITS_LIST_LOADER, null, this);
    }

    /**
     * Fires after activity has been paused or restarted
     */
    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(UNITS_LIST_LOADER, null, this);
    }

    /**
     * Get Unit Type Text
     * @return The Unit Type FriendlyName
     */
    private String getUnitTypeFriendlyName(String unitType) {
        if (UNIT_CLASS_UNIT.equals(unitType))
        {
            return getResources().getString(R.string.unittype_unit);
        }
        else if (UNIT_CLASS_BADGUY.equals(unitType))
        {
            return getResources().getString(R.string.unittype_badguy);
        }
        else
        {
            return getResources().getString(R.string.stmt_unknown);
        }
    }

    /**
     * Fires on Click Add Unit Button
     * @param view The Button View
     */
    public void onAddUnitClick(View view) {
        Intent intent = new Intent(this, EditUnitActivity.class);
        intent.putExtra(EditUnitActivity.EXTRA_EDIT_MODE, EditUnitActivity.EDIT_MODE_ADD);
        intent.putExtra(EXTRA_UNIT_CLASS, _unitClass);
        startActivity(intent);
    }

    /**
     * Fires on Create Cursor
     * @param id The Loader Identifier
     * @param loaderArguments The Loader Arguments
     * @return The Loader
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle loaderArguments) {
        return new AsyncTaskLoader<Cursor>(this) {

            /**
             * The Units Cursor
             */
            Cursor _unitsCursor;

            /**
             * Fires when the loader first starts
             */
            @Override
            protected void onStartLoading() {
                if (_unitsCursor != null)
                {
                    deliverResult(_unitsCursor);
                }
                else
                {
                    forceLoad();
                }
            }

            /**
             * The Job that fires in the background
             * @return The Cursor
             */
            @Override
            public Cursor loadInBackground() {
                try
                {
                    /*
                        Because this Activity is used for multiple list types, User Units and BadGuys
                        we have armed the activity with a unit class.  The Unit Class is a column
                        in the table, so we are building a selection expression which will filter
                        to those units.
                        Basically, if you entered this screen as managing units, it will show units,
                        whereas if you entered this screen managing badguys, it will show badguys
                     */
                    String selectionExpression = FfbeChainContract.Units.COLUMN_UNIT_CLASS + "=?";
                    String[] selectionArguments = new String[] { _unitClass };
                    return getContentResolver()
                            .query(FfbeChainContract.Units.CONTENT_URI,
                                    null,
                                    selectionExpression,
                                    selectionArguments,
                                    FfbeChainContract.Units.COLUMN_NAME);
                }
                catch (Exception caught)
                {
                    Log.e(TAG, "Failed to load Units");
                    caught.printStackTrace();
                    return null;
                }
            }

            /**
             * Sends result of the load to the registered listener
             * @param data The Data
             */
            public void deliverResult(Cursor data) {
                _unitsCursor = data;
                super.deliverResult(data);
            }

        };
    }

    /**
     * Fires when the loader has finished its work
     * @param loader The Loader
     * @param data The Data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        _unitsListAdapater.swapCursor(data);
    }

    /**
     * Fires when the loader has been reset
     * @param loader The Loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _unitsListAdapater.swapCursor(null);
    }
}
