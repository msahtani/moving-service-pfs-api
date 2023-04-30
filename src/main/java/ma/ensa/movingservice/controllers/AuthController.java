package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.auth.AuthRequest;
import ma.ensa.movingservice.dto.auth.AuthResponse;
import ma.ensa.movingservice.dto.auth.RegisterRequest;
import ma.ensa.movingservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(
            @RequestBody AuthRequest request
    ){
        return service.authenticate(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signup(
            @RequestBody RegisterRequest request
    ) throws Exception{

        return service.register(request);
    }

}
