package ma.ensa.moving.service;

import lombok.RequiredArgsConstructor;
import ma.ensa.moving.dto.DemandDTO;
import ma.ensa.moving.models.Demand;
import ma.ensa.moving.models.user.Client;
import ma.ensa.moving.models.user.Provider;
import ma.ensa.moving.repositories.DemandRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;

    private Optional<Client> getClient(){
        try{
            return Optional.of(
                (Client) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }

    private Optional<Provider> getProvider(){
        try{
            return Optional.of(
                    (Provider) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal()
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Demand> findForProvider(){

        Optional<Provider> provider = getProvider();

        if(provider.isEmpty())
            return Collections.emptyList();

        String city = provider.get().getCity();

        return demandRepository.findForProvider(city);
    }

    public List<Demand> findClientDemand(){


        // TODO: complete this
        Optional<Client> client = getClient();

        return null;
    }

    public boolean addDemand(DemandDTO dto){
        Optional<Client> client = getClient();

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
                .createdAt(LocalDate.now())
                .build();

        demandRepository.save(demand);

        return true;
    }

    public int editDemand(long id, DemandDTO dto){

        Optional<Client> client = getClient();

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

        Optional<Client> client = getClient();

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
