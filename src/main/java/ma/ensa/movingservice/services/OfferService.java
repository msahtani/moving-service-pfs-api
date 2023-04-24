package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.OfferDTO;
import ma.ensa.movingservice.enums.ServiceStatus;
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
                        .providerId(offer.getProvider().getId())
                        .providerName(offer.getProvider().getFullName())

                        .price(offer.getPrice())
                        .build()
                ).toList();
    }

    public int addOffer(long demandId, double price){

        // get the provider
        Optional<Provider> provider = Auths.getProvider();

        if(provider.isEmpty()) return 1;

        // get the demand
        Optional<Demand> demand = demandRepository.findById(demandId);
        if(demand.isEmpty())
            return 2;

        // check if the provider already applied
        if(offerRepository.findByProvider(provider.get()) != 0)
            return 3;


        Offer offer = Offer.builder()
                .provider(provider.get())
                .demand(demand.get())
                .price(price)
                .build();

        offerRepository.save(offer);

        return 0;
    }

    public int deleteOffer(long id){

        // check the provider authentication
        Optional<Provider> providerBox = Auths.getProvider();
        if(providerBox.isEmpty()) return 1;

        // check the existence of the offer
        Optional<Offer> offerBox = offerRepository.findById(id);
        if(offerBox.isEmpty()) return 2;

        // check the provider has the permission to delete its offer
        Provider provider = providerBox.get();
        Offer offer = offerBox.get();

        if(offer.getProvider().getId() != provider.getId())
            return 3;


        if(offer.getService() != null)
            return 4;

        offerRepository.delete(offer);

        return 0;
    }

    public int acceptOffer(long id){

        // check the client authentication
        Optional<Client> clientBox = Auths.getClient();
        if(clientBox.isEmpty())
            return 1;

        // check the existence of the offer
        Optional<Offer> offerBox = offerRepository.findById(id);
        if(offerBox.isEmpty())
            return 2;

        // check the permission
        Offer offer = offerBox.get();
        if(offer.getDemand().getClient().getId() != clientBox.get().getId())
            return 3;



        Service service = Service.builder()
                .sTime("2023/05/01 14h00")
                .eTime("2023/05/01 17h00")
                .status(ServiceStatus.PENDING)
                .offer(offer)
                .build();

        serviceRepository.save(service);
        offerRepository.setServiceById(offer.getId(), service);

        return 0;
    }

}
