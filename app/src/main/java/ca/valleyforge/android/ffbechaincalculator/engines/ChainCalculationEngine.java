package ca.valleyforge.android.ffbechaincalculator.engines;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;
import ca.valleyforge.android.ffbechaincalculator.models.Ability;
import ca.valleyforge.android.ffbechaincalculator.models.Attack;
import ca.valleyforge.android.ffbechaincalculator.models.Chain;
import ca.valleyforge.android.ffbechaincalculator.models.Stage;
import ca.valleyforge.android.ffbechaincalculator.models.Unit;

/**
 * The Chain Calculation Engine
 */
public class ChainCalculationEngine extends AsyncTaskLoader<Chain> {

    /**
     * The Calculated Chain
     */
    private Chain _calculatedChain;

    /**
     * The Chain Identifier
     */
    private int _stageId;

    /**
     * Initialize the Calculation Engine
     *
     * @param context The Activity Context
     * @param stageId The Stage Identifier
     */
    public ChainCalculationEngine(Context context, int stageId) {
        super(context);
        _stageId = stageId;
    }

    /**
     * Fires when loader first starts
     */
    @Override
    protected void onStartLoading() {
        if (_calculatedChain != null) {
            deliverResult(_calculatedChain);
        }
        else
        {
            forceLoad();
        }
    }

    /**
     * The Calculation Job, fired in the background
     * @return The Chain
     */
    @Override
    public Chain loadInBackground() {
        Chain calculatedChain = new Chain();

        //Grab our stage
        Uri stageUri = ContentUris.withAppendedId(FfbeChainContract.Stages.CONTENT_URI, _stageId);
        Cursor stageCursor = getContext().getContentResolver().query(stageUri, null, null, null, null);
        Stage stage = new Stage(stageCursor, 0);
        stageCursor.close();

        //Cache the badguy
        Uri badguyUri = ContentUris.withAppendedId(FfbeChainContract.Units.CONTENT_URI, stage.getBadguyId());
        Cursor badguyCursor = getContext().getContentResolver().query(badguyUri, null, null, null, null);
        new Unit(badguyCursor, 0);
        badguyCursor.close();

        //Cache the Stage Units
        String stageUnitsSelectionExpression = FfbeChainContract.StageUnits.COLUMN_STAGE_ID + "=?";
        String[] stageUnitsSelectionArguments = new String[] { Integer.toString(stage.getIdentifier()) };
        Cursor stageUnitsCursor = getContext().getContentResolver().query(
                FfbeChainContract.StageUnits.CONTENT_URI,
                null,
                stageUnitsSelectionExpression,
                stageUnitsSelectionArguments,
                null);
        for (int i = 0; i < stageUnitsCursor.getCount(); i++) {
            new Unit(stageUnitsCursor, i);
        }
        stageCursor.close();

        //Cache attacks and abilities, we should have everything else connected
        String attacksSelectionExpression = FfbeChainContract.Attacks.COLUMN_STAGE_ID + "=?";
        String[] attacksSelectionArguments = new String[] { Integer.toString(stage.getIdentifier()) };
        Cursor attacksCursor = getContext().getContentResolver().query(
                FfbeChainContract.Attacks.CONTENT_URI,
                null,
                attacksSelectionExpression,
                attacksSelectionArguments,
                null);
        for (int i = 0; i < attacksCursor.getCount(); i++) {
            new Attack(attacksCursor, i);
        }
        attacksCursor.close();

        Cursor abilitiesCursor = getContext().getContentResolver().query(
                FfbeChainContract.Abilities.CONTENT_URI,
                null,
                null,
                null,
                null);
        for (int i = 0; i < abilitiesCursor.getCount(); i++) {
            new Ability(abilitiesCursor, i);
        }

        //TODO: Create new Chain Object
        //TODO: Insert new Chain Object into SQLite Database
        //TODO: Run Calculation building ChainHit objects, and get them inserted into the SQLite Database

        /*
            At this point, the calculated chain information should all be referenced from the returned chain object,
            and that should be enough to create some bindings for some UI elements to display the results
         */

        return calculatedChain;
    }

    /**
     * Sends result of the load to the registered listener
     * @param calculatedChain The Calculated Chain
     */
    public void deliverResult(Chain calculatedChain) {
        _calculatedChain = calculatedChain;
        super.deliverResult(_calculatedChain);
    }

}
