package pjatk.tpo6.tpo6;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.*;

public class Setup implements ServletContextListener {
    private String[] tables = {"Cuisine", "Feature", "Restaurant","Restaurant_Cuisine","Restaurant_Feature"};
    private String[] dbInit = {
            "CREATE TABLE Cuisine (" +
                    "    cuisine_id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "    cuisine_name VARCHAR(100) NOT NULL," +
                    "    description VARCHAR(250) NOT NULL," +
                    "    CONSTRAINT Cuisine_pk PRIMARY KEY (cuisine_id)" +
                    ")",
            "CREATE TABLE Feature (" +
                    "    feature_id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "    feature_text VARCHAR(100) NOT NULL," +
                    "    CONSTRAINT Feature_pk PRIMARY KEY (feature_id)" +
                    ")",
            "CREATE TABLE Restaurant (" +
                    "    restaurant_id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    address VARCHAR(100) NOT NULL," +
                    "    website VARCHAR(100)," +
                    "    zip_code VARCHAR(100) NOT NULL," +
                    "    rating DECIMAL(2,1) NOT NULL," +
                    "    price_range VARCHAR(10) NOT NULL," +
                    "    lat DECIMAL(8,6) NOT NULL," +
                    "    lon DECIMAL(8,6) NOT NULL," +
                    "    CONSTRAINT Restaurant_pk PRIMARY KEY (restaurant_id)" +
                    ")",
            "CREATE TABLE Restaurant_Cuisine (" +
                    "    restaurant_id INT NOT NULL," +
                    "    cuisine_id INT NOT NULL," +
                    "    CONSTRAINT Restaurant_Cuisine_pk PRIMARY KEY (restaurant_id, cuisine_id)," +
                    "    CONSTRAINT Restaurant_Cuisine_Restaurant FOREIGN KEY (restaurant_id)" +
                    "        REFERENCES Restaurant (restaurant_id)," +
                    "    CONSTRAINT Restaurant_Cuisine_Cuisine FOREIGN KEY (cuisine_id)" +
                    "        REFERENCES Cuisine (cuisine_id)" +
                    ")",
            "CREATE TABLE Restaurant_Feature (" +
                    "    restaurant_id INT NOT NULL," +
                    "    feature_id INT NOT NULL," +
                    "    CONSTRAINT Restaurant_Feature_pk PRIMARY KEY (restaurant_id, feature_id)," +
                    "    CONSTRAINT Restaurant_Feature_Restaurant FOREIGN KEY (restaurant_id)" +
                    "        REFERENCES Restaurant (restaurant_id)," +
                    "    CONSTRAINT Restaurant_Feature_Feature FOREIGN KEY (feature_id)" +
                    "        REFERENCES Feature (feature_id)" +
                    ")"
    };
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby:somethingDB;create=true");
            sce.getServletContext().setAttribute("DBcon", connection);

            Statement statement = connection.createStatement();
            DatabaseMetaData dbData = connection.getMetaData();
            for (int i = 0; i < tables.length; i++) {
                if (!exists(dbData, tables[i])){
                    statement.executeUpdate(dbInit[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = ((Connection) sce.getServletContext().getAttribute("DBcon"));
        if (connection != null){
            try {
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
