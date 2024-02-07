package recipegenius.servlet;

import recipegenius.dal.RecipesDao;
import recipegenius.model.Recipes;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/searchresult")
public class SearchResult extends HttpServlet {
    protected RecipesDao recipesDao;

    @Override
    public void init() throws ServletException {
        recipesDao = RecipesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
        	try {
        		List<Recipes> recipesList = recipesDao.getAllRecipes();
        		req.setAttribute("recipesList", recipesList);
        		req.setAttribute("name", name);
        	} catch (SQLException e) {
                req.setAttribute("messages", "Failed to retrieve recipes.");
                e.printStackTrace();
            }
        } else {

	        try {
	            List<Recipes> recipesList = recipesDao.getRecipesByName("%" + name + "%");
	            req.setAttribute("recipesList", recipesList);
	            req.setAttribute("name", name);
	        } catch (SQLException e) {
	            req.setAttribute("messages", "Failed to retrieve recipes.");
	            e.printStackTrace();
	        }
        }

        req.getRequestDispatcher("/SearchResult.jsp").forward(req, resp);
    }
}
