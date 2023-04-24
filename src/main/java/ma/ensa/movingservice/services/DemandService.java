package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DemandDTO;
import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.repositories.DemandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;

    public Optional<Demand> getDemand(long id){
        return demandRepository.findById(id);
    }


    public List<DemandDTO> findForProvider(){

        Optional<Provider> provider = Auths.getProvider();

        if(provider.isEmpty())
            return Collections.emptyList();

        String city = provider.get().getCity();

        return demandRepository
                .findForProvider(city)
                .stream().map(demand ->
                    DemandDTO.builder()
                        .clientName(demand.getClient().getFullName())
                        .title(demand.getTitle())
                        .description(demand.getDescription())
                        .from(demand.getSCity())
                        .to(demand.getDCity())
                        .build()
                ).toList();
    }

    public List<Demand> findClientDemands(){

        // TODO: complete this
        Optional<Client> client = Auths.getClient();

        if(client.isEmpty())
            return Collections.emptyList();

        return demandRepository.findAllByClient(client.get());

    }

    public boolean addDemand(DemandDTO dto){
        Optional<Client> client = Auths.getClient();

        if(client.isEmpty()){
            return false;
        }

        System.out.println(dto);

        Demand demand = Demand.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .sCity(dto.getFrom())
                .dCity(dto.getTo())
                .client(client.get())
                .createdAt(LocalDateTime.now())
                .build();

        demandRepository.save(demand);

        return true;
    }

    public int editDemand(long id, DemandDTO dto){

        Optional<Client> client = Auths.getClient();

        if(client.isEmpty()){
            return 3;
        }

        Optional<Demand> demandBox = demandRepository.findById(id);

        if(demandBox.isEmpty())
            return 2;

        Demand demand = demandBox.get();

        if(client.get().getId() != demand.getClient().getId())
            return 1;

        demand.setTitle(dto.getTitle());
        demand.setDescription(dto.getDescription());
        demand.setSCity(dto.getFrom());
        demand.setDCity(dto.getTo());

        demandRepository.save(demand);

        return 0;
    }

    public int deleteDemand(long id){

        Optional<Client> client = Auths.getClient();

        if(client.isEmpty())
            return 3;

        Optional<Demand> demandBox = demandRepository.findById(id);

        if(demandBox.isEmpty())
            return 2;

        Demand demand = demandBox.get();

        if(client.get().getId() != demand.getClient().getId())
            return 1;

        demandRepository.delete(demand);

        return 0;
    }

}
