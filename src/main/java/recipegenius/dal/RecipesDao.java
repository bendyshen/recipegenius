package recipegenius.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import recipegenius.model.*;

import java.util.ArrayList;
import java.util.List;

public class RecipesDao {
    protected ConnectionManager connectionManager;

	private static RecipesDao instance = null;
    protected RecipesDao() {
        connectionManager = new ConnectionManager();
    }
	public static RecipesDao getInstance() {
		if(instance == null) {
			instance = new RecipesDao();
		}
		return instance;
	}

    public Recipes createRecipe(Recipes recipe) {
        String insertRecipe = "INSERT INTO Recipes(Name, PrepTime, Method) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;

        try {
        
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecipe, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, recipe.getName());
            insertStmt.setInt(2, recipe.getPrepTime());
            insertStmt.setString(3, recipe.getMethod());

            int affectedRows = insertStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating recipe failed, no rows affected.");
            }

            resultKey = insertStmt.getGeneratedKeys();

            if (resultKey.next()) {
                recipe.setRecipeId(resultKey.getInt(1));
            } else {
                throw new SQLException("Creating recipe failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultKey != null) {
                try {
                    resultKey.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (insertStmt != null) {
                try {
                    insertStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connectionManager.closeConnection(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return recipe;
    }

    public Recipes getRecipeByRecipeId(int recipeId) throws SQLException {
        String selectRecipe = "SELECT RecipeId, Name, PrepTime, Method FROM Recipes WHERE RecipeId = ?;";
        Recipes recipe = null;
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecipe);
            selectStmt.setInt(1, recipeId);
            results = selectStmt.executeQuery();

            if (results.next()) {
                int resultRecipeId = results.getInt("RecipeId");
                String name = results.getString("Name");
                int prepTime = results.getInt("PrepTime");
                String method = results.getString("Method");

                recipe = new Recipes(resultRecipeId, name, prepTime, method);
                return recipe;
            }
        } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}

        return null;
    }
    
    public List<Recipes> getRecipesByName(String name) throws SQLException{
        List<Recipes> recipesList = new ArrayList<>();
        String selectRecipes = "SELECT * From Recipes Where Name LIKE ?";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;   
        try { 
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecipes);
            selectStmt.setString(1, name);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int recipeId = results.getInt("RecipeId");
                String recipeName = results.getString("Name");
                int prepTime = results.getInt("PrepTime");
                String method = results.getString("Method");
                Recipes recipe = new Recipes(recipeId, recipeName, prepTime, method);
                recipesList.add(recipe);
            }
        }catch (SQLException e) {
            e.printStackTrace();
 
        } finally {

            if (results != null) try { results.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connectionManager.closeConnection(connection); } catch (SQLException e) { e.printStackTrace(); }
        }

        return recipesList;
    }
    
    public List<Recipes> getAllRecipes() throws SQLException {
  	  List<Recipes> recipes = new ArrayList<>();
  	  String selectRecipes = "SELECT * FROM Recipes;";
  	  Connection connection = null;
  	  PreparedStatement selectStmt = null;
  	  ResultSet results = null;
  	  try {
  	    connection = connectionManager.getConnection();
  	    selectStmt = connection.prepareStatement(selectRecipes);
  	    results = selectStmt.executeQuery();
  	    while (results.next()) {
  	      int id = results.getInt("RecipeId");
  	      String name = results.getString("Name");
  	      int prepTime = results.getInt("PrepTime");
  	      String method = results.getString("Method");
  	      Recipes recipe = new Recipes(id, name, prepTime, method);
  	      recipes.add(recipe);
  	    }
  	  } catch (SQLException e) {
  	    e.printStackTrace();
  	    throw e;
  	  } finally {
  	    if (results != null) {
  	      results.close();
  	    }
  	    if (selectStmt != null) {
  	      selectStmt.close();
  	    }
  	    if (connection != null) {
  	      connection.close();
  	    }
  	  }
  	  return recipes;
  	}
}
