package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService service;

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelService(@PathVariable long id){

        String message;
        HttpStatus status;

        int code = service.cancelService(id);

        switch (code){
            case 1 -> {
                message = "authentication required";
                status = HttpStatus.FORBIDDEN;
            }
            case 2 -> {
                message = "service not found";
                status = HttpStatus.NOT_FOUND;
            }
            case 3 | 4 -> {
                message = "you are not permitted";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "cancelled successfully";
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(message, status);
    }


}
