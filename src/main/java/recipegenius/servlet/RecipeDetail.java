package recipegenius.servlet;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import recipegenius.dal.*;
import recipegenius.model.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.ArrayList;
@WebServlet("/recipedetail")
public class RecipeDetail extends HttpServlet {

    protected RecipesDao recipesDao;
    protected CookPairsDao cookPairsDao;
    protected IngredientsDao ingredientsDao;

    @Override
    public void init() throws ServletException {
        recipesDao = RecipesDao.getInstance();
        cookPairsDao = CookPairsDao.getInstance();
        ingredientsDao = IngredientsDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

  
        String recipeIdString = req.getParameter("recipeId");
        if (recipeIdString == null || recipeIdString.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Recipe ID.");
        } else {
            try {
                int recipeId = Integer.parseInt(recipeIdString);
                Recipes recipe = recipesDao.getRecipeByRecipeId(recipeId);
                if(recipe == null) {
                    messages.put("success", "Recipe does not exist.");
                } else {
                    NutritionalInfos nutritionalInfo = cookPairsDao.calculateTotalNutritionByRecipeId(recipeId);
                    List<Cookpairs> cookPairs = cookPairsDao.getCookpairsByRecipeId(recipeId);
                    List<Entry<Integer, String>> amountAndIngredientList = new ArrayList<>();

                    for (Cookpairs cookpair : cookPairs) {
                        Ingredients ingredient = ingredientsDao.getIngredientByIngredientId(cookpair.getIngredientId());
                        Entry<Integer, String> amountAndIngredient = new SimpleEntry<>(cookpair.getAmount(), ingredient.getDescription());
                        amountAndIngredientList.add(amountAndIngredient);
                    }
                    req.setAttribute("recipe", recipe);
                    req.setAttribute("nutritionalInfo", nutritionalInfo);
                    req.setAttribute("amountAndIngredientList", amountAndIngredientList);
                    messages.put("success", "Displaying details for recipe " + recipe.getName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            } catch (NumberFormatException e) {
                messages.put("success", "Invalid Recipe ID format.");
            }
        }
        
        req.getRequestDispatcher("/RecipeDetail.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}