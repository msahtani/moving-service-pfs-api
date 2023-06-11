package ma.ensa.movingservice.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.RateDTO;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.exceptions.PermissionException;
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

    public void cancelService(long id) throws Exception{

        // check authentication
        User user = Auths.getUser();

        // check the existence of the service
        Service service = serviceRepository
                .findById(id)
                .orElseThrow(RecordNotFoundException::new);

        // client or the provider has the right to cancel the service

        if(user instanceof Client){
            // he's the client
            Client client = service.getOffer().getDemand().getClient();
            if(client.getId() != user.getId())
                throw new PermissionException();

        }else{
            // he's the provider
            Provider provider = service.getOffer().getProvider();
            if(provider.getId() != user.getId()){
                throw new PermissionException();
            }
        }

        serviceRepository.setStatus(id, ServiceStatus.CANCELED);

    }

    public void closeService(long id, RateDTO dto){
        // check authentication
        Client client = Auths.getClient();

        // check the existence of the service
        Service service = serviceRepository
                .findById(id)
                .orElseThrow(RecordNotFoundException::new);

        if(service.getStatus() == ServiceStatus.DONE){
            throw new PermissionException("this service is already closed by you");
        }


        // client or the provider has the right to cancel the service
        Client owner = service.getOffer().getDemand().getClient();
        if(client.getId() != owner.getId())
            throw new PermissionException();

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
    }

}
