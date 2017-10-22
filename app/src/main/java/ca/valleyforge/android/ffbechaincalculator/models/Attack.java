package ca.valleyforge.android.ffbechaincalculator.models;

/**
 * The Attack Model
 *
 * This model is used directly in calculations,
 * as a result, I am storing references to instances to attackers and abilities, rather than
 * record identifiers.
 *
 * Just going to figure out how exactly I want to abstract this from the database.
 *
 * Update: I think the proper way to aproach this, is to store the record identifier here,
 * and to add some get accessors, which should be able to lookup the underlying record model
 * using cache mechanisms.
 *
 * In order to properly support this, the models should cache new records as needed, without
 * clearing the cache every time.  This can be done using the cursor constructor, which would
 * replace the model in the cache by ID, where the ID is the record identifier.  This should
 * ensure an up-to-date model whenever we need it, and without having to make excessive calls
 * to the content provider.
 *
 */
public class Attack {

    /**
     * The Record Identifier
     */
    private int _recordIdentifier;

    /**
     * The ID Index
     */
    private int _idIndex;

    /**
     * The Attacker ID Index
     */
    private int _attackerIdIndex;

    /**
     * The Ability ID index
     */
    private int _abilityIdIndex;

    /**
     * The Attacker ID
     */
    private int _attackerId;

    /**
     * The Ability ID
     */
    private int _abilityId;

//    /**
//     * The Attacker
//     */
//    private Unit _attacker;

    /**
     * The Ability
     */
    private Ability _ability;

    /**
     * Initialize the Attack
     */
    public Attack() {}

    /**
     * Initialize the Attack
     * @param attackerId The Attacker ID
     * @param abilityId The Ability ID
     */
    public Attack(int attackerId, int abilityId) {
        _attackerId = attackerId;
        _abilityId = abilityId;
    }

    //TODO: Wireup Cursor Voodoo

    /**
     * Get Attacker
     * @return The Attacker
     */
    public Unit getAttacker() {
        return Unit.getCachedUnit(_attackerId);
    }

}
