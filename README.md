Overview

The Scalable Tracking Application is a Spring Boot-based service that generates tracking numbers for shipments based on various parameters such as origin country, destination country, and customer details

Ensure you have the following installed on your system:

Java 21

Maven 3.6+

Setup and Installation

	1. Clone the Repository

		git clone <repository-url>
		cd scalable-tracking

	2. Build the Project

		Use Maven to build the project:
		mvn clean install

	3. Run the Application
		
		You can start the application using:
		mvn spring-boot:run

		Or, if using a JAR file:

		java -jar target/scalable.tracking-0.0.1-SNAPSHOT.jar

	4. API Endpoints

		Generate Tracking Number
		
		Endpoint: GET /api/next-tracking-number

		Parameters:

		originCountryId (required, ISO 3166-1 alpha-2 country code)

		destinationCountryId (required, ISO 3166-1 alpha-2 country code)

		weightStr (required, number with up to three decimal places)

		createdAt (optional, RFC 3339 timestamp format)

		customerId (required, UUID format)

		customerName (required, string)

		customerSlug (required, string)


Example Request:
curl -X GET "http://localhost:8080/api/next-tracking-number?originCountryId=US&destinationCountryId=CA&weightStr=1.5&customerId=550e8400-e29b-41d4-a716-446655440000&customerName=JohnDoe&customerSlug=johndoe"

The application includes unit tests. Run them using:
mvn test

Conclusion

This application provides an efficient way to generate tracking numbers. Use the provided API and test cases to validate its functionality.
