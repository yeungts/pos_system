package posSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import posSystem.db.DBConnector;

/**
 *
 * @author kenye
 */
public class UserDb {

    public UserDb() {
    }

    public static User getUser(String username, String password)
            throws Exception {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        
        try{
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            rs = stmt.executeQuery();

            if (rs.next()) {
                String ID = rs.getString("userid");
                String userName = rs.getString("username");
                int manager = rs.getInt("manager");

                user = new User(ID, userName, manager);
            } else {
                throw new Exception("User not found");
            }
        } catch (ClassNotFoundException ex) {
            throw new Exception (ex.toString());
        } catch (SQLException ex) {
            throw new Exception (ex.toString());
        } catch (Exception ex) {
            throw new Exception (ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt, rs);  
        }

        return user;
    }
}
