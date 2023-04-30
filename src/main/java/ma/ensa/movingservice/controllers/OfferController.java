package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.OfferDTO;
import ma.ensa.movingservice.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/demand/{id}/offer")
    public List<OfferDTO> getOffers(@PathVariable long id){
        return service.getOffers(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/demand/{id}/offer")
    public String addOffer (
            @PathVariable long id,
            @RequestBody OfferDTO dto
    ) throws Exception{
        service.addOffer(id, dto.getPrice());
        return "added successfully";
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/offer/{id}")
    public String deleteOffer(@PathVariable long id) throws Exception{
        service.deleteOffer(id);
        return "deleted successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/offer/{id}/accept")
    public String acceptOffer(
            @PathVariable long id
    ) throws Exception{
        service.acceptOffer(id);
        return "accepted successfully";
    }

}
