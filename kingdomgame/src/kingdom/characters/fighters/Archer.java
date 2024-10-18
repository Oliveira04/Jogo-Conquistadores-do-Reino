package kingdom.characters.fighters;

public class Archer extends Fighter {
    public Archer() {
        this.setType("Archer");

        this.setLife(5);
        this.setLevel(7);
        this.setExperience(40);

        this.setResistance(3);

        this.setSpeed(3);
        this.setIntelligence(5);
    }

    public Archer(int life, int resistance) {
        this.setType("Archer");

        this.setLife(life);
        this.setLevel(7);
        this.setExperience(40);

        this.setResistance(resistance);

        this.setSpeed(3);
        this.setIntelligence(5);
    }
}