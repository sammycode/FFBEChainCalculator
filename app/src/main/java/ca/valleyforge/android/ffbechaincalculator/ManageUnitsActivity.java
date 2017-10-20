package ca.valleyforge.android.ffbechaincalculator;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
public class ManageUnitsActivity extends AppCompatActivity {

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
}
