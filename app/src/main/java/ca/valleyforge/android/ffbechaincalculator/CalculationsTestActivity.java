package ca.valleyforge.android.ffbechaincalculator;

import android.content.ContentUris;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;
import ca.valleyforge.android.ffbechaincalculator.models.Stage;
import ca.valleyforge.android.ffbechaincalculator.models.Unit;

/**
 * The Calculations Test Activityt
 *
 * This is a temporary activity, which is just in place to test the content provider etc,
 * This should allow us to hardcode some data to run calculations on, and see how well it meshes with
 * the content provider set up...
 *
 */
public class CalculationsTestActivity extends AppCompatActivity {

    /**
     * The Logger Tag
     */
    private static final String TAG = CalculationsTestActivity.class.getSimpleName();

    /**
     * Fires on Create Activity
     * @param savedInstanceState The Saved Instance Stage
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations_test);
    }

    /**
     * Fires when Calculate button is clicked
     * @param view The Button View
     */
    public void onCalculateClick(View view) {

        /*
            Maybe the best thing here, would be to have the UI create a stage first.
            Then from the stage, add units to it.  
         */

        Unit unit1 = new Unit("Ashe", 100, 300, 650, 100, 100, 0, 0);
        Unit unit2 = new Unit("Ashe2", 100, 300, 650, 100, 100, 0, 0);
        Unit unit3 = new Unit("Ashe3", 100, 300, 650, 100, 100, 0, 0);
        Unit unit4 = new Unit("Ashe4", 100, 300, 650, 100, 100, 0, 0);
        Unit unit5 = new Unit("Ashe5", 100, 300, 650, 100, 100, 0, 0);

        Unit badGuy = new Unit("badguy1", 100, 200, 200, 200, 200, 0, 0);

        //TODO: Review possible disconnect, the stage wants just an ID for the badguy, which means it must be sourced from the
        //SQLite database, can't just hard code everything, and we should be able to... maybe...
        Stage chainStage = new Stage("Chain 1", 1);




        return;
    }
}
