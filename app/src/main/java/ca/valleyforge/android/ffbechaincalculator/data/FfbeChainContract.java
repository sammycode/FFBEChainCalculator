package ca.valleyforge.android.ffbechaincalculator.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Final Fantasy Brave Exvius - Chain Calculation - Data Contract
 */
public class FfbeChainContract {

    /**
     * The Content Authority
     */
    public static final String AUTHORITY = "ca.valleyforge.android.ffbechaincalculator";

    /**
     * The Base Content Uri
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * The Units Content Path
     */
    public static final String PATH_UNITS = "units";

    public static final class Units implements BaseColumns {

        /**
         * The Units Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_UNITS)
                .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "units";

        /**
         * The Unit Class, Column Name
         */
        public static final String COLUMN_UNIT_CLASS = "unit_class";

        /**
         * The Unit Name, Column Name
         */
        public static final String COLUMN_NAME = "unit_name";

        /**
         * The Unit Level, Column Name
         */
        public static final String COLUMN_UNIT_LEVEL = "unit_level";

        /**
         * The Unit Attack Power, Column Name
         */
        public static final String COLUMN_UNIT_ATTACK_POWER = "attack_power";

        /**
         * The Unit Magic Power, Column Name
         */
        public static final String COLUMN_UNIT_MAGIC_POWER = "magic_power";

        /**
         * The Unit Defence Rating, Column Name
         */
        public static final String COLUMN_UNIT_DEFENSE_RATING = "defence_rating";

        /**
         * The Unit Spirit Rating, Column Name
         */
        public static final String COLUMN_UNIT_SPIRIT_RATING = "spirit_rating";

        /**
         * The Unit Defense Broken Percentage, Column Name
         */
        public static final String COLUMN_UNIT_DEFENCE_BROKEN = "defence_broken";

        /**
         * The Unit Spirit Broken Percentage, Column Name
         */
        public static final String COLUMN_UNIT_SPIRIT_BROKEN = "spirit_broken";

    }

}
