package kingdom;

import kingdom.characters.Player;

class Kingdom extends Player {
    private String kingdomName;
    private String[] territory;
    private int gold;
    private int food;
    private int fighters;

    public Kingdom(String name) {
        super(name);
        this.territory = new String[]{};

    }

    public String getKingdomName() {
        return kingdomName;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public String[] getTerritory() {
        return territory;
    }

    public void setTerritory(String[] territory) {
        this.territory = territory;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getFighters() {
        return fighters;
    }

    public void setFighters(int fighters) {
        this.fighters = fighters;
    }

}