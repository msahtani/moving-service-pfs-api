package ma.ensa.movingservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.RateDTO;
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
    public ResponseEntity<?> cancelService(@PathVariable long id) throws Exception{

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


    @PutMapping("/{id}/done")
    public ResponseEntity<?> closeService(
            @PathVariable long id,
            @RequestBody @Valid RateDTO dto
            ) throws Exception{

        final String message;
        final HttpStatus status;

        int code = service.closeService(id, dto);

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
                message = "closed successfully successfully";
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(message, status);

    }

}
