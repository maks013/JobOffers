# Job Offers

## About

Job Offers is a system that aggregates job listings for the position of Junior Java Developer from external server. Upon authentication and token issuance, users gain the ability to access, review, and post new job offers for such positions. This application serves as a centralized hub for potential applicants to find relevant job opportunities.
## Built With

The Job Offers system is developed using robust and modern technologies to ensure efficient performance and a seamless user experience:

<div >
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" title="Maven"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/192107858-fe19f043-c502-4009-8c47-476fc89718ad.png" alt="REST" title="REST"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117533873-484d4480-afef-11eb-9fad-67c8605e3592.png" alt="JUnit" title="JUnit"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/184097317-690eea12-3a26-4f7c-8521-729ebbbb3f98.png" alt="Testcontainers" title="Testcontainers"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/182884177-d48a8579-2cd0-447a-b9a6-ffc7cb02560e.png" alt="mongoDB" title="mongoDB"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117207330-263ba280-adf4-11eb-9b97-0ac5b40bc3be.png" alt="Docker" title="Docker"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/182884894-d3fa6ee0-f2b4-4960-9961-64740f533f2a.png" alt="redis" title="redis"/>
</div>
 
## Features

- **Token-Based Authentication**: Ensures that only authenticated users can access, create, and manage job offers.
- **Job Offer Management**: Users can browse available job listings or post new ones.
- **Real-Time Data**: Integrates with external server to fetch the latest job offers.

## Architecture
![Alt text](https://github.com/maks013/JobOffers/blob/master/architecture/job-offers-architecture-1.0.png)

## Usage

### Login and Register Controller

This controller manages user authentication and registration processes.

- `POST /login` - Authenticates a user by validating their login credentials and returns a JWT token for accessing secured endpoints.
- `POST /register` - Registers a new user with a username and password, and returns registration confirmation.

### Offer Controller

This controller handles operations related to job offers.

- `GET /offers` - Retrieves a list of all available job offers.
- `GET /offers/{id}` - Retrieves a specific job offer by its unique identifier.
- `POST /offers` - Creates a new job offer with the provided job details and adds it to the listing.
- `DELETE /offers/{id}` - Deletes a specific job offer from the listing using its unique identifier.
