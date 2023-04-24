package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.OfferDTO;
import ma.ensa.movingservice.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService service;

    @GetMapping("/demand/{id}/offer")
    public ResponseEntity<?> getOffers(@PathVariable long id){

        return ResponseEntity
                .ok(service.getOffers(id));
    }

    @PostMapping("/demand/{id}/offer")
    public ResponseEntity<?> addOffer(
            @PathVariable long id,
            @RequestBody OfferDTO dto
            ){

        final String message;
        final HttpStatus status;

        int code = service.addOffer(id, dto.getPrice());

        switch (code){
            case 1 -> {
                message = "provider is not authenticated";
                status = HttpStatus.UNAUTHORIZED;
            }
            case 2 -> {
                message = "demand not found";
                status = HttpStatus.NOT_FOUND;
            }
            case 3 -> {
                message = "you already applied to the offer";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "added successfully";
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(message, status);
    }


    @DeleteMapping("/offer/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable long id){
        final String message;
        final HttpStatus status;

        int code = service.deleteOffer(id);

        switch (code){
            case 1 -> {
                message = "provider is not authenticated";
                status = HttpStatus.UNAUTHORIZED;
            }
            case 2 -> {
                message = "offer not found";
                status = HttpStatus.NOT_FOUND;
            }
            case 3 -> {
                message = "you're not permitted";
                status = HttpStatus.FORBIDDEN;
            }
            case 4 -> {
                message = "forbidden ... it's now a service";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "deleted successfully";
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(message, status);
    }

    @PutMapping("/offer/{id}/accept")
    public ResponseEntity<?> acceptOffer(
            @PathVariable long id
    ){
        final String message;
        final HttpStatus status;

        int code = service.acceptOffer(id);

        switch (code){
            case 1 -> {
                message = "client is not authenticated";
                status = HttpStatus.UNAUTHORIZED;
            }
            case 2 -> {
                message = "offer not found";
                status = HttpStatus.NOT_FOUND;
            }
            case 3 -> {
                message = "you're not permitted";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "accepted successfully";
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(message, status);
    }

}
