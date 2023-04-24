package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.config.JwtService;
import ma.ensa.movingservice.dto.auth.AuthRequest;
import ma.ensa.movingservice.dto.auth.AuthResponse;
import ma.ensa.movingservice.dto.auth.RegisterRequest;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    public final  AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public Optional<AuthResponse> register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail()))
            return Optional.empty();


        User user;
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if(request.isProvider()){
            user = Provider.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
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

        return Optional.of(
            new AuthResponse(
                jwtService.generateToken(user), user.getClass().getSimpleName()
            )
        );
    }

    public Optional<AuthResponse> authenticate(
            AuthRequest request
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        return user.map(_user ->
            new AuthResponse(
                jwtService.generateToken(_user),
                user.get().getClass().getSimpleName()
            )
        );

    }

}