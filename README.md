# moving-service-pfs


## APIs
api prefix: `http://localhost:8080/api/v1` (noted as ~)
### Authentication
* `POST ~/auth/login`: login 
* `POST ~/auth/signup`: signup
* output : the JWT token and the user type

### Client:
* `GET ~/demand`: get all the demand of the client (*)
* `POST ~/demand/`: create a demand
* `PUT ~/demand/{id}`: edit the demand given by id
* `DELETE ~/demand/{id}`: delete the demand given by id
* `GET ~/demand/{id}/offers`: get all the offers of the demand given by id
* `POST ~/offer/{id}/accept`: accept the offer given by id (it will create a service object related to it)
* `PUT ~/service/{id}/close`: close the service / rate the service (optional)


### Provider:
* `POST ~/demand/{id}`: apply to the demand (create an offer)
* `DELETE ~/offer/{id}`: delete the offer
* `POST ~/vehicle/`: add a vehicle
* `DELETE ~/vehicle/{id}`: delete the vehicle given by id



### Common (Client and Provider)
* `PUT ~/service/{id}/cancel`: cancel the service
* `GET ~/provider/{id}`: consult the provider profile (just for the clients and the provider itself)
* `POST ~/declamation`: add a declamation specifying the __declaimedID__ and __description__ as JSON attributes 

### Admin:
* `PUT ~/provider/{id}/accept`: accept the provider and verify all the vehicles
* `PUT ~/vehicle/{id}/verify`: verify the vehicle given by id
* `POST ~/admin/`: create an admin (by the super-admin (sudo = true) )
* `DELETE ~/admin/{id}`: delete the admin given by id (by the super-admin (sudo = true) )
* `GET ~/declamation`: get all open declamations
* `PUT ~/declamation/{id}`: close the declamation given by id