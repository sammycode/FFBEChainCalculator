package ca.valleyforge.android.ffbechaincalculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.valleyforge.android.ffbechaincalculator.*;
import ca.valleyforge.android.ffbechaincalculator.data.FfbeChainContract;

/**
 * The Unit List Adapter
 */
public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.UnitListViewHolder> {

    /**
     * The Logger Tag
     */
    private static final String TAG = UnitListAdapter.class.getSimpleName();

    /**
     * The Bound Cursor
     */
    private Cursor _cursor;

    /**
     * The Activity Context
     */
    private Context _context;

    /**
     * Initialize Unit ListAdapter
     * @param context The Activity Context
     */
    public UnitListAdapter(Context context) {
        this._context = context;
    }

    /**
     * Fires when ViewHolder is created, to fill RecyclerView
     * @param parent The Parent ViewGroup
     * @param viewType The ViewType
     * @return The Unit ViewHolder
     */
    @Override
    public UnitListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context)
                .inflate(R.layout.unit_listitem_view, parent, false);
        return new UnitListViewHolder(view);
    }

    /**
     * Fires when RecyclerView needs to display data at a specific position in the cursor
     * @param holder The ViewHolder to bind cursor data to
     * @param position The position of the data in the cursor to bind to
     */
    @Override
    public void onBindViewHolder(UnitListViewHolder holder, int position) {
        if (_cursor == null)
        {
            Log.e(TAG, "Attempted to bind adapter to a null cursor");
            return;
        }

        int idIndex = _cursor.getColumnIndex(FfbeChainContract.Units._ID);
        int nameIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_NAME);
        int levelIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_LEVEL);
        int attackPowerIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_ATTACK_POWER);
        int magicPowerIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_MAGIC_POWER);
        int defRatingIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_DEFENSE_RATING);
        int sprRatingIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_RATING);
        int defBrokenIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_DEFENCE_BROKEN);
        int sprBrokenIndex = _cursor.getColumnIndex(FfbeChainContract.Units.COLUMN_UNIT_SPIRIT_BROKEN);

        _cursor.moveToPosition(position);
        final int unitID = _cursor.getInt(idIndex);
        holder.itemView.setTag(unitID);

        holder._tvUnitName.setText(_cursor.getString(nameIndex));
        holder._tvUnitLevel.setText(_cursor.getString(levelIndex));
        holder._tvAttackPower.setText(_cursor.getString(attackPowerIndex));
        holder._tvMagicPower.setText(_cursor.getString(magicPowerIndex));
        holder._tvDefenseRating.setText(_cursor.getString(defRatingIndex));
        holder._tvSpiritRating .setText(_cursor.getString(sprRatingIndex));
        holder._tvDefenseBroken.setText(_cursor.getString(defBrokenIndex));
        holder._tvSpiritBroken.setText(_cursor.getString(sprBrokenIndex));

    }

    /**
     * Gets Vound Item Count
     * @return The bound item count
     */
    @Override
    public int getItemCount() {
        if (_cursor == null)
        {
            return 0;
        }
        return _cursor.getCount();
    }

    /**
     * Swap Cursor to update data in the recycler
     * @param cursor The New Cursor
     * @return The Original Cursor
     */
    public Cursor swapCursor(Cursor cursor) {
        if (_cursor == cursor)
        {
            return null;
        }

        Cursor temp = _cursor;
        _cursor = cursor;

        if (_cursor != null)
        {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    /**
     * The Unit ListView Holder
     */
    public class UnitListViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Unit Name TextView
         */
        TextView _tvUnitName;

        /**
         * The Unit Level TextView
         */
        TextView _tvUnitLevel;

        /**
         * The Attack Power TextView
         */
        TextView _tvAttackPower;

        /**
         * The Magic Power TextView
         */
        TextView _tvMagicPower;

        /**
         * The Defense Rating TextView
         */
        TextView _tvDefenseRating;

        /**
         * The Spirit Rating TextView
         */
        TextView _tvSpiritRating;

        /**
         * The Defense Broken TextView
         */
        TextView _tvDefenseBroken;

        /**
         * The Spirit Broken TextView
         */
        TextView _tvSpiritBroken;

        /**
         * Initialize Unit ListView Holder
         * @param itemView The ItemView
         */
        public UnitListViewHolder(View itemView) {
            super(itemView);

            _tvUnitName = itemView.findViewById(R.id.tv_unit_name);
            _tvUnitLevel = itemView.findViewById(R.id.tv_unit_level);
            _tvAttackPower = itemView.findViewById(R.id.tv_unit_attack_power);
            _tvMagicPower = itemView.findViewById(R.id.tv_unit_magic_power);
            _tvDefenseRating = itemView.findViewById(R.id.tv_unt_defense_rating);
            _tvSpiritRating = itemView.findViewById(R.id.tv_unit_spirit_rating);
            _tvDefenseBroken = itemView.findViewById(R.id.tv_unit_defense_broken);
            _tvSpiritBroken = itemView.findViewById(R.id.tv_unit_spirit_broken);

            //TODO: Wireup Click Listener

        }

    }

}
