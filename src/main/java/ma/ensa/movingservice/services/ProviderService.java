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

    private boolean canShowPhoneNumber(long providerId){

        User user = Auths.getUser();

        long userId = user.getId();

        if(userId == providerId) return true;

        if(!(user instanceof Client)) return false;

        return serviceRepository.existPendingService(userId, providerId);

    }

    public ProviderDTO getProviderProfile(long providerId){

        Provider provider = providerRepository
                .findById(providerId)
                .orElseThrow(RecordNotFoundException::new);

        return ProviderDTO.builder()
                .fullName(provider.getFullName())
                .email(provider.getEmail())
                .phoneNumber(
                    canShowPhoneNumber(providerId) ? provider.getPhoneNumber() : null
                )
                .doneServicesCount(
                        serviceRepository.countAllDoneServicesByProvider(providerId)
                )
                .averageRate(
                        serviceRepository
                                .getAvgRatingByProvider(provider.getId())
                                .stream().mapToDouble(d -> d)
                                .average()
                                .orElse(.0)
                )
                .build();
    }


    @Transactional
    public List<ProviderDTO> getAllProviders(boolean unacceptedOnly) {

        Auths.getAdmin();

        List<Provider> providers = (unacceptedOnly) ?
                providerRepository.findAllByAcceptedByIsNull()
                : providerRepository.findAll();

        return providers
                .stream()
                .map(
                        provider -> ProviderDTO.builder()
                                .id(provider.getId())
                                .fullName(provider.getFullName())
                                .email(provider.getEmail())
                                .phoneNumber(provider.getPhoneNumber())
                                .doneServicesCount(
                                        serviceRepository.countAllDoneServicesByProvider(provider.getId())
                                )
                                .averageRate(
                                        serviceRepository
                                                .getAvgRatingByProvider(provider.getId())
                                                .stream()
                                                .mapToDouble(d -> d)
                                                .average()
                                                .orElse(.0)
                                )
                                .build()
                ).toList();

    }
}
