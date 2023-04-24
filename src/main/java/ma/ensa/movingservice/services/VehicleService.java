package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.VehicleDTO;
import ma.ensa.movingservice.models.Vehicle;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public int addVehicle(VehicleDTO dto){

        Optional<Provider> provider = Auths.getProvider();

        if(provider.isEmpty()) return 1;

        Vehicle vehicle = Vehicle.builder()
                .brand(dto.getBrand())
                .imm(dto.getImm())
                .model(dto.getModel())
                .provider(provider.get())
                .build();

        vehicleRepository.save(vehicle);

        return 0;
    }

    /*
        0 --> deleted successfully
        1 --> you're not authenticated
        2 --> vehicle not found
        3 --> you're not permitted to delete
     */
    public int deleteVehicle(String imm){

        Optional<Provider> provider = Auths.getProvider();

        if(provider.isEmpty())
            return 1;

        Optional<Vehicle> vehicle = vehicleRepository.findById(imm);

        if(vehicle.isEmpty())
            return 2;

        if(vehicle.get().getProvider().getId() != provider.get().getId())
            return 3;

        vehicleRepository.deleteById(imm);

        return 0;
    }


    public int verifyVehicle(String imm){

        Optional<Admin> admin = Auths.getAdmin();
        if(admin.isEmpty()) return 1;

        vehicleRepository.verifyVehicle(imm, admin.get());

        return 0;
    }

    public int verifyAllVehicles(long providerId){

        Optional<Admin> admin = Auths.getAdmin();
        if(admin.isEmpty()) return 1;

        vehicleRepository.verifyAllVehicles(providerId, admin.get());

        return 0;
    }

    public List<Vehicle> getVehicles(long providerId){
        return vehicleRepository.getAllByProvider(providerId);
    }


}
