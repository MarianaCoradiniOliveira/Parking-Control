package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.model.ParkingSpotModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> { // extendeu o JpaRepository do SpringDate, definiu o model do repository que é o ParkingSpotModel
    // e o tipo de identificador UUID
    // o JpaRepository possui vários métodos prontos para serem utilizados para transações com o banco de dados ex:salvar, editar, atualizar
    //quando é extendido o JpaRepository, ele já traz consigo o @Repository (não é necessário fazer a anotação)

    boolean existsByLicensePlateCar(String licensePlateCar); //recebe a placa do carro
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
}
