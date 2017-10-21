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

    /**
     * The Chain Rules Content Path
     */
    public static final String PATH_CHAIN_RULES = "chain_rules";

    /**
     * The Abilities Content Path
     */
    public static final String PATH_ABILITIES = "abilities";

    /**
     * The Stages Content Path
     */
    public static final String PATH_STAGES = "stages";

    /**
     * The Stage Units Content Path
     */
    public static final String PATH_STAGE_UNITS = "stage_units";

    /**
     * The Attacks Content Path
     */
    public static final String PATH_ATTACKS = "attacks";

    /**
     * The Chains Content Path
     */
    public static final String PATH_CHAINS = "chains";

    /**
     * The Chain Hits Content Path
     */
    public static final String PATH_CHAIN_HITS = "chain_hits";

    /**
     * The Units Data Contract
     */
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

    /**
     * The Chain Rules Data Contract
     */
    public static final class ChainRules implements BaseColumns {

        /**
         * The Chain Rules Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_CHAIN_RULES)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "chain_rules";

        /**
         * The Name, Column Name
         */
        public static final String COLUMN_NAME = "name";

        /**
         * The Damage Modifier, Column Name
         */
        public static final String COLUMN_DAMAGE_MODIFIER = "damage_modifier";

        /**
         * The Hit Modifier Cap, Column Name
         */
        public static final String COLUMN_HIT_MODIFIER_CAP = "hit_modifier_cap";

    }

    /**
     * The Abilities Data Contract
     */
    public static final class Abilities implements BaseColumns {

        /**
         * The Abilities Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_ABILITIES)
                        .build();

        /**
         * The Expected Physical Ability Type Value
         */
        public static final String ABILITY_TYPE_PHYSICAL = "PHYSICAL";

        /**
         * The Expected Magic Ability Type Value
         */
        public static final String ABILITY_TYPE_MAGIC = "MAGIC";

        /**
         * The Expected Hybird Ability Type Value
         */
        public static final String ABILITY_TYPE_HYBRID = "HYBRID";

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "abilities";

        /**
         * The Name, Column Name
         */
        public static final String COLUMN_NAME = "name";

        /**
         * The Damage Modifier, Column Name
         */
        public static final String COLUMN_DAMAGE_MODIFIER = "damage_modifier";

        /**
         * The Ignore Defense Modifier Column Name
         */
        public static final String COLUMN_IGNORE_DEFENSE_MODIFIER = "ignore_def_modifier";

        /**
         * The Ignore Spirit Modifier, Column Name
         */
        public static final String COLUMN_IGNORE_SPIRIT_MODIFIER = "ignore_spr_modifier";

        /**
         * The Number of Hits, Column Name
         */
        public static final String COLUMN_NUMBER_OF_HITS = "number_of_hits";

        /**
         * The Ability Type, Column Name
         */
        public static final String COLUMN_ABILITY_TYPE = "ability_type";

    }

    /**
     * The Stages Data Contract
     *
     * A stage is a configuration, or setup for a chain to simulate.
     * This will have to be broken down in a relative fashion, as we're backing this in an
     * relational database format.
     *
     * So this represents a stage header, this will have descriptive meta-data about the stage,
     * and will be the anhor point for assigned units, a badguy as well as the calculated chain
     *
     * Because Abilities are being assigned to a unit, at least for now, im not certain how we
     * will handle "dirty" configurations.  In other words, if you change the configuration of units
     * unit stats or the stats on the badguy, what should happen to the chain data.
     *
     * I guess for the time being, this will only support one calculated chain at a time, and at the
     * time of modifying any part of it's configuration, we simply drop the chain calculation
     */
    public static final class Stages implements BaseColumns {

        /**
         * The Stages Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_STAGES)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "stages";

        /**
         * The Name, Column Name
         */
        public static final String COLUMN_NAME = "name";

        /**
         * The Badguy Identifier, Column Name
         */
        public static final String COLUMN_BADGUY_ID = "badguy_id";

    }

    /**
     * The Stage Units Data Contact
     */
    public static final class StageUnits implements BaseColumns {

        /**
         * The Stage Units Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_STAGE_UNITS)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "stage_units";

        /**
         * The Stage Identifier, Column Name
         */
        public static final String COLUMN_STAGE_ID = "stage_id";

        /**
         * The Unit Identifier, Column Name
         */
        public static final String COLUMN_UNIT_ID = "unit_id";

    }

    /**
     * The Attacks Data Contract
     */
    public static final class Attacks implements BaseColumns {

        /**
         * The Attacks Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_ATTACKS)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "stage_attacks";

        /**
         * The Stage Identifier, Column Name
         */
        public static final String COLUMN_STAGE_ID = "stage_id";

        /**
         * The Unit Identifier, Column Name
         */
        public static final String COLUMN_UNIT_ID = "unit_id";

        /**
         * The Ability Identifier, Column Name
         */
        public static final String COLUMN_ABILITY_ID = "ability_id";

    }

    /**
     * The Chains Data Contract
     */
    public static final class Chains implements BaseColumns {

        /**
         * The Chains Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_CHAINS)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "chains";

        /**
         * The Stage Identifier, Column Name
         */
        public static final String COLUMN_STAGE_ID = "stage_id";

        /**
         * The Total High Damage, Column Name
         */
        public static final String COLUMN_TOTAL_DAMAGE_HIGH = "total_damage_high";

        /**
         * The Total Mid Damage, Column Name
         */
        public static final String COLUMN_TOTAL_DAMAGE_MID = "total_damage_mid";

        /**
         * The Total Low Damage, Column Name
         */
        public static final String COLUMN_TOTAL_DAMAGE_LOW = "total_damage_low";

    }

    /**
     * The Chain Hits Data Contract
     */
    public static final class ChainHits implements BaseColumns {

        /**
         * The Chain Hits Content Uri
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_CHAIN_HITS)
                        .build();

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "chain_hits";

        /**
         * The Chain Identifier, Column Name
         */
        public static final String COLUMN_CHAIN_ID = "chain_id";

        /**
         * The Attack Identifier, Column Name
         */
        public static final String COLUMN_ATTACK_ID = "attack_id";

        /**
         * The Hit Number, Column Name
         */
        public static final String COLUMN_HIT_NUMBER = "hit_number";

        /**
         * The High Damage, Column Name
         */
        public static final String COLUMN_DAMAGE_HIGH = "damage_high";

        /**
         * The Mid Damage, Column Name
         */
        public static final String COLUMN_DAMAGE_MID = "damage_mid";

        /**
         * The Low Damage, Column Name
         */
        public static final String COLUMN_DAMAGE_LOW = "damage_low";

    }

}
