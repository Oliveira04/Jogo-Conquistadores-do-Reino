package kingdom.characters;

public class Player extends Character {
    private String name;

    public Player(String name) {
        this.setName(name);
        this.setType("Player");
        this.setLife(12);
        this.setLevel(1);
        this.setExperience(0);
        this.setIntelligence(30);
        this.setResistance(2);
        this.setSpeed(5);
        this.setIsEnemy(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
