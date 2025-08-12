import java.sql.*;

public class JDBCDemo {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String username = "root";
        String password = "root";

        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println(" Database connected successfully!");

            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM employees");

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Name: " + rs.getString("name"));
            }

            String insertSQL = "INSERT INTO employees (name, department) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "Govinda");
            pstmt.setString(2, "IT");
            int rowsInserted = pstmt.executeUpdate();
            System.out.println("\n Rows inserted: " + rowsInserted);


            conn.commit();
            System.out.println("\n💾 Transaction committed successfully!");

   
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
            System.out.println("\n Connection closed!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Rolling back changes due to error...");
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(" MySQL Driver not found!");
            e.printStackTrace();
        }
    }
}
