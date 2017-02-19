package com.hll.bigquery.study;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.Table;

/**
 * Hello Google BigQuery!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Step 1: Generate json data file
    	System.out.println("Step 1 start: Generate json data file at: " + (new Date()).toString());
    	String jsonFile = writeSalesOrder(1000000); // 1 million records
    	System.out.println("Step 1 end: Generate json data file at: " + (new Date()).toString());
    	
    	// Step 2: Compress json data file
    	System.out.println("\nStep 2 start: Compress json data file at: " + (new Date()).toString());
    	String gzipFile = GZipHelper.compressGzipFile(jsonFile, jsonFile + ".gz");
    	System.out.println("Step 2 end: Compress json data file end at: " + (new Date()).toString());
    	
    	// Step 3: Get BigQuery instance
    	System.out.println("\nStep 3 start: Get BigQuery instance at: " + (new Date()).toString());
    	BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
    	BigQuerySnippets snippets = new BigQuerySnippets(bigquery);
    	System.out.println("Step 3 end: Get BigQuery instance at: " + (new Date()).toString());
    	
    	// Step 4: Create dataset and table in google bigquery
    	System.out.println("\nStep 4 start: Create dataset and table in google bigquery at: " + (new Date()).toString());
    	String datasetName = "sales_order_ds";
    	String tableName = "sales_order";
    	boolean isAppendData = false;
    	try{
    		Dataset dataset = snippets.getDataset(datasetName);
        	if(dataset == null)
        		snippets.createDataset(datasetName);
        	Table table = bigquery.getTable(datasetName, tableName);
        	if(table == null){
        		snippets.createTable(datasetName, tableName, snippets.createSchema());
        	}else{
        		if(isAppendData == false){
        			// program will delete the existing table first, then create a new table with the same schema
	        		Schema schema = table.getDefinition().getSchema();
	        		Boolean deleteSuccess = snippets.deleteTable(datasetName, tableName);
	        		if(deleteSuccess)
	        			snippets.createTable(datasetName, tableName, schema);
        		}
        	}
        	System.out.println("DataSet: " + datasetName + ", table: " + tableName + " were created!");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	System.out.println("Step 4 end: Create dataset and table in google bigquery at: " + (new Date()).toString());
    	
    	// Step 5: Loading json.gz file into google bigquery
    	System.out.println("\nStep 5 start: Loading json.gz file into google bigquery at: " + (new Date()).toString());
    	loadSalesOrder(gzipFile, bigquery,snippets, datasetName, tableName);
    	System.out.println("Step 5 end: Loading json.gz file into google bigquery at: " + (new Date()).toString());
    	
    	// Step 6: Executing sql query command
    	System.out.println("\nStep 6 start: Executing sql query command at: " + (new Date()).toString());
    	String sql = "SELECT product_name, sum(product_price) total_amount" +
    				 " FROM [bigquery-study-157507:" + datasetName + "." + tableName + "]" + // From project_name.datasetName.tableName
    				 " group by product_name" +
    				 " order by total_amount desc" +
    				 " LIMIT 10";
    	execBigQuery(sql, snippets);
    	System.out.println("Step 6 end: Executing sql query command at: " + (new Date()).toString());
    	
    	System.out.println("\nCelebrating to yourself!!!");	
    }
    
    static public void loadSalesOrder(String jsonFileFullPath, BigQuery bigquery, BigQuerySnippets snippets, String datasetName, String tableName){
    	try{
	    	snippets.writeFileToTable(datasetName, tableName, getPath(jsonFileFullPath), FormatOptions.json());
	    	System.out.println("A json.gz file was loaded into bigquery: " + jsonFileFullPath);
    	}catch(IOException ioex){
    		System.out.println(ioex.getMessage());
    	}catch(InterruptedException interEx){
    		
    	}catch(TimeoutException toEx){
    	}
    }
    
    static public void execBigQuery(String sql, BigQuerySnippets snippets){
    	try{
	    	snippets.runQuery(sql);
    	}catch(InterruptedException interEx){
    		
    	}
    }
    
    static public Path getPath(String fullFileName){ 
    	try{
    		File file=new File(fullFileName);
    		return file.toPath();
    	}catch(NullPointerException ex){
    		return null;
    	}
    }
    
    static public String writeSalesOrder(Integer rowCount){
    	// prepare linses content 
    	List<String> lines = new ArrayList<String>();
    	for(int i = 0; i < rowCount; i++){
    		String product = "product " + i / 1000;
    		BigDecimal product_price = (new BigDecimal(i/1000)).multiply(new BigDecimal(100));
	    	JSONObject salesOrder = SalesOrderHelper.createOneSalesOrderRow("1", "1", product, product_price.toString(), "1", "michael", "han", "1", "QAD", "1", "HP", "open", Long.toString(new GregorianCalendar().getTimeInMillis()), "001");
	    	lines.add(salesOrder.toString());
    	}
    	
    	// prepare file system by a given filename
    	String fileName = "SalesOrder_" + rowCount.toString() + ".json";
    	String filePath = FileSystems.getDefault().getPath("").toAbsolutePath() + "/" + fileName;
    	File newFile = new File(filePath);
    	try {
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	FileWriterHelper.writeStringToFile(newFile.toPath(), lines, false);
    	
    	System.out.println("A json file with records[" + rowCount + "] :" + filePath);
    	return filePath;
    }
}
