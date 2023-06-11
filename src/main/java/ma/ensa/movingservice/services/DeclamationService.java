package ma.ensa.movingservice.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DeclamationDto;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Declamation;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.DeclamationRepository;
import ma.ensa.movingservice.repositories.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeclamationService {

    private final DeclamationRepository declamationRepository;

    private final UserRepository userRepository;

    private DateTimeFormatter dateTimeFormatter;

    @PostConstruct
    public void init(){
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    }

    public void addDeclamation(@NotNull DeclamationDto dto){

        // get the declaimer
        User declaimer = Auths.getUser();

        // get the declaimed
        User declaimed =  userRepository
                .findById(dto.getDeclaimedId())
                .orElseThrow(RecordNotFoundException::new);

        // check the ability
        if(declaimed.getClass().equals(declaimer.getClass()))
            throw new PermissionException("you can't declaim the user of the same type of you");

        // create the instance and save it
        Declamation declamation = Declamation.builder()
                .declaimer(declaimer)
                .declaimed(declaimed)
                .description(dto.getDescription())
                .build();

        declamationRepository.save(declamation);
    }

    public void closeDeclamation(long id){

        // get the admin
        Admin admin = Auths.getAdmin();

        // check the existence
        if(!declamationRepository.existsById(id)){
            throw new RecordNotFoundException("declamation not found");
        }

        declamationRepository.close(id, admin);

    }

    public List<DeclamationDto> findAllOpenDeclamations(){
        // requires admin auth
        Auths.getAdmin();

        return declamationRepository
                .findAllOpenDeclamations()
                .stream()
                .map(
                    d -> DeclamationDto.builder()
                        .id(d.getId())
                        .declaimedName(d.getDeclaimed().getFullName())
                        .declaimerName(d.getDeclaimer().getFullName())
                        .declaimedId(d.getDeclaimed().getId())
                        .description(d.getDescription())
                        .createdAt(
                                dateTimeFormatter.format(d.getCreatedAt())
                        )
                        .build()
                ).toList();
    }

}