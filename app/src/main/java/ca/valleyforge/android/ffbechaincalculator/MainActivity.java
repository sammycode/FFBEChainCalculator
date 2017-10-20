package ca.valleyforge.android.ffbechaincalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Fires when user clicks Manage Units Button
     * @param view The Button View
     */
    public void onManageUnitsClick(View view) {
        Intent intent = new Intent(this, ManageUnitsActivity.class);
        intent.putExtra(ManageUnitsActivity.EXTRA_UNIT_CLASS, ManageUnitsActivity.UNIT_CLASS_UNIT);
        startActivity(intent);
    }

    /**
     * Fires when user clicks Manage Badguys Button
     * @param view The Button View
     */
    public void onManageBadGuysClick(View view) {
        Intent intent = new Intent(this, ManageUnitsActivity.class);
        intent.putExtra(ManageUnitsActivity.EXTRA_UNIT_CLASS, ManageUnitsActivity.UNIT_CLASS_BADGUY);
        startActivity(intent);
    }
}
