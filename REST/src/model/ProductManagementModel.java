package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProductManagementModel {
	
	private Connection connect(){
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product_management_db", "paf_user", "^paf_user_pw_000");
		}
		catch (Exception e)
		{
			e.printStackTrace();}
		return con;
	}
	
	public boolean addProducts (int research_id, String name, String description, int stock_quantity, double price, String added_date) {

		try {

			Connection con = connect();
			
			if (con == null) {
				return false;
			}
			
			// create a prepared statement
			String query =  " insert into products (`research_id`,`name`,`description`,`stock_quantity`,`price`,`added_date`) values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			preparedStmt.setInt(1, research_id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, description);
			preparedStmt.setInt(4, stock_quantity);
			preparedStmt.setDouble(5, price);
			preparedStmt.setString(6, added_date);
			preparedStmt.executeUpdate();

		} catch (Exception e) {

			System.err.println(e.getMessage());
			return false;

		}

		return true;
	}
	
	public boolean addSold_products (int products_id, int buyer_id, String sold_date) {

		try {

			Connection con = connect();
			
			if (con == null) {
				return false;
			}
			
			// create a prepared statement
			String query =  " insert into products_sold (`products_id`,`buyer_id`,`sold_date`) values (?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			preparedStmt.setInt(1, products_id);
			preparedStmt.setInt(2, buyer_id);
			preparedStmt.setString(3, sold_date);
	
			preparedStmt.executeUpdate();

		} catch (Exception e) {

			System.err.println(e.getMessage());
			return false;

		}

		return true;
	}
	
	
	public String readProducts() {
		JsonObject products = new JsonObject();
		
		try {

			Connection con = connect();
			if (con == null) {
				return products.toString();
			}
			// create a prepared statement
			String query = " select * from products ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();
			
			while (rs.next()) {
				
				JsonObject innerProducts= new JsonObject();
				innerProducts.addProperty("products_id", rs.getInt(1));
				innerProducts.addProperty("research_id", rs.getInt(2));
				innerProducts.addProperty("name", rs.getString(3));
				innerProducts.addProperty("description", rs.getString(4));
				innerProducts.addProperty("stock_quantity", rs.getInt(5));
				innerProducts.addProperty("price", rs.getDouble(6));
				innerProducts.addProperty("added_date", rs.getString(7));
				array.add(innerProducts);

			}
			products.add("products", array);

		} catch (Exception e) {

			e.printStackTrace();
			products.addProperty("status", "error");
			return products.toString();

		}

		return products.toString();
	}
	
	public String readSoldProducts() {
		JsonObject SoldProducts = new JsonObject();
		
		try {

			Connection con = connect();
			if (con == null) {
				return SoldProducts.toString();
			}
			// create a prepared statement
			String query = " select * from products_sold ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();
			
			while (rs.next()) {
				JsonObject innerProducts= new JsonObject();
				innerProducts.addProperty("products_id", rs.getInt(1));
				innerProducts.addProperty("buyer_id", rs.getInt(2));
				innerProducts.addProperty("sold_date", rs.getString(3));
			
				array.add(innerProducts);

			}
			SoldProducts.add("sold_Products", array);

		} catch (Exception e) {

			e.printStackTrace();
			SoldProducts.addProperty("status", "error");
			return SoldProducts.toString();

		}

		return SoldProducts.toString();
	}
	
	public String readProducts(int products_id) {
		
		JsonObject products = new JsonObject();

		try {

			Connection con = connect();
			if (con == null) {
				return products.toString();
			}
			// create a prepared statement
			String query = " select * from products where products_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, products_id);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray array = new JsonArray();
			JsonObject innerProducts = new JsonObject();
			while (rs.next()) {

				innerProducts.addProperty("research_id", rs.getInt(2));
				innerProducts.addProperty("name", rs.getString(3));
				innerProducts.addProperty("description", rs.getString(4));
				innerProducts.addProperty("stock_quantity", rs.getInt(5));
				innerProducts.addProperty("price", rs.getDouble(6));
				innerProducts.addProperty("added_date", rs.getString(7));
				array.add(innerProducts);

			}
			products.add("products", array);

		} catch (Exception e) {

			e.printStackTrace();
			products.addProperty("status", "error");
			return products.toString();

		}

		return products.toString();
	}
	
	public String removeProducts( int products_id) {
		String output = "Error";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " Delete FROM products where products_id = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setInt(1, products_id);
		
			// execute the statement

			int result = preparedStmt.executeUpdate();

			if (result >= 1) {
				output = "Successfully Deleted";

			}
			con.close();

		} catch (Exception e) {
			output = "Exception Occurred";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateProduct(int products_id, int research_id, String name, String description, int stock_quantity, double price, String added_date){
		 
		String output = "";
		try{
			 Connection con = connect();
			 if (con == null){
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 // create a prepared statement
			 String query = "UPDATE products SET research_id=?,name=?,description=?,stock_quantity=?,price=?,added_date=? WHERE products_id=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, research_id);
			 preparedStmt.setString(2, name);
			 preparedStmt.setString(3, description);
			 preparedStmt.setInt(4, stock_quantity);
			 preparedStmt.setDouble(5, price);
			 preparedStmt.setString(6, added_date);
			 preparedStmt.setInt(7, products_id);
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Updated successfully";
			 
		 }
		 catch (Exception e)
		 {
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		 }
		 return output;
		 }

}
