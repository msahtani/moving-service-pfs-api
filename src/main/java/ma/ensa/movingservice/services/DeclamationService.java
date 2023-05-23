package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DeclamationDto;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Declamation;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.DeclamationRepository;
import ma.ensa.movingservice.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DeclamationService {

    private final DeclamationRepository declamationRepository;

    private final UserRepository userRepository;

    public void addDeclamation(DeclamationDto dto) throws Exception{

        // get the declaimer
        User declaimer = Auths.getUser();

        // get the declaimed
        Optional<User> _declaimed =  userRepository.findById(dto.getDeclaimedId());
        if(_declaimed.isEmpty()){
            throw new RecordNotFoundException("declaimed not found");
        }
        User declaimed = _declaimed.get();

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

    public void closeDeclamation(long id) throws Exception{

        // get the admin
        Admin admin = Auths.getAdmin();

        // check the existence
        if(!declamationRepository.existsById(id)){
            throw new RecordNotFoundException("declamation not found");
        }

        declamationRepository.close(id, admin);

    }

    public void findAll(){

    }

}
