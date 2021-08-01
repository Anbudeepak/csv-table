# CSV To Dynamic Table

## CSV Upload
http://localhost:8080 - upload a csv file thru this UI, It will create dynamically create table with all the columns as per CSV file. 

**Below are the dynamic configurations**
`csvtable.default.table.name=csvtable` configure the table name, csvtable is the table name by default
`csvtable.default.table.columns.size=20`configure the size of the table columns, 20 is the default size

## Find Records From Table
http://localhost:8080/get-all-records - returns all the records from table (api for testing)

## DB Configs
** In Memory H2 DB
`spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password`