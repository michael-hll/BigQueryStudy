# BigQueryStudy
A sample code to load one million data into bigquery and did an select SQL command execution.

It's a maven java command project:</br>
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

In order to use Google's BigQuery java library, you need follow the steps from below link first:
https://cloud.google.com/bigquery/docs/reference/libraries#client-libraries-install-java </br>

It also use the BigQuerySnippets sample code from Github: https://github.com/GoogleCloudPlatform/google-cloud-java/blob/master/google-cloud-examples/src/main/java/com/google/cloud/examples/bigquery/snippets/BigQuerySnippets.java
</br>But I enhanced it to meet my requirement.

The table schema with json format looks like below:

{"company_id":"1","user_firstname":"michael","user_lastname":"han","product_price":"5000.50","product_name":"iPhone6","sales_order_number":"001","sales_order_date":"1486283602455","user_id":"1","sales_order_id":"1","product_id":"1","company_name":"QAD","customer_name":"HP","customer_id":"1","status":"open"} </br>

# Execution & Output
After you import into your exclipse project, run the java main method, you can see the following output in the console:

Step 1 start: Generate json data file at: Sun Feb 19 13:23:02 CST 2017</br>
A json file with records[1000000] :/Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json</br>
Step 1 end: Generate json data file at: Sun Feb 19 13:23:14 CST 2017</br>

Step 2 start: Compress json data file at: Sun Feb 19 13:23:14 CST 2017</br>
A json.gz file was created: /Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json.gz</br>
Step 2 end: Compress json data file end at: Sun Feb 19 13:23:16 CST 2017</br>

Step 3 start: Get BigQuery instance at: Sun Feb 19 13:23:16 CST 2017</br>
Step 3 end: Get BigQuery instance at: Sun Feb 19 13:23:16 CST 2017</br>

Step 4 start: Create dataset and table in google bigquery at: Sun Feb 19 13:23:16 CST 2017</br>
DataSet: sales_order_ds, table: sales_order were created!</br>
Step 4 end: Create dataset and table in google bigquery at: Sun Feb 19 13:23:27 CST 2017</br>

Step 5 start: Loading json.gz file into google bigquery at: Sun Feb 19 13:23:27 CST 2017</br>
A json.gz file was loaded into bigquery: /Users/Janine/Documents/workspace/BigQueryStudy/SalesOrder_1000000.json.gz</br>
Step 5 end: Loading json.gz file into google bigquery at: Sun Feb 19 13:24:22 CST 2017</br>

Step 6 start: Executing sql query command at: Sun Feb 19 13:24:22 CST 2017</br>
A query command :</br>
SELECT product_name, sum(product_price) total_amount FROM [bigquery-study-157507:sales_order_ds.sales_order] group by product_name order by total_amount desc LIMIT 10</br>
was executed! Executing result lists below:</br>
product 999, 9.99E7, </br>
product 998, 9.98E7, </br>
product 997, 9.97E7, </br>
product 996, 9.96E7, </br>
product 995, 9.95E7, </br>
product 994, 9.94E7, </br>
product 993, 9.93E7, </br>
product 992, 9.92E7, </br>
product 991, 9.91E7, </br>
product 990, 9.9E7, </br>
Step 6 end: Executing sql query command at: Sun Feb 19 13:24:24 CST 2017</br>

Celebrating to yourself!!!