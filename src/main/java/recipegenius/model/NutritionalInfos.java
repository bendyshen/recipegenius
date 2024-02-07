package recipegenius.model;

public class NutritionalInfos {
    private float totalCalories;
    private float totalProtein;
    private float totalCarbs;
    private float totalFat;

    public NutritionalInfos(float totalCalories, float totalProtein, float totalCarbs, float totalFat) {
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public float getTotalCalories() {
        return totalCalories;
    }

    public float getTotalProtein() {
        return totalProtein;
    }

    public float getTotalCarbs() {
        return totalCarbs;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public void setTotalCalories(float totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalProtein(float totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void setTotalCarbs(float totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }
}
