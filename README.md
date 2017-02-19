# BigQuery Study
A sample code to load one million sales order records into bigquery and executed a select SQL command with groupby and aggregation method.

It's a maven java command project:</br>
```xml
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-bigquery</artifactId>
    <version>0.8.1-beta</version>
</dependency>
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20160810</version>
</dependency>
```

In order to use Google's BigQuery java library, you need follow the steps from below link first:
https://cloud.google.com/bigquery/docs/reference/libraries#client-libraries-install-java </br>

It also uses the BigQuerySnippets sample code from Github: https://github.com/GoogleCloudPlatform/google-cloud-java/blob/master/google-cloud-examples/src/main/java/com/google/cloud/examples/bigquery/snippets/BigQuerySnippets.java
</br>But I enhanced it to meet my requirement.

The table schema with json format looks like below:

```json
{"company_id":"1","user_firstname":"michael","user_lastname":"han","product_price":"5000.50","product_name":"iPhone6","sales_order_number":"001","sales_order_date":"1486283602455","user_id":"1","sales_order_id":"1","product_id":"1","company_name":"QAD","customer_name":"HP","customer_id":"1","status":"open"}
```

## Execution & Output
After you import it into your Eclipse project, run the java main method, you can see the following output in the console:
</br>
The important thing is it used about 2 seconds to execute the SQL command with 1 million rows. The BigQuery official said it could execute SQL query command fast even with 1 trillion records.

```
Step 1 start: Generate json data file at: Sun Feb 19 13:23:02 CST 2017
A json file with records[1000000] :/Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json
Step 1 end: Generate json data file at: Sun Feb 19 13:23:14 CST 2017

Step 2 start: Compress json data file at: Sun Feb 19 13:23:14 CST 2017
A json.gz file was created: /Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json.gz
Step 2 end: Compress json data file end at: Sun Feb 19 13:23:16 CST 2017

Step 3 start: Get BigQuery instance at: Sun Feb 19 13:23:16 CST 2017
Step 3 end: Get BigQuery instance at: Sun Feb 19 13:23:16 CST 2017

Step 4 start: Create dataset and table in google bigquery at: Sun Feb 19 13:23:16 CST 2017
DataSet: sales_order_ds, table: sales_order were created!
Step 4 end: Create dataset and table in google bigquery at: Sun Feb 19 13:23:27 CST 2017

Step 5 start: Loading json.gz file into google bigquery at: Sun Feb 19 13:23:27 CST 2017
A json.gz file was loaded into bigquery: /Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json.gz
Step 5 end: Loading json.gz file into google bigquery at: Sun Feb 19 13:24:22 CST 2017

Step 6 start: Executing sql query command at: Sun Feb 19 13:24:22 CST 2017
A query command :
SELECT product_name, sum(product_price) total_amount FROM [bigquery-study-157507:sales_order_ds.sales_order] group by product_name order by total_amount desc LIMIT 10
was executed! Executing result lists below:
product 999, 9.99E7, 
product 998, 9.98E7, 
product 997, 9.97E7, 
product 996, 9.96E7, 
product 995, 9.95E7, 
product 994, 9.94E7, 
product 993, 9.93E7, 
product 992, 9.92E7, 
product 991, 9.91E7, 
product 990, 9.9E7, 
Step 6 end: Executing sql query command at: Sun Feb 19 13:24:24 CST 2017

Celebrating to yourself!!!
```