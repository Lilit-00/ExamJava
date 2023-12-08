public enum FoodType {

    DRINK("Drinks",10),
    SNACK("Snack", 12),
    MEAL("Meal", 8);

     String typeDescription;
     double priceProduct;

    FoodType(String typeDescription, double priceProduct) {
      this.typeDescription = typeDescription;
      this.priceProduct = priceProduct;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

}
