package recipegenius.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import recipegenius.model.*;

public class IngredientsDao {
    protected ConnectionManager connectionManager;

	private static IngredientsDao instance = null;
    protected IngredientsDao() {
        connectionManager = new ConnectionManager();
    }
	public static IngredientsDao getInstance() {
		if(instance == null) {
			instance = new IngredientsDao();
		}
		return instance;
	}

    public Ingredients createIngredient(Ingredients ingredient) {
        String INSERT_QUERY = "INSERT INTO Ingredients(Description, Kilocalories, Protein, Carbohydrate, Fat) VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;

        try {
            connection = connectionManager.getConnection(); 
            insertStmt = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);

            insertStmt.setString(1, ingredient.getDescription());
            insertStmt.setFloat(2, ingredient.getKilocalories());
            insertStmt.setFloat(3, ingredient.getProtein());
            insertStmt.setFloat(4, ingredient.getCarbohydrate());
            insertStmt.setFloat(5, ingredient.getFat());

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating ingredient failed, no rows affected.");
            }

           
            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                ingredient.setIngredientId(resultKey.getInt(1));
            } else {
                throw new SQLException("Creating ingredient failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
          
        } finally {
            
            if (resultKey != null) try { resultKey.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (insertStmt != null) try { insertStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }
        return ingredient;
    }

    public Ingredients getIngredientByIngredientId(int ingredientId) {
        String SELECT_QUERY = "SELECT IngredientId, Description, Kilocalories, Protein, Carbohydrate, Fat FROM Ingredients WHERE IngredientId = ?;";
        Ingredients ingredient = null;
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection(); 
            selectStmt = connection.prepareStatement(SELECT_QUERY);
            selectStmt.setInt(1, ingredientId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int retrievedId = results.getInt("IngredientId");
                String description = results.getString("Description");
                float kilocalories = results.getFloat("Kilocalories");
                float protein = results.getFloat("Protein");
                float carbohydrate = results.getFloat("Carbohydrate");
                float fat = results.getFloat("Fat");

                ingredient = new Ingredients(retrievedId, description, kilocalories, protein, carbohydrate, fat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        } finally {
            
            if (results != null) try { results.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }
        return ingredient;
    }


}

