package com.api.parkingcontrol.services;

import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

//Em certos momentos irá ter que injetar uma dependencia de ParkingSpotRepository dentro de ParkingSpotService
// @Autowired é usada para realizar a injeção de dependência automaticamente em uma classe. A injeção de dependência é um princípio do design de software
// que permite que objetos dependentes sejam fornecidos automaticamente por um contêiner de inversão de controle, como o Spring.

    final
    ParkingSpotRepository parkingSpotRepository; //dependencia

    //construtor
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository){ //Construor publico que recebe um objeto do tipo "ParkingSpotRepository", que é uma interface relacionada ao acesso
        // e manipulação do banco de dados
        this.parkingSpotRepository = parkingSpotRepository; // o valor passado para o parâmetro parkingSpotRepository é atribuído ao atributo parkingSpotRepository da classe ParkingSpotService
        // O uso do this é usado para distinguir entre o parâmetro e o atributo da classe com o mesmo nome.
    }

    //método
    @Transactional //quando sao métodos construtivos ou destrutivos havendo relacionamentos com salvamento em cascata para caso algo der errado durante a transação garante o rollback volte ao normal
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModal) {
        return parkingSpotRepository.save(parkingSpotModal);//método do JPA
        //entidade = parkingspotmodel
    }

    //método declado dentro do repositoy
    public boolean existsByLicensePlateCar(String licensePlateCar) { //licença do carro
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) { //licença da vaga
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) { //licença do apartamaento/bloco
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }


    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable); //paginação
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id); //busca o ID na base de dados
    }

    @Transactional //quando sao métodos construtivos ou destrutivos havendo relacionamentos com salvamento em cascata para caso algo der errado durante a transação garante o rollback volte ao normal
    public void delete(ParkingSpotModel parkingSpotModel) { //nao tem return pois ele é void, vai apenas deletar
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
