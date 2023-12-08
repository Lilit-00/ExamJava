import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
    public Map<Food,Integer> mapOrder = new HashMap<>();
    double price;
    public boolean haveOrder = false;

    public void addOrderFood(Food food, Integer count){
        mapOrder.put(food,count);
    }
    public double getTotalCost(){

        if (mapOrder.isEmpty()){
            System.out.println("There is No Order,put order");
            return 0;
        }
        for (Map.Entry<Food,Integer> entry:mapOrder.entrySet()){
            price+= entry.getKey().getFoodTypePrice() * entry.getValue();
        }
        if (price > 0)
            haveOrder= true;

        return price;
    }

    public void generateBill() {
        double foodCharge = getTotalCost();
        double tax = foodCharge * 0.20; // 20% tax
        double serviceCharge = foodCharge * 0.10; // 10% service Charge
        double tip = foodCharge * 0.10;//10% tip
        double totalAmount = foodCharge + tax + serviceCharge +tip;



        System.out.println("----- Bill for Custumer -----");
        for (Map.Entry<Food,Integer> entry:mapOrder.entrySet()){
            System.out.println("Food Name : "+ entry.getKey().foodName+
                            " Food price :"+entry.getKey().getFoodTypePrice()+" Food count :"+ entry.getValue());
        }


        System.out.println("FoodCharge: $" + foodCharge);
        System.out.println("Tax (20%): $" + tax);
        System.out.println("Service Charge (10%): $" + serviceCharge);
        System.out.println("Tip (10%): $"+tip);
        System.out.println("Total Amount: $" + totalAmount);

    }
   // public void printBill(){}
}
