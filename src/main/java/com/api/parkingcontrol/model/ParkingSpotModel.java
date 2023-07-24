package com.api.parkingcontrol.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
public class ParkingSpotModel implements Serializable {//conversões
    private static final long serialVersionUID = 1L; // controle das conversões feito pela JVM

    @Id // a classe principal precisa de um IDENTIFICADOR, chama-se Id e é do tipo UUID
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; //O UUID é um tipo de dado especial que representa um identificador único universal.
    // Ele é gerado automaticamente e é composto por uma sequência de caracteres alfanuméricos. Essa sequência é tão única que mesmo em diferentes sistemas ou ambientes,
    // não haverá identificadores iguais.
    @Column(nullable = false, unique = true, length = 10) // o campo terá que ter um valor para ser salvo(não pode ser nula), campo único, caracteres limitado para 10
    private String parkingSpotNumber; //número da vaga, mistura de letras com numeros
    @Column(nullable = false, unique = true, length = 7) //não pode ser nula, campo único, caractéres limitado para 7
    private String licensePlateCar; //placa do carro, mistura de letras com números
    @Column(nullable = false, length = 70)//não pode ser nula, campo único, caractéres limitado para 70
    private String brandCar; //marca do carro
    @Column(nullable = false, length = 70) //não pode ser nula, campo único, caractéres limitado para 70
    private String modelCar; //modelo do carro
    @Column(nullable = false, length = 70) //não pode ser nula, campo único, caractéres limitado para 70
    private String colorCar; // cor do carro
    @Column(nullable = false) // nao pode ser nula
    private LocalDateTime registrationDate; // data de registro, dia do registro do carro
    @Column(nullable = false, length = 130)// nao pode ser nula, caracteres limitado para 130
    private String responsibleName; //nome do responsável do veículo/apartamento
    @Column(nullable = false, length = 30)// nao pode ser nula, caracteres limitado para 30
    private String apartment; //apartamento em si, mistura de letras com números
    @Column(nullable = false, length = 30)// nao pode ser nula, caracteres limitado para 30
    private String block; //bloco/torre do condomínio


    //Gerar métodos getters e setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlateCar) {
        this.licensePlateCar = licensePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}


