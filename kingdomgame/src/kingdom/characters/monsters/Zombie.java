package kingdom.characters.monsters;

public class Zombie extends Monster {
    public Zombie() {
        this.setType("Zombie");

        this.setLife(30);
        this.setLevel(11);
        this.setExperience(0);

        this.setResistance(2);

        this.setSpeed(1);
        this.setIntelligence(1);
    }
}
