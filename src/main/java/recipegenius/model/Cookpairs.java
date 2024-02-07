package recipegenius.model;
public class Cookpairs {
    protected int cookpairId;
    protected int recipeId;
    protected int ingredientId;
    protected int amount;

    public Cookpairs(int recipeId, int ingredientId, int amount) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
    }

    public Cookpairs(int cookpairId, int recipeId, int ingredientId, int amount) {
        this.cookpairId = cookpairId;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
    }

    public int getCookpairId() {
        return cookpairId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public int getAmount() {
        return amount;
    }

    public void setCookpairId(int cookpairId) {
        this.cookpairId = cookpairId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

