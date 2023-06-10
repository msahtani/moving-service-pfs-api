package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.OfferDTO;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.models.Offer;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.repositories.DemandRepository;
import ma.ensa.movingservice.repositories.OfferRepository;
import ma.ensa.movingservice.repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class OfferService {

    private final DemandRepository demandRepository;
    private final OfferRepository offerRepository;

    private final ServiceRepository serviceRepository;

    public List<OfferDTO> getOffers(long demandId){
        return offerRepository
                .findByDemandId(demandId)
                .stream()
                .map(offer -> OfferDTO
                        .builder()
                        .id(offer.getId())
                        .providerId(offer.getProvider().getId())
                        .providerName(offer.getProvider().getFullName())
                        .price(offer.getPrice())
                        .build()
                ).toList();
    }

    public void addOffer(long demandId, OfferDTO dto){

        // get the provider
        Provider provider = Auths.getProvider();

        // get the demand
        Demand demand = demandRepository
                .findById(demandId)
                .orElseThrow(RecordNotFoundException::new);


        // check if the provider already applied
        if(offerRepository.findByProvider(provider) != 0)
            throw new PermissionException("you are already applied");


        Offer offer = Offer.builder()
                .provider(provider)
                .demand(demand)
                .price(dto.getPrice() != 0 ? dto.getPrice() : demand.getProposedPrice())
                .description(dto.getDescription())
                .build();

        offerRepository.save(offer);
    }

    public void deleteOffer(long id){

        // check the provider authentication
        Provider provider = Auths.getProvider();

        // check the existence of the offer
        Optional<Offer> offerBox = offerRepository.findById(id);
        if(offerBox.isEmpty())
            throw new RecordNotFoundException("offer not found");

        // check the provider has the permission to delete its offer
        Offer offer = offerBox.get();
        if(offer.getProvider().getId() != provider.getId())
            throw new PermissionException();


        if(offer.getService() != null)
            throw new PermissionException("forbidden ... it become a service");

        offerRepository.delete(offer);

    }

    public void acceptOffer(long id) throws Exception{

        // check the client authentication
        Client client = Auths.getClient();

        // check the existence of the offer
        Optional<Offer> offerBox = offerRepository.findById(id);
        if(offerBox.isEmpty())
            throw new RecordNotFoundException("offer not found");

        // check the permission
        Offer offer = offerBox.get();
        if(offer.getDemand().getClient().getId() != client.getId())
            throw new PermissionException("you are not permitted");

        // TODO: DateTime implementation
        Service service = Service.builder()
                .sTime("2023/05/01 14h00")
                .eTime("2023/05/01 17h00")
                .status(ServiceStatus.PENDING)
                .offer(offer)
                .build();

        serviceRepository.save(service);
        offerRepository.setServiceById(offer.getId(), service);

    }

}
