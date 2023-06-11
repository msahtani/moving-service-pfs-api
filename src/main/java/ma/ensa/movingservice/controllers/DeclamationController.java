package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DeclamationDto;
import ma.ensa.movingservice.services.DeclamationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/declamation")
@RequiredArgsConstructor
public class DeclamationController {

    private final DeclamationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addDeclamation(@RequestBody DeclamationDto dto){
        service.addDeclamation(dto);
        return "the declamation is sent to review by the administrator";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DeclamationDto> findAll(){
        return service.findAllOpenDeclamations();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String closeDeclamation(@PathVariable long id){
        service.closeDeclamation(id);
        return "closed successfully";
    }

}