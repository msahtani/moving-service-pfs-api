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

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/cancel")
    public String cancelService(@PathVariable long id) throws Exception{
        service.cancelService(id);
        return "cancelled successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/close")
    public String closeService(
            @PathVariable long id,
            @RequestBody @Valid RateDTO dto
    ) throws Exception{
        service.closeService(id, dto);
        return "closed successfully";
    }

}
