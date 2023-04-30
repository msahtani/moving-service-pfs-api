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


    public void acceptProvider(long id) throws Exception{
        Admin admin = Auths.getAdmin();
        int rec = providerRepository.acceptProvider(id, admin.getId());
        if(rec == 0)
            throw new RecordNotFoundException("provider not found");
    }

    public void createAdmin(UserDTO dto) throws Exception{

        Admin admin = Auths.getAdmin(), newAdmin;

        if(!admin.isSudo()){
            throw new PermissionException(
                    "forbidden ... you must be sudo admin "
            );
        }

        String encodedPassword =
                passwordEncoder.encode(dto.getPassword());

        newAdmin = Admin.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(encodedPassword)
                .sudo(false)
                .build();

        adminRepository.save(newAdmin);

    }

    public void deleteAdmin(long id) throws Exception{

        Admin admin = Auths.getAdmin();
        if(admin.isSudo())
            throw new PermissionException(
                    "forbidden ... you must be sudo admin "
            );

        adminRepository.deleteById(id);


    }

}
