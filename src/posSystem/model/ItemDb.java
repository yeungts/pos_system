/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ItemDb {

    public ItemDb() {
    }

    public static ItemList getItems() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ItemList items = new ItemList();

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "SELECT * FROM items";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String itemId = Integer.toString(rs.getInt("itemID"));
                String itemName = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                if (itemId.length() < 5) {
                    String zero = "";
                    for (int i = 0; i < (5 - itemId.length()); i++) {
                        zero += "0";
                    }
                    itemId = zero + itemId;
                }

                items.add(new Item(itemId, price, itemName, inventory));
            }
        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt, rs);
        }
        return items;
    }

    public static void updateItem(int id, double price, int inventory)
            throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "UPDATE items SET price = ?, inventory = ? WHERE itemID = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, price);
            stmt.setInt(2, inventory);
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt);
        }
    }

    public static void AddItem(String id, String name, double price, int inventory)
            throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        id = prepStringField(id, 5);
        name = prepStringField(name, 50);

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "INSERT INTO items (itemID, name, price, inventory) "
                    + "VALUES (?,?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setInt(4, inventory);
            stmt.executeUpdate();

        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt);
        }
    }

    public static void removeItem(String id)
            throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "DELETE FROM items WHERE itemID = ?;"
                    + "INSERT INTO log (itemID, typeid) VALUES (?, 3)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setInt(2, Integer.parseInt(id));
            stmt.executeUpdate();

        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt);
        }
    }
    
    public static int getTransactionId() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int transactionid;

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);
            
            String sql = "SELECT transactionid FROM log;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    transactionid = rs.getInt("transactionid");
                } while (rs.next());
                transactionid++;
            } else {
                transactionid = 1;
            }

        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt, rs);
        }
        return transactionid;
    }
    
    public static void purchaseItem(int newInventory, int qty,
            String id, int transactionid) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String driver = "org.postgresql.Driver";
            String connURL = "jdbc:postgresql://ec2-174-129-254-218.compute-1.amazonaws.com/";
            String database = "dfo59qato0ool5";
            String account = "sosajrkcdfsddy";
            String pass = "a69fd1982852e9244d63bce9a0fbb17334df60c3ccee6cd2a7c4a2ba16b27644";

            conn = DBConnector.getConnection(driver, connURL, database, account, pass);

            String sql = "UPDATE items SET inventory = ? WHERE itemID = ?;"
                    + "INSERT INTO log (itemID, qty, transactionid, typeid) VALUES (?, ?, ?, 4)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newInventory);
            stmt.setInt(2, Integer.parseInt(id));
            stmt.setInt(3, Integer.parseInt(id));
            stmt.setInt(4, qty);
            stmt.setInt(5, transactionid);
            stmt.executeUpdate();

        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.toString());
        } catch (SQLException ex) {
            throw new Exception(ex.toString());
        } catch (Exception ex) {
            throw new Exception(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, stmt);
        }
    }

    /**
     * This method will prepare the String field that fits the record size
     *
     * @param value name of an Item
     * @param size assigned size of a string
     * @return a string that fits the record size
     */
    private static String prepStringField(String value, int size) {
        if (value.length() < size) {
            int numSpaces = size - value.length();
            for (int i = 1; i <= numSpaces; i++) {
                value += " ";
            }
        } else {
            value = value.substring(0, size);
        }
        return value;
    }
}
