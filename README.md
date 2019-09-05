# Spring Boot based Java Parser

Java based parser with Spring Boot ,that parses web server access log file, loads the log to MySQL and 
checks if a given IP makes more than a certain number of requests for the given time duration currently
one hour and 24 hours.Please ensure MySQL is up and all Schema created before running. 

  ## Setup the Application:
  
  1. Create a database named `parser` in MySQL database.
  
  2. Open `src/main/resources/application.properties` and change `spring.datasource.username` and `spring.datasource.password` properties as per your MySQL installation.
  
  3. Execute $ mvn install 
    
  ## Usage:
  
   - The tool takes "startDate", "duration" and "threshold" as command line arguments.
"startDate" is of "yyyy-MM-dd.HH:mm:ss" format, "duration" can take only "hourly",
"daily" as inputs and "threshold" can be an integer.

  - Example usage:
  
      java -jar parser.jar --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --accesslog=access.log
      
          The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 
          to 2017-01-01.14:00:00 (one hour) and print them to console and also load them to another 
          MySQL table with comments on why it's blocked.
      
      java -jar parser.jar --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250 --accesslog=access.log
      
          The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 
          to 2017-01-02.13:00:00 (24 hours) and print them to console 
          and also load them to another MySQL table with comments on why it's blocked.
    

  - Example console output:
  
    Loaded 116484 records from access.log
    Starting bulk save of 116484 log records. It will take a while...
    
    Blocked IP: 192.168.129.191 number of requests : 350 , for duration "daily" and threshold 250
    
    Blocked IP: 192.168.143.177 number of requests : 332 , for duration "daily" and threshold 250
    ...
    
    ## SQL query
    
       SELECT log.ip AS ip, COUNT(log.date_created) AS requests FROM rows_log log WHERE log.date_created BETWEEN '2017-01-01.13:00:00' AND '2017-01-01.14:00:00' GROUP BY log.ip
