package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.UserDTO;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.repositories.user.AdminRepository;
import ma.ensa.movingservice.repositories.user.ProviderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final ProviderRepository providerRepository;
    private final AdminRepository adminRepository;


    private Admin getAdmin(){
        try {
            return (Admin) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }

    public int acceptProvider(long id){
        Admin admin = getAdmin();
        if(admin == null) return 2;
        int rec = providerRepository.acceptProvider(id, admin);
        if(rec == 0) return 1;
        return 0;
    }

    public boolean createAdmin(UserDTO dto){

        Admin admin = getAdmin(), newAdmin;

        if(admin == null || !admin.isSudo()){
            return false;
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

        return true;
    }

    public boolean deleteAdmin(long id){

        Admin admin = getAdmin();
        if(admin == null || !admin.isSudo())
            return false;

        adminRepository.deleteById(id);

        return true;
    }

}
