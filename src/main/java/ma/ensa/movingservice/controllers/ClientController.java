package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.ClientDto;
import ma.ensa.movingservice.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClients(){
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClient(@PathVariable long id){
        return service.consultClientProfile(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteClient(@PathVariable long id){
        service.deleteClient(id);
        return "deleted successfully";
    }

}