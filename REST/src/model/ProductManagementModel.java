package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
	
	public String addProducts (String research_id, String name, String description, String stock_quantity, String price, String added_date) {
		
		String output = "";	
		
		try {

			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query =  " insert into products (`products_id`,`research_id`,`name`,`description`,`stock_quantity`,`price`,`added_date`) values (?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setInt(2, Integer.parseInt(research_id));
			 preparedStmt.setString(3, name);
			 preparedStmt.setString(4, description);
			 preparedStmt.setInt(5, Integer.parseInt(stock_quantity));
			 preparedStmt.setDouble(6, Double.parseDouble(price));
			 preparedStmt.setString(7, added_date);
			
			// execute the statement
			 preparedStmt.execute();
			 con.close();

			
			String newUProducts = readProducts();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newUProducts + "\"}"; 
			 

		}catch (Exception e) {

			output = "{\"status\":\"error\", \"data\": \"Error while inserting the product.\"}";
			 System.err.println(e.getMessage()); 
		}

		return output;
	}
	
//	public boolean addSold_products (int products_id, int buyer_id, String sold_date) {
//
//		try {
//
//			Connection con = connect();
//			
//			if (con == null) {
//				return false;
//			}
//			
//			// create a prepared statement
//			String query =  " insert into products_sold (`products_id`,`buyer_id`,`sold_date`) values (?, ?, ?)"; 
//			PreparedStatement preparedStmt = con.prepareStatement(query);
//			// binding values
//
//			
//			preparedStmt.setInt(1, products_id);
//			preparedStmt.setInt(2, buyer_id);
//			preparedStmt.setString(3, sold_date);
//	
//			preparedStmt.executeUpdate();
//
//		} catch (Exception e) {
//
//			System.err.println(e.getMessage());
//			return false;
//
//		}
//
//		return true;
//	}
	
	
//	public String readProducts() {
//		JsonObject products = new JsonObject();
//		
//		try {
//
//			Connection con = connect();
//			if (con == null) {
//				return products.toString();
//			}
//			// create a prepared statement
//			String query = " select * from products ";
//			PreparedStatement preparedStmt = con.prepareStatement(query);
//			// binding values
//
//			
//			ResultSet rs = preparedStmt.executeQuery();
//
//			JsonArray array = new JsonArray();
//			
//			while (rs.next()) {
//				
//				JsonObject innerProducts= new JsonObject();
//				innerProducts.addProperty("products_id", rs.getInt(1));
//				innerProducts.addProperty("research_id", rs.getInt(2));
//				innerProducts.addProperty("name", rs.getString(3));
//				innerProducts.addProperty("description", rs.getString(4));
//				innerProducts.addProperty("stock_quantity", rs.getInt(5));
//				innerProducts.addProperty("price", rs.getDouble(6));
//				innerProducts.addProperty("added_date", rs.getString(7));
//				array.add(innerProducts);
//
//			}
//			products.add("products", array);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			products.addProperty("status", "error");
//			return products.toString();
//
//		}
//
//		return products.toString();
//	}
	
	
	public String readProducts() {
		String output = ""; 

		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			output = "<table border='1'><tr><th>products_id</th>"
					 + "<th>research_id</th><th>name</th>"
					 + "<th>description</th><th>stock_quantity</th>"
					 + "<th>price</th><th>added_date</th>"
					 + "<th>Update</th><th>Remove</th></tr>";
			
			// create a prepared statement
			String query = " select * from products ";
			Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query); 
			// binding values

			
			while (rs.next()) {
				String products_id = Integer.toString(rs.getInt("products_id"));
				String research_id = Integer.toString(rs.getInt("research_id"));
				String name = rs.getString("name");
				String description = rs.getString("description");
				String stock_quantity = Integer.toString(rs.getInt("stock_quantity"));
				String price = Double.toString(rs.getDouble("price"));
				String added_date = rs.getString("added_date");
				
				
				output += "<td>" + products_id + "</td>";
				output += "<td>" + research_id + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + stock_quantity + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + added_date + "</td>";
						 
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-productid='" + products_id + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-productid='" + products_id + "'></td></tr>";
				

			}
			con.close();
			 // Complete the html table
			 output += "</table>";

		} catch (Exception e) {

			output = "Error while reading the products.";
			 System.err.println(e.getMessage()); 

		}

		return output; 
	}
	
