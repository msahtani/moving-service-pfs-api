package ma.ensa.movingservice.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.ProviderDTO;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.Vehicle;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.ServiceRepository;
import ma.ensa.movingservice.repositories.user.ProviderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final ServiceRepository serviceRepository;
    
    private final VehicleService vehicleService;

    private boolean canShowPhoneNumber(long providerId){

        User user = Auths.getUser();

        long userId = user.getId();

        if(userId == providerId) return true;

        if(!(user instanceof Client)) return false;

        return serviceRepository.existPendingService(userId, providerId);

    }

    public ProviderDTO getProviderProfile(long providerId){

        Optional<Provider> providerBox = providerRepository.findById(providerId);
        if(providerBox.isEmpty())
            throw new RecordNotFoundException("provider profile not found");
        
        List<Service> doneServices = serviceRepository.findDoneServiceByProvider(providerId);
        List<Vehicle> vehicles = vehicleService.getVehicles(providerId);
        Provider provider = providerBox.get();

        return ProviderDTO.builder()
                .isAccepted(provider.getAcceptedBy() != null)
                .fullName(provider.getFullName())
                .email(provider.getEmail())
                .phoneNumber(
                    canShowPhoneNumber(providerId) ? provider.getPhoneNumber() : null
                )
                .vehicles(vehicles)
                .doneServices(doneServices)
                .build();
    }


    @Transactional
    public List<ProviderDTO> getAllProviders(boolean unacceptedOnly) {

        Auths.getAdmin();

        List<Provider> providers = (unacceptedOnly) ?
                providerRepository.findAllByAcceptedByIsNullAndVehiclesIsNotNull()
                : providerRepository.findAll();

        return providers
                .stream()
                .map(
                        provider -> ProviderDTO.builder()
                                .id(provider.getId())
                                .fullName(provider.getFullName())
                                .email(provider.getEmail())
                                .phoneNumber(provider.getPhoneNumber())
                                .vehicles(provider.getVehicles())
                                .build()
                ).toList();

    }
}
