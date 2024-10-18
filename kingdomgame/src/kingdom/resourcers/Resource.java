package kingdom.resourcers;

public class Resource {
    private String type;
    private int goldCost;
    private int durability;


    public Resource(){
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

}