//	public String readSoldProducts() {
//		JsonObject SoldProducts = new JsonObject();
//		
//		try {
//
//			Connection con = connect();
//			if (con == null) {
//				return SoldProducts.toString();
//			}
//			// create a prepared statement
//			String query = " select * from products_sold ";
//			PreparedStatement preparedStmt = con.prepareStatement(query);
//			// binding values
//
//			
//			ResultSet rs = preparedStmt.executeQuery();
//
//			JsonArray array = new JsonArray();
//			
//			while (rs.next()) {
//				JsonObject innerProducts= new JsonObject();
//				innerProducts.addProperty("products_id", rs.getInt(1));
//				innerProducts.addProperty("buyer_id", rs.getInt(2));
//				innerProducts.addProperty("sold_date", rs.getString(3));
//			
//				array.add(innerProducts);
//
//			}
//			SoldProducts.add("sold_Products", array);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			SoldProducts.addProperty("status", "error");
//			return SoldProducts.toString();
//
//		}
//
//		return SoldProducts.toString();
//	}
	
//	public String readProducts(int products_id) {
//		
//		JsonObject products = new JsonObject();
//
//		try {
//
//			Connection con = connect();
//			if (con == null) {
//				return products.toString();
//			}
//			// create a prepared statement
//			String query = " select * from products where products_id = ? ";
//			PreparedStatement preparedStmt = con.prepareStatement(query);
//			// binding values
//
//			preparedStmt.setInt(1, products_id);
//			ResultSet rs = preparedStmt.executeQuery();
//
//			JsonArray array = new JsonArray();
//			JsonObject innerProducts = new JsonObject();
//			while (rs.next()) {
//
//				innerProducts.addProperty("research_id", rs.getInt(2));
//				innerProducts.addProperty("name", rs.getString(3));
//				innerProducts.addProperty("description", rs.getString(4));
//				innerProducts.addProperty("stock_quantity", rs.getInt(5));
//				innerProducts.addProperty("price", rs.getDouble(6));
//				innerProducts.addProperty("added_date", rs.getString(7));
//				array.add(innerProducts);
//
//			}
//			products.add("products", array);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			products.addProperty("status", "error");
//			return products.toString();
//
//		}
//
//		return products.toString();
//	}
	
	public String removeProducts( String products_id) {
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

			preparedStmt.setInt(1, Integer.parseInt(products_id));
		
			// execute the statement

			preparedStmt.executeUpdate();

			con.close();
			
			String newProducts = readProducts();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newProducts + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the product.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateProduct(String products_id, String research_id, String name, String description, String stock_quantity, String price, String added_date){
		 
		String output = "error";
		try{
			 Connection con = connect();
			 if (con == null){
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 if (output.equals("invalid")) {
					return output;
				}
			 
			 
			 // create a prepared statement
			 String query = "UPDATE products SET research_id=?,name=?,description=?,stock_quantity=?,price=?,added_date=? WHERE products_id=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(research_id));
			 preparedStmt.setString(2, name);
			 preparedStmt.setString(3, description);
			 preparedStmt.setInt(4, Integer.parseInt(stock_quantity));
			 preparedStmt.setDouble(5, Double.parseDouble(price));
			 preparedStmt.setString(6, added_date);
			 preparedStmt.setInt(7, Integer.parseInt(products_id));
			
			 
			 // execute the statement
			 preparedStmt.executeUpdate();
			 con.close();
			 
			 String newProducts = readProducts();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newProducts + "\"}"; 
			 
		 }
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the Product.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
		 }

}
