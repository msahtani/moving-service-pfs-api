package ma.ensa.movingservice;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.repositories.user.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class MovingServiceApp implements CommandLineRunner {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MovingServiceApp.class, args);
    }


    @Override
    public void run(String... args) throws Exception {


        if(adminRepository.count() != 0) return;

        Admin admin = Admin.builder()
                .fullName("admin")
                .email("admin@movingservice.ma")
                .password( passwordEncoder.encode("12345678"))
                .sudo(true)
                .phoneNumber("+212704261627")
                .build();

        adminRepository.save(admin);


    }
}
