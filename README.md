Internal Resource Allocation Portal | IRA

The IRA portal is a web application built with React and Spring Boot. It enables the PMO to upload resumes and assign interviewers to candidates. Interviewers can view the candidates assigned to them and provide feedback after conducting interviews. This feedback is also accessible to the PMO.


The frontend and backend both are integrated into a single zip. To run the project in Visual Studio Code, follow these steps:

1. Open a terminal in VS Code .and run the backend using the command:   mvn spring-boot:run
   Note :- 1. cd accentureproject3 is required 
           2. Maven should be installed in the system
           3. Download the STS extension for VScode
           4. MySQL should be installed in the system / create one DB in Mysql
           5. Change the DB name, ID & password in application.properties
           6. Before running all dependencies should be build. :- mvn install 

  -> IF you want to run backend in STS 
       - Import the project in STS 
       - MySQL should be installed in the system / create one DB in Mysql
       - Change the DB name, ID & password in application.properties
       - Run AccentureApplication.java
  

2. Open another terminal in VS Code and navigate to the frontend directory, then run the frontend using the command: npm start
   Note :- 
   1. Frontend directory :- accentureproject3\src\main\resources\jobportal\jobportal
   2. npm i 
   3. Change the image path in index.css
       - line no. 11 background-image: url("PATH")
       - accentureproject3\src\main\resources\jobportal\jobportal\src\Components\Assets\wp10409377-accenture-wallpapers.jpg - replace the path of this image in your system
   

Admin signup (First signup) - update accenture.users set active=1, approved=1 where id=1; Use this query to update/approve the first admin account in MySql

Uploading Resume :- 
      - Only specified resume pattern are allowed in PPT format, sample resume is being attached.
     
 
