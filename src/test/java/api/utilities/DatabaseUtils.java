package api.utilities;


import java.sql.*;
import java.util.*;

public class DatabaseUtils {

    public static List<Map<String, String>> getDataFromDatabase(String query) throws ClassNotFoundException {
        String url = "jdbc:mysql://127.0.0.1:3306/fhir_db";
        String username = "root";
        String password = "Test123#";
        List<Map<String, String>> dataList = new ArrayList<>();
     // Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");



        try (Connection connection = DriverManager.getConnection(url, username, password);
        		
        		
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getString(i));
                }
                dataList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
