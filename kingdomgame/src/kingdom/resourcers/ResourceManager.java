package kingdom.resourcers;

public class ResourceManager {
    private int availableGold;
    private int availableMaterials;
    private int availableFood;


    public ResourceManager(){
    }

    public int getAvailableGold() {
        return availableGold;
    }

    public void setAvailableGold(int availableGold) {
        this.availableGold = availableGold;
    }

    public int getAvailableFood() {
        return availableFood;
    }

    public void setAvailableFood(int availableFood) {
        this.availableFood = availableFood;
    }

    public int getAvailableMaterials() {
        return availableMaterials;
    }

    public void setAvailableMaterials(int availableMaterials) {
        this.availableMaterials = availableMaterials;
    }

}
