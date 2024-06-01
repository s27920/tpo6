package pjatk.tpo6.tpo6;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.*;


public class Setup implements ServletContextListener {
    public static  String DBString = "jdbc:derby:sometDB;create=true";
            String[] drops = {
            "ALTER TABLE Restaurant_Cuisine DROP CONSTRAINT Restaurant_Cuisine_Restaurant",
            "ALTER TABLE Restaurant_Cuisine DROP CONSTRAINT Restaurant_Cuisine_Table_3",
            "ALTER TABLE Restaurant_Feature DROP CONSTRAINT Restaurant_Feature_Feature",
            "ALTER TABLE Restaurant_Feature DROP CONSTRAINT Restaurant_Feature_Restaurant",
            "DROP TABLE Cuisine",
            "DROP TABLE Feature",
            "DROP TABLE Restaurant",
            "DROP TABLE Restaurant_Cuisine",
            "DROP TABLE Restaurant_Feature"
    };

    String[] creates = {
            "CREATE TABLE Cuisine (" +
                    "    cuisine_id INT NOT NULL GENERATED ALWAYS AS IDENTITY," +
                    "    cuisine_name VARCHAR(100) NOT NULL," +
                    "    description VARCHAR(250) NOT NULL," +
                    "    CONSTRAINT Cuisine_pk PRIMARY KEY (cuisine_id)" +
                    ")",

            "CREATE TABLE Feature (" +
                    "    feature_id INT NOT NULL GENERATED ALWAYS AS IDENTITY," +
                    "    feature_text VARCHAR(100) NOT NULL," +
                    "    CONSTRAINT Feature_pk PRIMARY KEY (feature_id)" +
                    ")",

            "CREATE TABLE Restaurant (" +
                    "    restaurant_id INT NOT NULL GENERATED ALWAYS AS IDENTITY," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    address VARCHAR(100) NOT NULL," +
                    "    website VARCHAR(100) NOT NULL," +
                    "    zip_code VARCHAR(100) NOT NULL," +
                    "    rating DECIMAL(2,1) NOT NULL," +
                    "    price_range VARCHAR(10) NOT NULL," +
                    "    lat DECIMAL(9,6) NOT NULL," +
                    "    lon DECIMAL(9,6) NOT NULL," +
                    "    CONSTRAINT Restaurant_pk PRIMARY KEY (restaurant_id)" +
                    ")",

            "CREATE TABLE Restaurant_Cuisine (" +
                    "    Table_3_cuisine_id INT NOT NULL," +
                    "    Restaurant_restaurant_id INT NOT NULL," +
                    "    CONSTRAINT Restaurant_Cuisine_pk PRIMARY KEY (Restaurant_restaurant_id, Table_3_cuisine_id)" +
                    ")",

            "CREATE TABLE Restaurant_Feature (" +
                    "    Feature_feature_id INT NOT NULL," +
                    "    Restaurant_restaurant_id INT NOT NULL," +
                    "    CONSTRAINT Restaurant_Feature_pk PRIMARY KEY (Restaurant_restaurant_id, Feature_feature_id)" +
                    ")",

            "ALTER TABLE Restaurant_Cuisine ADD CONSTRAINT Restaurant_Cuisine_Restaurant" +
                    "    FOREIGN KEY (Restaurant_restaurant_id)" +
                    "    REFERENCES Restaurant (restaurant_id)",

            "ALTER TABLE Restaurant_Cuisine ADD CONSTRAINT Restaurant_Cuisine_Table_3" +
                    "    FOREIGN KEY (Table_3_cuisine_id)" +
                    "    REFERENCES Cuisine (cuisine_id)",

            "ALTER TABLE Restaurant_Feature ADD CONSTRAINT Restaurant_Feature_Feature" +
                    "    FOREIGN KEY (Feature_feature_id)" +
                    "    REFERENCES Feature (feature_id)",

            "ALTER TABLE Restaurant_Feature ADD CONSTRAINT Restaurant_Feature_Restaurant" +
                    "    FOREIGN KEY (Restaurant_restaurant_id)" +
                    "    REFERENCES Restaurant (restaurant_id)"
    };

