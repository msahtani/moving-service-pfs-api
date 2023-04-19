package ma.ensa.moving.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.moving.dto.DemandDTO;
import ma.ensa.moving.service.DemandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demand")
@RequiredArgsConstructor
public class DemandController {

    private final DemandService service;

    @GetMapping
    public ResponseEntity<?> getDemandsForProvider(){
        return ResponseEntity
                .ok(service.findForProvider());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDemand(@PathVariable long id){
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addDemand(
            @RequestBody DemandDTO dto
    ){

        if(!service.addDemand(dto)){
            return new ResponseEntity<>(
                    "something went wrong",
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                "saved successfully",
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editDemand(
            @PathVariable long id,
            @RequestBody DemandDTO dto
    ){

        String message;
        HttpStatus status;

        int code = service.editDemand(id, dto);

        switch (code) {
            case 0 -> {
                message = "updated successfully";
                status = HttpStatus.OK;
            }
            case 1 -> {
                message = "this demand does not belong to you";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "this demand is not found";
                status = HttpStatus.NOT_FOUND;
            }
        }

        return new ResponseEntity<>(message, status);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDemand(
            @PathVariable long id
    ){

        String message;
        HttpStatus status;

        int code = service.deleteDemand(id);

        switch (code) {
            case 0 -> {
                message = "deleted successfully";
                status = HttpStatus.OK;
            }
            case 1 -> {
                message = "this demand does not belong to you";
                status = HttpStatus.FORBIDDEN;
            }
            default -> {
                message = "this demand is not found";
                status = HttpStatus.NOT_FOUND;
            }
        }

        return new ResponseEntity<>(message, status);
    }

}