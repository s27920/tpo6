package pjatk.tpo6.tpo6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.Context;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
           message = "huh";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
    public void destroy() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining());
        requestJson requestJson = new Gson().fromJson(json, HelloServlet.requestJson.class);
        String sqlRequest = buildSQLRequest(requestJson);
        System.out.println(sqlRequest);
        try(Connection connection = DriverManager.getConnection(Setup.DBString);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest);){
            List<Restaurant> restaurants = new ArrayList<>();
            while (resultSet.next()){
                restaurants.add(new Restaurant(resultSet));
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(restaurants));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("there you broke it. Happy?");
        }
    }

    private String buildSQLRequest(requestJson json){
        StringBuilder query = new StringBuilder("SELECT * FROM Restaurant");
        if (json.features.length > 0){
            query.append(" INNER JOIN Restaurant_Feature ON Restaurant.restaurant_id = Restaurant_Feature.Restaurant_restaurant_id " +
                        "INNER JOIN Feature ON Restaurant_Feature.Feature_feature_id = Feature.feature_id ");
        }
        if (json.cuisines.length > 0){
            query.append(" INNER JOIN Restaurant_Cuisine ON Restaurant.restaurant_id = Restaurant_Cuisine.Restaurant_restaurant_id " + "INNER JOIN Cuisine ON Restaurant_Cuisine.Table_3_cuisine_id = Cuisine.cuisine_id ");
        }
        chainSQLWhere(json.features, json.cuisines.length,query," Feature.feature_text", "AND", false);
        chainSQLWhere(json.cuisines, json.prices.length,query," Cuisine.cuisine_name ", "OR", false);
        chainSQLWhere(json.prices, 0, query ," Restaurant.price_range ", "OR", true);
        return query.toString();
    }
    private StringBuilder chainSQLWhere(String[] arr, int nextLen, StringBuilder query, String whereSubject, String operator, boolean terminal){
        //I know this looks bad but I actually cooked here
        int length = arr.length;
        if (length > 0){
            if (!query.toString().contains("WHERE")){
                query.append(" WHERE ");
            }
            if (operator.equals("OR") && length > 1){
                query.append("(");
            }
            for (int i = 0; i < length; i++) {
                query.append(whereSubject).append(" = '").append(arr[i]).append("' ");
                if (i < length-1){
                    query.append(operator);
                }
            }
            if (operator.equals("OR")  && length > 1){
                query.append(")");
            }
            if (!terminal && nextLen != 0){
                query.append(" AND ");
            }
        }
        return query;
    }
    private StringBuilder subQueries(String[] arr){
        for (int i = 0; i < arr.length; i++) {

        }
    }

    class requestJson{
        String search;
        String[] features;
        String[] cuisines;
        String[] prices;
    }

    class returnJson{
        String name;
        String address;
        String website;
        String zip_code;
        double rating;
        String price_range;
        double lat;
        double lon;
    }

    //DTO
    class Restaurant {
        private int restaurantId;
        private String name;
        private String address;
        private String website;
        private String zipCode;
        private float rating;
        private String priceRange;
        private double lat;
        private double lon;

        public Restaurant(ResultSet resultSet) throws SQLException {
            this.restaurantId = resultSet.getInt("restaurant_id");
            this.name = resultSet.getString("name");
            this.address = resultSet.getString("address");
            this.website = resultSet.getString("website");
            this.zipCode = resultSet.getString("zip_code");
            this.rating = resultSet.getFloat("rating");
            this.priceRange = resultSet.getString("price_range");
            this.lat = resultSet.getDouble("lat");
            this.lon = resultSet.getDouble("lon");
        }
    }
}