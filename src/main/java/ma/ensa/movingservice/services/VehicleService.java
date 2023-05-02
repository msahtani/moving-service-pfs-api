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

    public void addVehicle(VehicleDTO dto) throws Exception{

        Provider provider = Auths.getProvider();

        String imageName = null;
        if(dto.getImage() != null){
            imageName = imageService.handleImageUpload(dto.getImage());
        }

        Vehicle vehicle = Vehicle.builder()
                .brand(dto.getBrand())
                .imm(dto.getImm())
                .model(dto.getModel())
                .provider(provider)
                .imageName(imageName)
                .build();

        vehicleRepository.save(vehicle);
    }


    public void deleteVehicle(String imm) throws Exception{

        Provider provider = Auths.getProvider();

        Optional<Vehicle> vehicle = vehicleRepository.findById(imm);

        if(vehicle.isEmpty())
            throw new RecordNotFoundException("vehicle not found");

        if(vehicle.get().getProvider().getId() != provider.getId())
            throw new PermissionException();

        // delete the image associated with the vehicle given by id
        Optional<String> imageName = vehicleRepository.findImageName(imm);
        imageName.ifPresent(imageService::deleteImage);

        // delete the vehicle
        vehicleRepository.deleteById(imm);
    }


    public void verifyVehicle(String imm) throws Exception{
        Admin admin = Auths.getAdmin();
        vehicleRepository.verifyVehicle(imm, admin);
    }

    public void verifyAllVehicles(long providerId) throws Exception{
        vehicleRepository.verifyAllVehicles(
            providerId,
            Auths.getAdmin()
        );
    }

    public List<Vehicle> getVehicles(long providerId) throws Exception{
        Auths.getUser();
        return vehicleRepository.getAllByProvider(providerId);
    }

}
