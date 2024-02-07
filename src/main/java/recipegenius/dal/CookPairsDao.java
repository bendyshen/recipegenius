package recipegenius.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import recipegenius.model.*;
import java.util.ArrayList;
import java.util.List;
 

public class CookPairsDao {
    protected ConnectionManager connectionManager;
    
	private static CookPairsDao instance = null;
    protected CookPairsDao() {
        connectionManager = new ConnectionManager();
    }
	public static CookPairsDao getInstance() {
		if(instance == null) {
			instance = new CookPairsDao();
		}
		return instance;
	}

    public Cookpairs getCookpairByCookpairId(int cookpairId) {
        String SELECT_QUERY = "SELECT CookpairId, RecipeId, IngredientId, Amount FROM Cookpairs WHERE CookpairId = ?;";
        Cookpairs cookpair = null;
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection(); // Replace with your connection method
            selectStmt = connection.prepareStatement(SELECT_QUERY);
            selectStmt.setInt(1, cookpairId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int retrievedCookpairId = results.getInt("CookpairId");
                int recipeId = results.getInt("RecipeId");
                int ingredientId = results.getInt("IngredientId");
                int amount = results.getInt("Amount");
                cookpair = new Cookpairs(retrievedCookpairId, recipeId, ingredientId, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        } finally {
            if (results != null) try { results.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }
        return cookpair;
    }

    public Cookpairs createCookpair(Cookpairs cookpair) {
        String INSERT_QUERY = "INSERT INTO Cookpairs(RecipeId, IngredientId, Amount) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;

        try {
            connection = connectionManager.getConnection(); // Replace with your connection method
            insertStmt = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);

            insertStmt.setInt(1, cookpair.getRecipeId());
            insertStmt.setInt(2, cookpair.getIngredientId());
            insertStmt.setInt(3, cookpair.getAmount());

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating cookpair failed, no rows affected.");
            }

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                cookpair.setCookpairId(resultKey.getInt(1));
            } else {
                throw new SQLException("Creating cookpair failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        } finally {
          
            if (resultKey != null) try { resultKey.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (insertStmt != null) try { insertStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }
        return cookpair;
    }
    public List<Cookpairs> getCookpairsByRecipeId(int recipeId) {
        List<Cookpairs> cookpairsList = new ArrayList<>();
        String SELECT_QUERY = "SELECT CookpairId, RecipeId, IngredientId, Amount FROM Cookpairs WHERE RecipeId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_QUERY);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int cookpairId = results.getInt("CookpairId");
                int retrievedRecipeId = results.getInt("RecipeId");
                int ingredientId = results.getInt("IngredientId");
                int amount = results.getInt("Amount");

                Cookpairs cookpair = new Cookpairs(cookpairId, retrievedRecipeId, ingredientId, amount);
                cookpairsList.add(cookpair);
            }
        } catch (SQLException e) {
            e.printStackTrace();
 
        } finally {

            if (results != null) try { results.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }

        return cookpairsList;
    }

    public NutritionalInfos calculateTotalNutritionByRecipeId(int recipeId) {
        List<Cookpairs> cookpairs = getCookpairsByRecipeId(recipeId);
        NutritionalInfos nutritionalInfo = new NutritionalInfos(0, 0, 0, 0);
        IngredientsDao ingredientsDao = new IngredientsDao();

        for (Cookpairs cookpair : cookpairs) {
            Ingredients ingredient = ingredientsDao.getIngredientByIngredientId(cookpair.getIngredientId());
            int amountFactor = cookpair.getAmount();
            float totalCalories = ingredient.getKilocalories() * amountFactor;
            float totalProtein = ingredient.getProtein() * amountFactor;
            float totalCarbs = ingredient.getCarbohydrate() * amountFactor;
            float totalFat = ingredient.getFat() * amountFactor;
            nutritionalInfo.setTotalCalories(nutritionalInfo.getTotalCalories() + totalCalories);
            nutritionalInfo.setTotalProtein(nutritionalInfo.getTotalProtein() + totalProtein);
            nutritionalInfo.setTotalCarbs(nutritionalInfo.getTotalCarbs() + totalCarbs);
            nutritionalInfo.setTotalFat(nutritionalInfo.getTotalFat() + totalFat);
        }

        return nutritionalInfo;
    }
    
    public List<Ingredients> getIngredientsByRecipeId(int recipeId) {
        List<Ingredients> ingredientsList = new ArrayList<>();
        String SELECT_QUERY = 
            "SELECT i.IngredientId, i.Description, i.Kilocalories, i.Protein, i.Carbohydrate, i.Fat " +
            "FROM Ingredients i INNER JOIN Cookpairs c ON i.IngredientId = c.IngredientId " +
            "WHERE c.RecipeId = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_QUERY);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            while (results.next()) {
                int ingredientId = results.getInt("IngredientId");
                String description = results.getString("Description");
                float kilocalories = results.getFloat("Kilocalories");
                float protein = results.getFloat("Protein");
                float carbohydrate = results.getFloat("Carbohydrate");
                float fat = results.getFloat("Fat");

                Ingredients ingredient = new Ingredients(ingredientId, description, kilocalories, protein, carbohydrate, fat);
                ingredientsList.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
  
        } finally {
   
            if (results != null) try { results.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }

        return ingredientsList;
    }
    

}


