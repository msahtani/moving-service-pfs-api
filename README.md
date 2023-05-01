# moving-service-pfs


## APIs
api prefix: `http://localhost:8080/api/v1`
### Authentication
* `POST ~/auth/login`: login 
* `POST ~/auth/signup`: signup
* output : the JWT token and the user type

### Client:
* `POST ~/demand/`: create a demand
* `PUT  ~/demand/{id}`: edit the demand given by id
* `DELETE ~/demand/{id}`: delete the demand given by id
* `GET ~/demand/{id}/offers`: get all the offers of the demand given by id
* `POST ~/offer/{id}/accept`: accept the offer given by id (it will create a service object related to it)
* `PUT ~/service/{id}/cancel`: cancel the service
* `PUT ~/service/{id}/close`: close the service / rate the service (optional)


### Provider:
* `POST ~/demand/{id}`: apply to the demand (create an offer)
* `DELETE ~/offer/{id}`: delete the offer
* `PUT ~/service/{id}/cancel`: cancel the service

### Admin:
* `PUT ~/provider/{id}/accept`: accept the provider