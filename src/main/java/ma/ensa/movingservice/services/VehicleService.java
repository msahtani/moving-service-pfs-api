package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.VehicleDTO;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Vehicle;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final ImageService imageService;

    public long addVehicle(VehicleDTO dto){

        Provider provider = Auths.getProvider(true);

        String imageName = dto.getImage() != null ?
                imageService.handleImageUpload(dto.getImage())
                : null;


        Vehicle vehicle = Vehicle.builder()
                .brand(dto.getBrand())
                .imm(dto.getImm())
                .model(dto.getModel())
                .provider(provider)
                .imageName(imageName)
                .build();

        return vehicleRepository
                .save(vehicle)
                .getId();
    }


    public void deleteVehicle(String imm){

        Provider provider = Auths.getProvider();

        Vehicle vehicle = vehicleRepository
                .findById(imm)
                .orElseThrow(RecordNotFoundException::new);


        if(vehicle.getProvider().getId() != provider.getId())
            throw new PermissionException();

        // delete the image associated with the vehicle given by id
        Optional<String> imageName = vehicleRepository.findImageName(imm);
        imageName.ifPresent(imageService::deleteImage);

        // delete the vehicle
        vehicleRepository.deleteById(imm);
    }


    public void verifyVehicle(long id){
        Admin admin = Auths.getAdmin();
        vehicleRepository.verifyVehicle(admin.getId(), id);
    }

    public void verifyAllVehicles(long providerId){
        vehicleRepository.verifyAllVehicles(
            Auths.getAdmin().getId(), providerId
        );
    }

    public List<Vehicle> getVehicles(long providerId){
        Auths.getUser();
        return vehicleRepository.getAllByProvider(providerId);
    }

}
