package kingdom.characters.fighters;

public class Arcan extends Fighter {
    public Arcan() {
        this.setType("Arcan");

        this.setLife(3);
        this.setLevel(12);
        this.setExperience(20);

        this.setResistance(8);

        this.setSpeed(5);
        this.setIntelligence(8);
    }

    public Arcan(int life, int resistance) {
        this.setType("Arcan");

        this.setLife(life);
        this.setLevel(12);
        this.setExperience(20);

        this.setResistance(resistance);

        this.setSpeed(5);
        this.setIntelligence(8);
    }
}
