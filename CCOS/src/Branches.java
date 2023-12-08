import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Branches implements Serializable {
   private List<Food>  restaurantFood =new ArrayList<>();
   public Map<Food,Integer> countOfFood = new TreeMap<>();
   public List<Order> orderList = new ArrayList<>();
   String pathRestuarantFood = "HistoryOfOrder.bin";
   String pathOrder = "RestaurantFood.bin";


    public void saveAllData(){
        saveOrder();
        saveRestuarantFood();
    }
    private void saveOrder(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathOrder))) {
            oos.writeObject(orderList);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error saving food data: " + e.getMessage());
        }
    }

    private  void saveRestuarantFood(){
        try (ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(pathRestuarantFood))) {
            oos.writeObject(restaurantFood);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error saving Food data: " + e.getMessage());
        }
    }

    public void loadRestuarantFood(){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathRestuarantFood))) {

            List<Food> saved = (List<Food>) objectInputStream.readObject();
            restaurantFood.addAll(saved);
            System.out.println("Food data loaded successfully from " + pathRestuarantFood);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No saved food data found.");
        }
    }

    public void addMeal(){

            Meal meal = new Meal();//add describtion
            restaurantFood.add(meal);
            System.out.println("Meal added in restaurant ");

    }
    public  void addDrink(){
        Drink drink = new Drink();//add description
        restaurantFood.add(drink);
        System.out.println("Drink added in restaurant ");

    }
    public  void addSnack(){
        Snack snack = new Snack();//add description
        restaurantFood.add(snack);
        System.out.println("Snack added in restaurant ");

     //   printMenuForWaiters();

    }
    //creat order
    public void orderFood(Scanner sc){
        int globalDrinkOrderedCount = 0 ,globalMailOrderedCount = 0 ,globalSnackOrderedCount = 0;
        System.out.println("please enter right order..");
        Order order = new Order();
        int count = 0;
        String operator;

       do{
           System.out.println("Enter Food name: drink , meal, snack");
           String foodName = sc.next();

           switch (foodName){
               case "drink"->{
                   //to check whether there isa drink or not
                   System.out.println("Enter desired amount of drink :");
                   count = sc.nextInt();
                   globalDrinkOrderedCount += count;
                   checkAmountFood(count,foodName,order);//hanel qanaky
                   deleteOrderedFood(globalDrinkOrderedCount,"drink");

               }
               case "meal"->{
                   //to check whether there isa meal or not
                   System.out.println("Enter desired amount of drink :");
                   count = sc.nextInt();
                   globalMailOrderedCount += count;
                   checkAmountFood(count,foodName,order);
                   deleteOrderedFood(globalMailOrderedCount,"meal");

               }
               case "snack"->{
                   //to check whether there isa snack or not
                   System.out.println("Enter desired amount of drink :");
                   count = sc.nextInt();
                   globalSnackOrderedCount += count;
                   checkAmountFood(count,foodName,order);
                   deleteOrderedFood(globalSnackOrderedCount,"snack");

               }

           }
           System.out.println("press 0 for exiting...");
           operator = sc.next();

       }while (!operator.equals("0"));

       if (order.getTotalCost()>0){
           order.generateBill();

           //delete celected foods
           orderList.add(order);
       }


    }
    private void deleteOrderedFood(int globalCount, String foodName){
        Map<Food,Integer> currentMap = new HashMap<>();

        //delet of list
        for (int i = 0; i<restaurantFood.size(); i++){
            if (restaurantFood.get(i).foodName.equals(foodName) && globalCount !=0){
                restaurantFood.remove(i);
                globalCount--;
            }
        }

        //delet of map
        for (Map.Entry<Food,Integer> entry: countOfFood.entrySet()){
            if (entry.getKey().foodName.equals(foodName)  && entry.getValue() > globalCount){
                currentMap.put(entry.getKey(),entry.getValue() - globalCount);
            }else {
                currentMap.put(entry.getKey(),entry.getValue());
            }

        }
        countOfFood = currentMap;


    }
    private boolean checkAmountFood(int count,String foodName,Order order){
        if (countOfFood.isEmpty()){
            System.out.println("food is empty ,pleas add food");
        }
      for (Map.Entry<Food,Integer> entry: countOfFood.entrySet()){
          if(entry.getKey().getName().equals(foodName) && entry.getValue()>= count){
              order.addOrderFood(entry.getKey(),count);
              return true;
          }
      }
        System.out.println("There is no food as much as you want ...");
        return false;

    }
    public boolean thereIsNoFood(){
        return restaurantFood.isEmpty();
    }
    public void printCountOfFoodMap(){
        if (thereIsNoFood()) {
            System.out.println("RESTAURANT FOOD IS EMPTY ");
            return;
        }
        toFillCountOfFood();
        System.out.println("print Count of Food");
        for (Map.Entry<Food,Integer> entry: countOfFood.entrySet()){
            System.out.println("food name - "+entry.getKey().foodName+" count-"+entry.getValue());
        }
    }

    public void printMenuForWaiters(){

        if (restaurantFood.size() > 1)
           sortList();

        System.out.println(restaurantFood);//verjum hanel
        int count = 1;
        if (restaurantFood.size() == 1){
            System.out.println(restaurantFood.get(0).foodName + " count = " + count);
            return;
        }

       for (int i = 0; i< restaurantFood.size()-1; i++){

           if (!restaurantFood.get(i).getName().equals(restaurantFood.get(i+1).getName())) {
               System.out.println(restaurantFood.get(i).foodName + " count = " + count);
               System.out.println(restaurantFood.get(i).getName() +" price = " +
                       restaurantFood.get(i).getFoodTypePrice());
               count = 1;
           }
           if (restaurantFood.get(i).getName().equals(restaurantFood.get(i+1).getName())) {
              count++;
           }
       }
      /* if (!restaurantFood.get(restaurantFood.size()-1).getName().equals(restaurantFood.get(restaurantFood.size()-2).getName())){
           System.out.println(restaurantFood.get(restaurantFood.size()-1).foodName + " count = " + 1);
       }*/


    }
    //remove  all meal in restuarant
    public void removeAllMeal(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("meal"))
                restaurantFood.remove(restaurantFood.get(i));
        }
        toFillCountOfFood();
        System.out.println("Sussessfully deleted a full meal ...");
    }

    public void removeAllDrink(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("drink"))
                restaurantFood.remove(restaurantFood.get(i));
        }
        toFillCountOfFood();
        System.out.println("Sussessfully deleted a full drink ...");
    }

    public void removeAllSnack(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("snack"))
                restaurantFood.remove(restaurantFood.get(i));
        }
        toFillCountOfFood();
        System.out.println("Sussessfully deleted a full snack ...");
    }

    //remove  one  snack  restuarant
    public void removeSnack(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("snack")){
                restaurantFood.remove(restaurantFood.get(i));
                break;
            }
        }
        toFillCountOfFood();
        System.out.println("A snack has been successfully deleted ");

    }

    public void removeMeal(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("meal")){
                restaurantFood.remove(restaurantFood.get(i));
                break;
            }
        }
        toFillCountOfFood();
        System.out.println("A meal has been successfully deleted ");
    }

    public void removeDrink(String foodName){
        sortList();
        for (int i = 0;i<restaurantFood.size();i++){
            if (restaurantFood.get(i).foodName.equals("drink")) {
                restaurantFood.remove(restaurantFood.get(i));
                break;
            }
        }
        toFillCountOfFood();
        System.out.println("A drink has been successfully deleted ");
    }

    private void sortList(){
        if ( restaurantFood != null && restaurantFood.size() == 0){
            System.out.println("RESTAURANT FOOD IS EMPTY ...");
            System.out.println("Please add food \n");
            return;
        }
        restaurantFood.sort((e1, e2) -> (int) (e1.getFoodTypePrice() - e2.getFoodTypePrice()));
    }

    private void toFillCountOfFood(){
        sortList();
        int count = 0;
        boolean flag = false;
        try {
            for (int i = 0; i < restaurantFood.size() - 1; i++) {
                if (restaurantFood.get(i).getFoodTypePrice() != restaurantFood.get(i + 1).getFoodTypePrice()) {
                    countOfFood.put(restaurantFood.get(i), count + 1);
                    count = 0;
                }
                if (restaurantFood.get(i).getFoodTypePrice() == restaurantFood.get(i + 1).getFoodTypePrice())
                    count++;

            }
            countOfFood.put(restaurantFood.get(restaurantFood.size() - 1), count + 1);
        }catch (RuntimeException e){
            System.out.println("Eception situation " + e.getMessage());
        }
    }


}
