package ma.ensa.moving.service;

import lombok.RequiredArgsConstructor;

import ma.ensa.moving.config.JwtService;

import ma.ensa.moving.dto.auth.AuthRequest;
import ma.ensa.moving.dto.auth.AuthResponse;
import ma.ensa.moving.dto.auth.RegisterRequest;
import ma.ensa.moving.enums.UserType;
import ma.ensa.moving.models.user.Client;
import ma.ensa.moving.models.user.Provider;
import ma.ensa.moving.models.user.User;
import ma.ensa.moving.repositories.user.UserRepository;
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