package recipegenius.model;

public class Recipes {
    protected int recipeId;
    protected String name;
    protected int prepTime;
    protected String method;

    public Recipes(String name, int prepTime, String method) {
        this.name = name;
        this.prepTime = prepTime;
        this.method = method;
    }


    public Recipes(int recipeId, String name, int prepTime, String method) {
        this.recipeId = recipeId;
        this.name = name;
        this.prepTime = prepTime;
        this.method = method;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public String getMethod() {
        return method;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
