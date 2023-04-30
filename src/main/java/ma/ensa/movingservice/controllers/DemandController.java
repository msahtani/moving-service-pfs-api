package ma.ensa.movingservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DemandDTO;
import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.services.DemandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demand")
@RequiredArgsConstructor
public class DemandController {

    private final DemandService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<DemandDTO> getDemandsForProvider() throws Exception{
        return service.findForProvider();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Demand getDemand(@PathVariable long id) throws Exception{
        return service.getDemand(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addDemand(@RequestBody DemandDTO dto) throws Exception{
        service.addDemand(dto);
        return "saved successfully";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String editDemand(
            @PathVariable long id,
            @RequestBody DemandDTO dto
    ) throws Exception{
        service.editDemand(id, dto);
        return "updated successfully";
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public String deleteDemand(
            @PathVariable long id
    ) throws Exception{
        service.deleteDemand(id);
        return "deleted successfully";
    }

}