package ca.valleyforge.android.ffbechaincalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

    }
}
