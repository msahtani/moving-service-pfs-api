package ma.ensa.movingservice.controllers;


import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.UserDTO;
import ma.ensa.movingservice.dto.auth.RegisterRequest;
import ma.ensa.movingservice.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createAdmin(@RequestBody UserDTO dto) throws Exception{
        service.createAdmin(dto);
        return "admin created successfully";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable long id) throws Exception{
        service.deleteAdmin(id);
        return "admin deleted successfully";
    }
}
