package ma.ensa.movingservice.controllers;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptProvider(@PathVariable long id){

        String message;
        HttpStatus status;

        switch (service.acceptProvider(id)){
            case 2 -> {
                message = "you're not permitted";
                status = HttpStatus.FORBIDDEN;
            }
            case 1 -> {
                message = "provider not found";
                status = HttpStatus.NOT_FOUND;
            }
            default -> {
                message = "accepted successfully";
                status = HttpStatus.ACCEPTED;
            }
        }

        return new ResponseEntity<>(message, status);

    }



}
