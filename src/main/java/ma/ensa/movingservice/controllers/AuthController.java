package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.auth.AuthRequest;
import ma.ensa.movingservice.dto.auth.AuthResponse;
import ma.ensa.movingservice.dto.auth.RegisterRequest;
import ma.ensa.movingservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthRequest request
    ){
        // get the token
        Optional<AuthResponse> res = service.authenticate(request);

        if(res.isEmpty()){
            return new ResponseEntity<>(
                    "Whoops credentials, please verify and try again",
                    HttpStatus.NOT_FOUND
            );
        }

        return  new ResponseEntity<>(
                new AuthResponse(res.get().getToken(), res.get().getUserType()),
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(
            @RequestBody RegisterRequest request
    ){
        // get the token
        Optional<AuthResponse> res = service.register(request);

        if(res.isEmpty()){
            return new ResponseEntity<>(
                    "the user is already exist",
                    HttpStatus.FORBIDDEN
            );
        }

        return  new ResponseEntity<>(
                new AuthResponse(res.get().getToken(), res.get().getUserType()),
                HttpStatus.ACCEPTED
        );
    }


}
