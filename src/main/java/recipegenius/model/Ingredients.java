package recipegenius.model;

public class Ingredients {
    protected int ingredientId;
    protected String description;
    protected float kilocalories;
    protected float protein;
    protected float carbohydrate;
    protected float fat;

    public Ingredients(String description, float kilocalories, float protein, float carbohydrate, float fat) {
        this.description = description;
        this.kilocalories = kilocalories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }

    public Ingredients(int ingredientId, String description, float kilocalories, float protein, float carbohydrate, float fat) {
        this.ingredientId = ingredientId;
        this.description = description;
        this.kilocalories = kilocalories;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
    }
    public int getIngredientId() {
        return ingredientId;
    }

    public String getDescription() {
        return description;
    }

    public float getKilocalories() {
        return kilocalories;
    }

    public float getProtein() {
        return protein;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public float getFat() {
        return fat;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKilocalories(float kilocalories) {
        this.kilocalories = kilocalories;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }
}
