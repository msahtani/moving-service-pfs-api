package ma.ensa.movingservice.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.ServiceRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public int cancelService(long id){

        // check authentication
        Optional<User> userBox = Auths.getUser();
        if(userBox.isEmpty()) return 1;

        // check the existence of the service
        Optional<Service> service = serviceRepository.findById(id);
        if(service.isEmpty()) return 2;

        // client or the provider has the right to cancel the service
        User user = userBox.get();
        if(user instanceof Client){
            // he's the client
            Client client = service.get().getOffer().getDemand().getClient();
            if(client.getId() != user.getId())
                return 3;

        }else{
            // he's the provider
            Provider provider = service.get().getOffer().getProvider();
            if(provider.getId() != user.getId()){
                return 4;
            }
        }

        serviceRepository.setStatus(id, ServiceStatus.CANCELED);

        return 0;
    }

}
