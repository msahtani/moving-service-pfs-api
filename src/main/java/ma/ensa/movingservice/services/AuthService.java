package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.config.JwtService;
import ma.ensa.movingservice.dto.auth.AuthRequest;
import ma.ensa.movingservice.dto.auth.AuthResponse;
import ma.ensa.movingservice.dto.auth.RegisterRequest;
import ma.ensa.movingservice.exceptions.EmailNotAvailableException;
import ma.ensa.movingservice.features.mail.Email;
import ma.ensa.movingservice.features.mail.EmailService;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    public final  AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public AuthResponse register(RegisterRequest request) throws Exception{

        if(userRepository.existsByEmail(request.getEmail()))
            throw new EmailNotAvailableException();


        User user;
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if(request.getCity() != null){
            user = Provider.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .phoneNumber(request.getPhoneNumber())
                    .city(request.getCity())
                    .build();
        }
        else{
            user = Client.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .phoneNumber(request.getPhoneNumber())
                    .build();
        }

        userRepository.save(user);


        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .userType(user.getClass().getSimpleName())
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) throws AuthenticationException {

        User user = (User) userService.loadUserByUsername(
                request.getEmail()
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        // send email

        Email email = Email.builder()
                .subject("just a test")
                .recipient(request.getEmail())
                .msgBody("hi hitler bitch")
                .build();



        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .userType(user.getClass().getSimpleName())
                .build();
    }

}