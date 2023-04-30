package ma.ensa.movingservice.controllers;


import lombok.RequiredArgsConstructor;
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


    @GetMapping("/{id}")
    public ResponseEntity<?> consultProfile(
            @PathVariable("id") long id
    ) throws Exception{
        return providerService.getProviderProfile(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                    () -> ResponseEntity.notFound().build()
                );
    }

    @PutMapping ("/{id}/accept")
    public ResponseEntity<?> acceptProvider(@PathVariable long id) throws Exception{


        adminService.acceptProvider(id);

        return new ResponseEntity<>(
                "provider is accepted successfully",
                HttpStatus.ACCEPTED
        );
    }







}
