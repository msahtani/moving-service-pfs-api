package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.UserDTO;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.repositories.user.AdminRepository;
import ma.ensa.movingservice.repositories.user.ProviderRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final ProviderRepository providerRepository;
    private final AdminRepository adminRepository;
    private final VehicleService vehicleService;

    public void acceptProvider(long id){
        Admin admin = Auths.getAdmin();

        if(!providerRepository.existsById(id)){
            throw new RecordNotFoundException();
        }

        if(providerRepository.isVerified(id) != 0){
            throw new PermissionException("the provider is already verified");
        }

        providerRepository.acceptProvider(admin.getId(), id);

        vehicleService.verifyAllVehicles(id);
    }

    public void createAdmin(UserDTO dto) {

        Admin admin = Auths.getAdmin(), newAdmin;

        if (admin.notSudo()) {
            throw new PermissionException(
                    "forbidden ... you must be sudo admin "
            );
        }


        newAdmin = Admin.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(
                        passwordEncoder.encode(dto.getPassword())
                )
                .build();

        adminRepository.save(newAdmin);
    }

    public void deleteAdmin(long id) {

        Admin admin = Auths.getAdmin();
        if (admin.notSudo())
            throw new PermissionException(
                    "forbidden ... you must be sudo admin "
            );

        if (id == 1L) {
            throw new PermissionException("you cannot delete yourself");
        }
        ;

        adminRepository.deleteById(id);

    }

}