package server.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;
import java.util.ArrayList;

public class InventoryDBController implements ConnectDetailsContainer{
	
	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	public void connect() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			System.out.println("Attempting Connection...");
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	        System.out.println("Database Connection Succesful.");
		} catch(SQLException e) {
			System.err.println("A problem has been encountered when trying to make a connection.");
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertIntoToolTable(int id, String name, int quantity, double price, int supplierId, String type, String powerType) {
		try {
			String insertTool = "INSERT INTO TOOLTABLE(TOOLID, NAME, QUANTITY, PRICE, SUPPLIERID, TYPE, POWERTYPE) VALUES (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(insertTool);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, quantity);
			pstmt.setDouble(4, price);
			pstmt.setInt(5, supplierId);
			pstmt.setString(6, type);
			pstmt.setString(7, powerType);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] resultsToArray(ResultSet rs) {
		String[] arr = null;
		try {
			arr = new String[7];
			for(int i = 0; i < arr.length; i++) {
				arr[i] = rs.getString(i+1);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return arr;
	}
	
	public ArrayList<String[]> queryAllTools() {
		ArrayList<String[]> toolList = new ArrayList<String[]>();
		String queryAllTools = "SELECT * FROM TOOLTABLE";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(queryAllTools);
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					toolList.add(resultsToArray(rs));
				}
			}
			else {
				System.out.println("\nSearch failed to find data in ToolTable");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toolList;
	}
	
	public String[] executeToolQuery(String queryTool) {
		String[] toolInfo = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(queryTool);
			if(rs.isBeforeFirst()) {
				rs.next();
				toolInfo = resultsToArray(rs);
			}

			else {
				System.out.println("\nSearch failed to find tool");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toolInfo;
	}
	
	public String[] queryByName(String toolName) {
		String queryName = "SELECT * FROM TOOLTABLE WHERE NAME = '" +toolName+ "'";
		return executeToolQuery(queryName);
	}
	
	public String[] queryById(int id) {
		String queryId = "SELECT * FROM TOOLTABLE WHERE TOOLID = " +id+ "";
		return executeToolQuery(queryId);
	}
	
	public void decreaseQuantity(String toolName) {
        String queryId = "UPDATE TOOLTABLE SET QUANTITY = QUANTITY-1 WHERE NAME = '" +toolName+ "'";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(queryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}