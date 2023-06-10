package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.VehicleDTO;
import ma.ensa.movingservice.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addVehicle(@ModelAttribute VehicleDTO dto){
        long id = service.addVehicle(dto);
        return String.format("""
                vehicle added successfully
                vehicle id: %d
                """, id
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{imm}")
    public String deleteVehicle(@PathVariable String imm){
        service.deleteVehicle(imm);
        return "vehicle deleted successfully";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/verify")
    public String verifyVehicle(@PathVariable long id){
        service.verifyVehicle(id);
        return "verified";
    }

    /*
    @Deprecated
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{pid}/verifyAll")
    public String verifyAllVehicles(@PathVariable long pid){
        service.verifyAllVehicles(pid);
        return "verified";
    }

     */


}
