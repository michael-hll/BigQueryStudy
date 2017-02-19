package com.hll.bigquery.study;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.cloud.bigquery.Field;

public class SalesOrderHelper {

	public static JSONObject createOneSalesOrderRow(
			String sales_order_id,
			String product_id,
			String product_name,
			String product_price,
			String user_id,
			
			String user_firstname,
			String user_lastname,
			String company_id,
			String company_name,
			String customer_id,
			
			String customer_name,
			String status,
			String sales_order_date,
			String sales_order_number
			){
		JSONObject json = new JSONObject();
		Field.Mode.REQUIRED.toString();
    	json.put("sales_order_id", sales_order_id);
    	json.put("product_id", product_id);
    	json.put("product_name", product_name);
    	json.put("product_price", product_price);
    	json.put("user_id", user_id);
    	
    	json.put("user_firstname", user_firstname);
    	json.put("user_lastname", user_lastname);
    	json.put("company_id", company_id);
    	json.put("company_name", company_name);
    	json.put("customer_id", customer_id);
    	
    	json.put("customer_name", customer_name);
    	json.put("status", status);
    	json.put("sales_order_date", sales_order_date);
    	json.put("sales_order_number", sales_order_number);
    	
    	return json;
	}
	
	public static List<String> getSalesOrderFieldds(){
		// TODO: Later we need to create an entity for nomal cases
		// Currently it's just a sample
		List<String> fields = new ArrayList<String>();
		fields.add("sales_order_id");
		fields.add("product_id");
		fields.add("product_name");
		fields.add("product_price");
		fields.add("user_id");
		
		fields.add("user_firstname");
		fields.add("user_lastname");
		fields.add("company_id");
		fields.add("company_name");
		fields.add("customer_id");
		
		fields.add("customer_name");
		fields.add("status");
		fields.add("sales_order_date");
		fields.add("sales_order_number");
		return fields;
	}
}
