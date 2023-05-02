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
    public String addVehicle(@ModelAttribute VehicleDTO dto) throws Exception{
        service.addVehicle(dto);
        return "vehicle added successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{imm}")
    public String deleteVehicle(@PathVariable String imm) throws Exception{
        service.deleteVehicle(imm);
        return "vehicle deleted successfully";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{imm}/verify")
    public String verifyVehicle(@PathVariable String imm) throws Exception{
        service.verifyVehicle(imm);
        return "verified";
    }

    @Deprecated
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{pid}/verifyAll")
    public String verifyVehicle(@PathVariable long pid) throws Exception{
        service.verifyAllVehicles(pid);
        return "verified";
    }


}
