package ca.valleyforge.android.ffbechaincalculator;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
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
import ca.valleyforge.android.ffbechaincalculator.models.Unit;

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
     * The Bound Unit
     */
    private Unit _boundUnit;

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
    private int _unitRecordID;

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
     * @param savedInstanceState The Saved Instance State
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

        //If we are editing a unit, we need to pull the current data from the content provider
        if (EDIT_MODE_EDIT.equals(_editMode)) {
            loadUnitData();
        }
        else
        {
            //Since we aren't loading an existing unit, instead lets create a new one
            _boundUnit = new Unit();
        }

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
            _unitRecordID = intent.getIntExtra(EXTRA_RECORD_ID, -1);
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
     * @return The Activity Title
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
     * Gets Existing Record Unit Data,
     * For purposes of editing
     */
    private void loadUnitData() {
        Uri recordUri = ContentUris
                .withAppendedId(FfbeChainContract.Units.CONTENT_URI, _unitRecordID);
        try
        {
            Cursor unitData = getContentResolver().query(recordUri, null, null, null, null);
            _boundUnit  = new Unit(unitData, 0); //Might get an error here if the position is not 0 based
            unitData.close();

            _etUnitName.setText(_boundUnit.getName());
            _etUnitLevel.setText(String.format("%.0f", _boundUnit.getLevel()));
            _etAttackPower.setText(String.format("%.0f", _boundUnit.getAttackPower()));
            _etMagicPower.setText(String.format("%.0f", _boundUnit.getMagicPower()));
            _etDefenseRating.setText(String.format("%.0f", _boundUnit.getDefenseRating()));
            _etSpiritRating.setText(String.format("%.0f", _boundUnit.getSpiritRating()));
            _etDefenseBroken.setText(String.format("%.0f", _boundUnit.getDefenseBrokenPercent()));
            _etSpiritBroken.setText(String.format("%.0f", _boundUnit.getSpiritBrokenPercent()));

        }
        catch (Exception caught)
        {
            Log.e(TAG, "Unable to load Unit Data");
            caught.printStackTrace();
            throw caught;
        }
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

        _boundUnit.setUnitClass(_unitClass);
        _boundUnit.setName(_etUnitName.getText().toString());
        _boundUnit.setLevel(getFloatValueFromEditText(_etUnitLevel));
        _boundUnit.setAttackPower(getFloatValueFromEditText(_etAttackPower));
        _boundUnit.setMagicPower(getFloatValueFromEditText(_etMagicPower));
        _boundUnit.setDefenseRating(getFloatValueFromEditText(_etDefenseRating));
        _boundUnit.setSpiritRating(getFloatValueFromEditText(_etSpiritRating));
        _boundUnit.setDefenseBrokenPercent(getFloatValueFromEditText(_etDefenseBroken));
        _boundUnit.setSpiritBrokenPercent(getFloatValueFromEditText(_etSpiritBroken));

        if (EDIT_MODE_ADD.equals(_editMode))
        {
            Uri result = getContentResolver().insert(FfbeChainContract.Units.CONTENT_URI,
                    _boundUnit.getContentValues());
            Toast.makeText(this, "Saved Unit Data: " + result, Toast.LENGTH_SHORT)
                    .show();
        }
        else if (EDIT_MODE_EDIT.equals(_editMode))
        {
            Uri recordUri = ContentUris
                    .withAppendedId(FfbeChainContract.Units.CONTENT_URI, _unitRecordID);
            int recordsAffected = getContentResolver().update(
                    recordUri,
                    _boundUnit.getContentValues(),
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
        return getFloatFromString(userValue);
    }

    /**
     * Parse Float from String
     * @param rawString The Raw String
     * @return The Float Value, 0 if not parsable for whatever reason
     */
    private float getFloatFromString(String rawString) {
        float parsedValue = 0;
        try
        {
            parsedValue = Float.parseFloat(rawString);
        }
        catch (Exception caught)
        {
            Log.e(TAG, "Unexpected text format, unable to parse float");
            caught.printStackTrace();
        }
        return parsedValue;
    }

}
