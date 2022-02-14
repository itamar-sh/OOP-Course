package Exams.Exam2019B.Q1;

public interface Armour {
    /**
     * calculate how much damage this armor can absorb from a given weapon.
     * @param weapon The weapon attacking the unit wearing this armor.
     * @return The amount of damage passing through the armor (weapon base damage
    minus amount absorbed)
     */
    double absorbDamage(Weapon weapon);
    /**
     * Set new durability percentage for this armour
     * @param newPercent Durability (in percentage points) to be set for this
    armour.
     */
    void setDurabilty(int newPercent);
    /**
     * Get the current durability of the armour.
     * @return Current durability
     */
    int getDurability();
}