    // Inserts
    String[] inserts = {
            "INSERT INTO Cuisine (cuisine_name, description) VALUES ('Italian', 'Traditional Italian cuisine with a variety of pasta and pizzas')",
            "INSERT INTO Cuisine (cuisine_name, description) VALUES ('Chinese', 'Authentic Chinese dishes with a mix of flavors and spices')",
            "INSERT INTO Cuisine (cuisine_name, description) VALUES ('Mexican', 'Spicy and flavorful Mexican food including tacos and burritos')",
            "INSERT INTO Cuisine (cuisine_name, description) VALUES ('Korean', 'Delicious Korean dishes including kimchi and bibimbap')",
            "INSERT INTO Cuisine (cuisine_name, description) VALUES ('Japanese', 'Exquisite Japanese cuisine with sushi and ramen')",

            "INSERT INTO Feature (feature_text) VALUES ('Outdoor Seating')",
            "INSERT INTO Feature (feature_text) VALUES ('Free Wi-Fi')",
            "INSERT INTO Feature (feature_text) VALUES ('Pet Friendly')",
            "INSERT INTO Feature (feature_text) VALUES ('Wheelchair Accessible')",

            "INSERT INTO Restaurant (name, address, website, zip_code, rating, price_range, lat, lon) VALUES ('Pasta Palace', '1234 Elm St', 'www.pastapalace.com', '90210', 4.5, '$$', 34.05223, -118.24368)",
            "INSERT INTO Restaurant (name, address, website, zip_code, rating, price_range, lat, lon) VALUES ('Dragon Delight', '5678 Oak St', 'www.dragondelight.com', '90210', 4.0, '$$', 34.05223, -118.24368)",
            "INSERT INTO Restaurant (name, address, website, zip_code, rating, price_range, lat, lon) VALUES ('Taco Town', '9101 Pine St', 'www.tacotown.com', '90210', 4.2, '$', 34.05223, -118.24368)",
            "INSERT INTO Restaurant (name, address, website, zip_code, rating, price_range, lat, lon) VALUES ('Korea Kitchen', '1212 Maple St', 'www.koreakitchen.com', '90210', 4.6, '$$', 34.05223, -118.24368)",
            "INSERT INTO Restaurant (name, address, website, zip_code, rating, price_range, lat, lon) VALUES ('Sushi Spot', '1313 Cedar St', 'www.sushispot.com', '90210', 4.8, '$$', 34.05223, -118.24368)",

            "INSERT INTO Restaurant_Cuisine (Restaurant_restaurant_id, Table_3_cuisine_id) VALUES (1, 1)",
            "INSERT INTO Restaurant_Cuisine (Restaurant_restaurant_id, Table_3_cuisine_id) VALUES (2, 2)",
            "INSERT INTO Restaurant_Cuisine (Restaurant_restaurant_id, Table_3_cuisine_id) VALUES (3, 3)",
            "INSERT INTO Restaurant_Cuisine (Restaurant_restaurant_id, Table_3_cuisine_id) VALUES (4, 4)",
            "INSERT INTO Restaurant_Cuisine (Restaurant_restaurant_id, Table_3_cuisine_id) VALUES (5, 5)",

            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (1, 1)",
            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (1, 2)",
            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (2, 2)",
            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (3, 3)",
            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (4, 4)",
            "INSERT INTO Restaurant_Feature (Restaurant_restaurant_id, Feature_feature_id) VALUES (5, 4)"
    };



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try(Connection connection = DriverManager.getConnection(DBString);
            Statement statement = connection.createStatement()) {
            sce.getServletContext().setAttribute("DBcon", connection);
            DatabaseMetaData dbData = connection.getMetaData();
//            for (int i = 0; i < tables.length; i++) {
//                if (!exists(dbData, tables[i])){
//                    statement.executeUpdate(dbInit[i]);
//                }
//            }
//            testExecute(connection, creates);
//            testExecute(connection, inserts);
            ResultSet set = statement.executeQuery("SELECT * FROM Restaurant INNER JOIN Restaurant_Feature ON Restaurant.restaurant_id = Restaurant_Feature.Restaurant_restaurant_id INNER JOIN Feature ON Restaurant_Feature.Feature_feature_id = Feature.feature_id  INNER JOIN Restaurant_Cuisine ON Restaurant.restaurant_id = Restaurant_Cuisine.Restaurant_restaurant_id INNER JOIN Cuisine ON Restaurant_Cuisine.Table_3_cuisine_id = Cuisine.cuisine_id WHERE Feature.feature_text = 'Outdoor Seating' AND (Cuisine.cuisine_name = 'Korean' OR Cuisine.cuisine_name = 'Italian') AND Restaurant.price_range = '$$'");
            while(set.next()){
                for (int i = 1; i < set.getMetaData().getColumnCount(); i++) {
                    System.out.print(set.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void testExecute(Connection con, String[] arr) throws SQLException {
        Statement statement = con.createStatement();
        for (String query:arr){
            statement.executeUpdate(query);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = ((Connection) sce.getServletContext().getAttribute("DBcon"));
        if (connection != null){
            try {
//                testExecute(connection, drops);
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean exists(DatabaseMetaData dbData, String tableName) throws SQLException{
        try (ResultSet resultSet = dbData.getTables(null, null, tableName.toUpperCase(), null)){
            return resultSet.next();
        }
    }
}
