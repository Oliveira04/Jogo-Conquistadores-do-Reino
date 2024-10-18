package kingdom.characters.monsters;

public class Witch extends Monster {
    public Witch() {
        this.setType("Witch");

        this.setLife(80);
        this.setLevel(17);
        this.setExperience(0);

        this.setResistance(9);

        this.setSpeed(6);
        this.setIntelligence(5);
    }
}