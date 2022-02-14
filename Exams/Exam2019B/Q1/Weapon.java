package Exams.Exam2019B.Q1;

public interface Weapon {
    double getBaseDamage();
    /**
     * deliver "getDamage" points onto target.
     * @param target The object to be targeted.
     * @return How much damage was delivered to target.
     */
    double attack( Attackable target);

}
