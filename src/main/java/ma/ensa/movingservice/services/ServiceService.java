package ma.ensa.movingservice.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.RateDTO;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Rate;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.RateRepository;
import ma.ensa.movingservice.repositories.ServiceRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final RateRepository rateRepository;

    public int cancelService(long id) throws Exception{

        // check authentication
        User user = Auths.getUser();


        // check the existence of the service
        Optional<Service> service = serviceRepository.findById(id);
        if(service.isEmpty()) throw new RecordNotFoundException(
                "service not found"
        );

        // client or the provider has the right to cancel the service

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

    public int closeService(long id, RateDTO dto) throws Exception{
        // check authentication
        Client client = Auths.getClient();

        // check the existence of the service
        Optional<Service> service = serviceRepository.findById(id);
        if(service.isEmpty()) return 2;

        // client or the provider has the right to cancel the service
        Client owner = service.get().getOffer().getDemand().getClient();
        if(client.getId() != owner.getId())
            return 3;

        // set the rate
        if(dto.getRate() != 0){
            Rate rate = Rate.builder()
                    .rate(dto.getRate())
                    .comment(dto.getComment())
                    .build();
            rateRepository.save(rate);
            serviceRepository.setRate(id, rate);
        }

        // mark service as DONE
        serviceRepository.setStatus(id, ServiceStatus.DONE);


        return 0;
    }

}
