package ma.ensa.movingservice.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.ensa.movingservice.dto.DemandDTO;
import ma.ensa.movingservice.exceptions.PermissionException;
import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.models.user.Client;
import ma.ensa.movingservice.models.user.Provider;
import ma.ensa.movingservice.models.user.User;
import ma.ensa.movingservice.repositories.DemandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository demandRepository;

    private DateTimeFormatter formatter;

    @PostConstruct
    public void initFormatter(){
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public Demand getDemand(long id) throws Exception{

        Optional<Demand> demand = demandRepository.findById(id);
        if(demand.isEmpty())
            throw new RecordNotFoundException("demand not found");

        return demand.get();
    }

    public List<DemandDTO> findAllDemands() {

        User user = Auths.getUser();

        if(user instanceof Provider provider){
            String city = provider.getCity();

            return demandRepository
                    .findForProvider(city)
                    .stream().map(demand ->
                            DemandDTO.builder()
                                    .clientName(demand.getClient().getFullName())
                                    .description(demand.getDescription())
                                    .proposedPrice(demand.getProposedPrice())
                                    .from(demand.getSCity())
                                    .to(demand.getDCity())
                                    .when(
                                            formatter.format(demand.getApproxTime())
                                    )
                                    .createdAt(
                                            formatter.format(demand.getCreatedAt())
                                    )
                                    .build()
                    )
                    .toList();
        }

        if(user instanceof Client client){

            return demandRepository
                    .findAllByClient(client)
                    .stream().map(demand ->
                            DemandDTO.builder()
                                    .id(demand.getId())
                                    .from(demand.getSCity())
                                    .to(demand.getDCity())
                                    .description(demand.getDescription())
                                    .when(
                                            formatter.format(demand.getApproxTime())
                                    )
                                    .createdAt(
                                            formatter.format(demand.getCreatedAt())
                                    )
                                    .proposedPrice(demand.getProposedPrice())
                                    .build()
                    )
                    .toList();
        }

        return null;

    }



    public Long addDemand(DemandDTO dto){

        Client client = Auths.getClient();

        Demand demand = Demand.builder()
                .description(dto.getDescription())
                .sCity(dto.getFrom())
                .dCity(dto.getTo())
                .client(client)
                .proposedPrice(dto.getProposedPrice())
                .approxTime(
                        LocalDateTime.parse(dto.getWhen(), formatter)
                )
                .build();

        return demandRepository
                .save(demand)
                .getId();

    }

    public void editDemand(long id, DemandDTO dto) throws Exception{

        Client client = Auths.getClient();

        Demand demand = demandRepository
                .findById(id)
                .orElseThrow(RecordNotFoundException::new);

        if(client.getId() != demand.getClient().getId())
            throw new PermissionException("the demand is not belong to you");

        demand.setDescription(dto.getDescription());
        demand.setSCity(dto.getFrom());
        demand.setDCity(dto.getTo());
        demand.setApproxTime(LocalDateTime.parse(dto.getWhen(), formatter));

        demandRepository.save(demand);

    }

    public void deleteDemand(long id){

        Client client = Auths.getClient();

        Demand demand = demandRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);


        if(client.getId() != demand.getClient().getId())
            throw new PermissionException("this demand does not belong to you");

        demandRepository.delete(demand);

    }


}
