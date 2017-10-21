package ca.valleyforge.android.ffbechaincalculator;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

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
     * The Record ID Extra Key
     */
    public static final String EXTRA_RECORD_ID = "RECORD.ID";

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
     * The Unit Record ID, this is needed if editing an existing
     * record, so we can construct a URI to pass back to the content resolver
     * for the update.
     * This is sourced from the Intent.
     */
    private long _unitRecordID;

    /**
     * The Header TextView for the Edit Unit Form
     */
    TextView _tvEditUnitHeader;

    /**
     * The Unit Name EditText
     */
    EditText _etUnitName;

    /**
     * The Unit Level EditText
     */
    EditText _etUnitLevel;

    /**
     * The Attack Power EditText
     */
    EditText _etAttackPower;

    /**
     * The Magic Power EditText
     */
    EditText _etMagicPower;

    /**
     * The Defense Rating EditText
     */
    EditText _etDefenseRating;

    /**
     * The Spirit Rating EditText
     */
    EditText _etSpiritRating;

    /**
     * The Defense Broken EditText
     */
    EditText _etDefenseBroken;

    /**
     * The Spirit Broken EditText
     */
    EditText _etSpiritBroken;

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
        _etUnitName = (EditText) findViewById(R.id.et_unit_name);
        _etUnitLevel = (EditText) findViewById(R.id.et_unit_level);
        _etAttackPower = (EditText) findViewById(R.id.et_unit_attack_power);
        _etMagicPower = (EditText) findViewById(R.id.et_unit_magic_power);
        _etDefenseRating = (EditText) findViewById(R.id.et_unit_defense_rating);
        _etSpiritRating = (EditText) findViewById(R.id.et_unit_spirit_rating);
        _etDefenseBroken = (EditText) findViewById(R.id.et_unit_defense_broken);
        _etSpiritBroken = (EditText) findViewById(R.id.et_unit_spirit_broken);

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

        //If the Record ID has been provided, grab that, we will need it to save the record
        if (intent.hasExtra(EXTRA_RECORD_ID))
        {
            //An important thing to note here, is that if the ID has not been provided,
            //we are asigning it to -1 so the update function wont update everything in the table
            _unitRecordID = intent.getLongExtra(EXTRA_RECORD_ID, -1);
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
        saveUnitData();
        finish();
    }

    /**
     * Save Unit Data
     */
    private void saveUnitData() {
        //Simple Validation
        if (_etUnitName.getText().length() == 0)
        {
            Toast.makeText(this, "Must enter a unit name", Toast.LENGTH_SHORT)
                .show();
            return;
        }
        //Do not really need to validate other numeric fields, since we are defaulting to 0
        ContentValues values = new ContentValues();
        values.put(FfbeChainContract.Units.COLUMN_UNIT_CLASS, _unitClass);
        values.put(FfbeChainContract.Units.COLUMN_NAME,
                _etUnitName.getText().toString());
        values.put(FfbeChainContract.Units.COLUMN_UNIT_LEVEL,
                getFloatValueFromEditText(_etUnitLevel));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_ATTACK_POWER,
                getFloatValueFromEditText(_etAttackPower));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_MAGIC_POWER,
                getFloatValueFromEditText(_etMagicPower));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_DEFENSE_RATING,
                getFloatValueFromEditText(_etDefenseRating));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_RATING,
                getFloatValueFromEditText(_etSpiritRating));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_DEFENCE_BROKEN,
                getFloatValueFromEditText(_etDefenseBroken));
        values.put(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_BROKEN,
                getFloatValueFromEditText(_etSpiritBroken));

        if (EDIT_MODE_ADD.equals(_editMode))
        {
            Uri result = getContentResolver().insert(FfbeChainContract.Units.CONTENT_URI,
                    values);
            Toast.makeText(this, "Saved Unit Data: " + result, Toast.LENGTH_SHORT)
                    .show();
        }
        else if (EDIT_MODE_EDIT.equals(_editMode))
        {
            Uri recordUri = ContentUris
                    .withAppendedId(FfbeChainContract.Units.CONTENT_URI, _unitRecordID);
            int recordsAffected = getContentResolver().update(
                    recordUri,
                    values,
                    null,
                    null);
            if (recordsAffected == 1)
            {
                Toast.makeText(this, "Updated Unit Data: " + recordUri, Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
                Toast.makeText(this, "Unable to Update Unit Data: " + recordUri, Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else
        {
            Log.e(TAG, "Unknown Edit Mode");
            throw new UnsupportedOperationException("Unsupported Edit Mode");
        }
    }

    /**
     * Get Float Value from user entered text in EditText Control
     * @param editControl The Edit Text Control
     * @return The Float Value, will default to returning 0 should the value not be parsable
     */
    private float getFloatValueFromEditText(EditText editControl) {
        String userValue = editControl.getText().toString();
        float parsedValue = 0;
        try
        {
            parsedValue = Float.parseFloat(userValue);
        }
        catch (Exception caught)
        {
            Log.e(TAG, "Unexpected text format, unable to parse float");
        }
        return parsedValue;
    }

}
