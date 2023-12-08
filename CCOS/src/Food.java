import java.io.Serializable;

public abstract class Food implements Serializable,Comparable<Food> {
    private final int foodId;
    protected  String foodName;
    private final FoodType foodType;
    private static int generateFoodId = 0;

    public Food(FoodType foodType) {
        this.foodId = generateFoodId++;
        this.foodType = foodType;
    }

    public double getFoodTypePrice(){
        return this.foodType.priceProduct;
    }
    public String getName(){
        return foodName;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", foodType=" + foodType +
                '}';
    }

    @Override
    public int hashCode() {
        return foodId;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Food)
            return this.foodId == ((Food) obj).foodId;

        return false;
    }

    @Override
    public int compareTo(Food o) {
        return (int)(this.getFoodTypePrice() - o.getFoodTypePrice());
    }
}
