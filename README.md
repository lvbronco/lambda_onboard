
```
To run single test (Web Browser Automation) use below command
     mvn test -D suite=single.xml

To run single test (Mobile Browser Automation) use below command
     mvn test -D suite=mobile.xml

To run parallel test (Web Browser Automation) use below command
     mvn test -D suite=parallel.xml

To run single test (Android App Automation) use below command
     mvn test -D suite=android_single.xml
    
To run single test (Android App Automation) use below command
     mvn test -D suite=android_parallel.xml    
 
To run single test (iOS App Automation) use below command
     mvn test -D suite=iOS_single.xml
    
To run single test (iOS App Automation) use below command
     mvn test -D suite=iOS_parallel.xml 

To run Cypress Test use below command
     npm test    
     
To run playwright Test - Single Thread    
     node playwright_single.js 
     
To run playwright Test - Parallel 3 Thread's
     node playwright_parallel.js   

```

### Visual UI Tests with Selenium

Navigate to https://beta-smartui.lambdatest.com/

1. Create a project with name - Demo  
![image](https://user-images.githubusercontent.com/97744525/161967674-a0d6668c-9079-4f08-b06e-c9132ef1b801.png)
2. Run command ```  mvn test -D suite=visual_baseline.xml  ``` 
3. run command ```  mvn test -D suite=visualchangebuild.xml ```

## Note- Above Step 1 & 2 in Visual UI test needs to be executed only onc's i.e to create the project and run the baseline.
## In Demo's please don't change Baseline


