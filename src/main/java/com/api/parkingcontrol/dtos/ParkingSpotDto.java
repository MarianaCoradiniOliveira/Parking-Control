package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//dados de entrada - todos os dados de registro da vaga de estacionamento do cliente para que sejam salvas
public class ParkingSpotDto {

    //@NotBlank verifica se o campo não é nulo, se não tem String vazia
    //@Size limite o número de caratcteres
    @NotBlank
    private String parkingSpotNumber; //Verifica número da vaga
    @NotBlank
    @Size(max = 7) //limite máximo de caracter da placa
    private String licensePlateCar; //placa
    @NotBlank
    private String brandCar; // verifica a marca do carro
    @NotBlank
    private String modelCar; //verifica o modelo
    @NotBlank
    private String colorCar;// verifica a cor
    @NotBlank
    private String responsibleName;// verifica o nome do responsável
    @NotBlank
    private String apartment;// verifica o apartamento
    @NotBlank
    private String block;//verifica o bloco/torre


    //gera os métodos getter e setter
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

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
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
