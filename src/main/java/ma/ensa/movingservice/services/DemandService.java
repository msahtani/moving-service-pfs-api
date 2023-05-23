package ma.ensa.movingservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DemandDTO;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.repositories.DemandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;

    public Demand getDemand(long id) throws Exception{

        Optional<Demand> demand = demandRepository.findById(id);
        if(demand.isEmpty())
            throw new RecordNotFoundException("demand not found");

        return demand.get();
    }


    public List<DemandDTO> findForProvider() throws Exception{

        Provider provider = Auths.getProvider();

        String city = provider.getCity();

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

    public List<Demand> getAllDemands() throws Exception{

        Client client = Auths.getClient();

        return demandRepository.findAllByClient(client);

    }

    public void addDemand(DemandDTO dto) throws Exception{
        Client client = Auths.getClient();

        System.out.println(dto);

        Demand demand = Demand.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .sCity(dto.getFrom())
                .dCity(dto.getTo())
                .client(client)
                .createdAt(LocalDateTime.now())
                .build();

        demandRepository.save(demand);

    }

    public void editDemand(long id, DemandDTO dto) throws Exception{

        Client client = Auths.getClient();

        Optional<Demand> demandBox = demandRepository.findById(id);

        if(demandBox.isEmpty())
            throw new RecordNotFoundException("demand not found");

        Demand demand = demandBox.get();

        if(client.getId() != demand.getClient().getId())
            throw new PermissionException("the demand is not belong to you");

        demand.setTitle(dto.getTitle());
        demand.setDescription(dto.getDescription());
        demand.setSCity(dto.getFrom());
        demand.setDCity(dto.getTo());

        demandRepository.save(demand);

    }

    public void deleteDemand(long id) throws Exception{

        Client client = Auths.getClient();

        Optional<Demand> demandBox = demandRepository.findById(id);

        if(demandBox.isEmpty())
            throw new RecordNotFoundException("demand not found");

        Demand demand = demandBox.get();

        if(client.getId() != demand.getClient().getId())
            throw new PermissionException("this demand does not belong to you");

        demandRepository.delete(demand);

    }

}
