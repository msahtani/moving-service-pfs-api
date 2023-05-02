package ma.ensa.movingservice.controllers;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.ProviderDTO;
import ma.ensa.movingservice.services.AdminService;
import ma.ensa.movingservice.services.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final AdminService adminService;
    private final ProviderService providerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProviderDTO consultProfile(@PathVariable("id") long id) throws Exception{
        return providerService.getProviderProfile(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping ("/{id}/accept")
    public String acceptProvider(@PathVariable long id) throws Exception{
        adminService.acceptProvider(id);
        return "provider is accepted successfully";
    }

}
