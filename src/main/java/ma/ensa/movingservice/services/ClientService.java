package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.ClientDto;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.user.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public List<ClientDto> getAllClients(){

        Auths.getAdmin();

        return clientRepository
                .findAll()
                .stream()
                .map(
                        client -> ClientDto.builder()
                                .id(client.getId())
                                .fullName(client.getFullName())
                                .email(client.getEmail())
                                .phoneNumber(client.getPhoneNumber())
                                .build()
                ).toList();
    }

    public ClientDto consultClientProfile(long id){

        User user = Auths.getUser();

        if(user instanceof Client client && client.getId() == id){
            return ClientDto.builder()
                    .id(client.getId())
                    .fullName(client.getFullName())
                    .email(client.getEmail())
                    .phoneNumber(client.getPhoneNumber())
                    .build();
        }

        Client client = clientRepository
                .findById(id)
                .orElseThrow(RecordNotFoundException::new);

        return ClientDto.builder()
                .id(client.getId())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();

    }

    public void deleteClient(long id){

        Auths.getAdmin();

        if(!clientRepository.existsById(id)){
            throw new RecordNotFoundException();
        }

        clientRepository.deleteById(id);

    }

}