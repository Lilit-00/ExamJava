import java.util.Scanner;

public class MySystem {


    private final Branches baseOfProgram = new Branches();
    public void startApplication(){
        Scanner sc = new Scanner(System.in);
        String operator;
        System.out.println("Welcome to the CCOS System");
        do {
            printMenu();
            operator = sc.next();
            switch (operator) {
                case "1" -> addFood(sc);
                case "2" -> addOrder(sc);
                case "3" -> removeAllSetFood(sc);
                case "4" -> removeFood(sc);
                case "5" -> saveData();
                case "6" -> baseOfProgram.printCountOfFoodMap();
                case "7" -> loadFullData();
                case "0" -> {
                    System.out.println("Exit - Do you want to save data?  Y/N");
                    String button = sc.next().toLowerCase();
                    if(button.equals("y")){
                         saveData();
                    }
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (!operator.equals("0"));
    }

    private void addFood(Scanner sc){

        String output = """
                1 - Drink
                2 - Snack
                3 - Meal
                0 - Exit in this page ...
                """;
        System.out.println(output);
        boolean find = false;
        while (!find){
            System.out.println("Please, choose one of this:  1 , 2 , 3 , 0");
            switch (sc.next()){
                case "1" -> baseOfProgram.addDrink();
                case "2" -> baseOfProgram.addSnack();
                case "3" -> baseOfProgram.addMeal();
                case "0" -> {
                    find= true;
                    System.out.println("Exit in this page !!!");
                }
            }
        }
    }

    private void addOrder(Scanner sc){
        boolean flag = false;
        String str= """
                If you have finished ordering ,please press 0 
                Submit an order ,please press 1
                """;

        if (baseOfProgram.thereIsNoFood()){
            System.out.println("there is no food, please enter food ...");
            return;
        }
        while (!flag){
            System.out.println(str);
            switch (sc.next()){
                case "1"->{
                    System.out.println("You can choose your order !!!");
                    baseOfProgram.orderFood(sc);
                }
                case "0"->{
                    System.out.println("exit in this page !!!");
                    flag= true;
                }
            }
        }


    }
    private void removeAllSetFood(Scanner sc){
        System.out.println("Warning delete full set food ...");
        System.out.println("Pleas enter food name :drink meal snack");
        String foodName = sc.next();

        switch (foodName.toLowerCase()){
            case "drink"-> baseOfProgram.removeAllDrink(foodName);
            case "snack"-> baseOfProgram.removeAllSnack(foodName);
            case "meal"-> baseOfProgram.removeAllMeal(foodName);
            default -> System.out.println("Invalid choice.!");
        }

    }
    private void removeFood(Scanner sc){
        System.out.println("Pleas enter food name : drink meal snack");
        String foodName = sc.next();

        switch (foodName.toLowerCase()){
            case "drink"-> baseOfProgram.removeDrink(foodName);
            case "snack"-> baseOfProgram.removeSnack(foodName);
            case "meal"-> baseOfProgram.removeMeal(foodName);
            default -> System.out.println("Invalid choice.!");
        }
    }
    private void saveData(){
        try {
            baseOfProgram.saveAllData();
            System.out.println("All data saved successfully to file");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    private  void loadFullData(){
        baseOfProgram.loadRestuarantFood();
    }


    private void printMenu() {
        String message = """
                1: Add a Food
                2: Add a Order
                3: Remove All Food
                4: Remove Food
                5: Save System State to a File
                6: Print Menu info
                7: Load Old Data
                0: Exit
                """;
        System.out.println(message);
    }
}
