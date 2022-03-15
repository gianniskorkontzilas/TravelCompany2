# TravelCompany Eshop - Prototype Solution
	
The following project is a prototype solution for the exercise 1 that was given to the students.
It fulfills the basic requirements and is designed in a proper way (expected architecture etc.)
	
The following requirements are implemented:

	- Publishing tickets
	- Showing a report for a list of the total number and list of the cost of tickets for all customers.
	- Showing a report for a list of the total offered itineraries per destination and offered itineraries per departure.
	- Showing a report for a list of the customers with the most tickets and with the largest cost of purchases.
	- Showing a report for a list of the customers who have not purchased any tickets.
	
## General Notes
- This app currently features no UI. A basic main is provided.
- Data read by the application is located in the root context of the project. There are three CSV files that contain the information
- Persistence storage is not implemented. The moment the application exits, any adds/updates/deletes are not stored.
- The app needs three CSV files in order to properly run. These files are customers.csv, itineraries.csv and airport_codes.csv. Each file is expected to have no missing values

## Technical Notes
- Compiled with Java 17.
- Dependencies:
	- lombok
		org.projectlombok
		version RELEASE
    
