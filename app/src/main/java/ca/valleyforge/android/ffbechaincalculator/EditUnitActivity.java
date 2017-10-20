package ca.valleyforge.android.ffbechaincalculator;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * The Edit Unit Activity
 *
 * This activity is used to edit Unit Data, which can be done
 * to either modify an existing unit, or to create a new unit.
 *
 * Much like the "Manage Units" Activity, this relies on some context provided by
 * intent extra data, which can tell if either new or edit mode, as well
 * as the record identifier for a particular unit in the event of edit mode.
 *
 * The record identifier must be provided by way of content Uri when saving changes,
 * note that this URI is supplied when a new record is created.  But the Uri is
 * predictable enough, that we can construct one on the fly..
 *
 */
public class EditUnitActivity extends AppCompatActivity {

    /**
     * The Logger Tag
     */
    private static final String TAG = EditUnitActivity.class.getSimpleName();

    /**
     * The Edit Mode Extra Key
     */
    public static final String EXTRA_EDIT_MODE = "EDIT.MODE";

    /**
     * The Add Edit Mode Extra Value
     */
    public static final String EDIT_MODE_ADD = "EDIT.MODE.ADD";

    /**
     * The "Edit" Edit Mode Extra VALUE
     */
    public static final String EDIT_MODE_EDIT = "EDIT.MODE.EDIT";

    /**
     * The Default Edit Mode (Add)
     */
    private static final String DEFAULT_EDIT_MODE = EDIT_MODE_ADD;

    /**
     * The Edit Mode
     */
    private String _editMode;

    /**
     * The Configured Unit Class
     */
    public String _unitClass;

    /**
     * The Header TextView for the Edit Unit Form
     */
    TextView _tvEditUnitHeader;

    /**
     * Fires on Create Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);

        //Wireup Controls
        _tvEditUnitHeader = (TextView) findViewById(R.id.tv_edit_unit_header);

        //Setup Navigation
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        configureIntentExtras();
        setTitle(getActivityTitle(_editMode));
        _tvEditUnitHeader.setText(getEditUnitHeaderText(_unitClass));

    }

    /**
     * Configure Activity with Intent Extra Settings
     */
    private void configureIntentExtras() {
        Intent intent = getIntent();
        //First determine the edit mode
        if (intent.hasExtra(EXTRA_EDIT_MODE))
        {
            _editMode = intent.getStringExtra(EXTRA_EDIT_MODE);
            Log.d(TAG, "Edit Unit Mode: " + _editMode);
        }
        else
        {
            _editMode = DEFAULT_EDIT_MODE;
            Log.d(TAG, "Edit Mode not configured, defaulting to " + _editMode + " units ");
        }


        //Determine the unit class type
        if (intent.hasExtra(ManageUnitsActivity.EXTRA_UNIT_CLASS))
        {
            _unitClass = intent.getStringExtra(ManageUnitsActivity.EXTRA_UNIT_CLASS);
            Log.d(TAG, "Unit Class Mode: " + _unitClass);
        }
        else
        {
            Log.e(TAG, "Unknown Unit Type, we really need to know this by now");
            throw new UnsupportedOperationException("Unsupported Unit Type");
        }
    }

    /**
     * Gets the Activity Title, based on edit mode
     * @param editMode The Edit Mode
     * @return
     */
    private String getActivityTitle(String editMode) {
        if (EDIT_MODE_ADD.equals(editMode))
        {
            return getResources().getString(R.string.eu_new_activity_label);
        }
        else if (EDIT_MODE_EDIT.equals(editMode))
        {
            return getResources().getString(R.string.eu_edit_activity_label);
        }
        else
        {
            return getResources().getString(R.string.stmt_unknown);
        }
    }

    /**
     * Gets the Edit Unit Form Header Text
     * @param unitClass The unit class
     * @return the header text
     */
    private String getEditUnitHeaderText(String unitClass) {
        if (ManageUnitsActivity.UNIT_CLASS_UNIT.equals(unitClass))
        {
            return getResources().getString(R.string.eu_form_header_unit_label);
        }
        else if (ManageUnitsActivity.UNIT_CLASS_BADGUY.equals(unitClass))
        {
            return getResources().getString(R.string.eu_form_header_badguy_label);
        }
        else
        {
            return getResources().getString(R.string.stmt_unknown);
        }
    }

    /**
     * Fires on Cancel Button Click
     * @param view The Button View
     */
    public void onCancelActionClick(View view) {
        finish();
    }

    /**
     * Fires on Save Button Click
     * @param view The Button View
     */
    public void onSaveUnitClick(View view) {
        //TODO: Implement Save Functionality
        finish();
    }
}
