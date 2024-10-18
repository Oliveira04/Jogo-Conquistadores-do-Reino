package kingdom.characters.fighters;

public class Warrior extends Fighter {
    public Warrior() {
        this.setType("Warrior");

        this.setLife(7);
        this.setLevel(10);
        this.setExperience(60);

        this.setResistance(5);

        this.setSpeed(8);
        this.setIntelligence(3);
    }

    public Warrior(int life, int resistance) {
        this.setType("Warrior");

        this.setLife(life);
        this.setLevel(10);
        this.setExperience(60);

        this.setResistance(resistance);

        this.setSpeed(8);
        this.setIntelligence(3);
    }
}
