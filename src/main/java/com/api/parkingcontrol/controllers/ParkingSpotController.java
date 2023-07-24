package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController// indica que uma classe é um controlador (controller) responsável por tratar requisições HTTP e retornar respostas no formato JSON ou XML
@CrossOrigin(origins = "*", maxAge = 3600)// permite que seja acessado de qualquer fonte
@RequestMapping("/parking-spot")// Definido a URI em nível de classe
public class ParkingSpotController {
    //criar um ponto de injeção via construtor
    final ParkingSpotService parkingSpotService;

    //construtor
    public ParkingSpotController(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    //primeiro criar os dados de entrada (ParkingSpotDto)


    //Criação do método POST
    @PostMapping //mapear uma solicitação HTTP POST para um método específico em um controlador
    //Não foi definido nenhuma URI pois já foi definido em nível de classe, ou seja, sempre que um cliente enviar um post, ele vai enviar para "/parking-spot", definindo o método post http
    // e assim o método "saveParkingSpot" vai ser acionado para responder a esta solicitação
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){ //A função saveParkingSpot é um método público que recebe um objeto do tipo ParkingSpotDto como parâmetro no corpo da requisição. Ela retorna um objeto ResponseEntity<Object>.

        //Verificações

        //é acionada o método "existsByLicensePlateCar", onde vai ser passado a placa do carro "getLicensePlateCar()"
        //se existir, irá retornar um "CONFLICT" para o cliente dizendo que esta placa já foi registrada
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){ //verifica se pra esse placa de carro já possui algum registro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Esta placa já foi registrada");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){ //verifica se pra esta vaga já possui algum registro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Esta vaga já foi registrada");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){ //verifica se este bloco e o apartamento já possui algum registro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Este bloco/apartamento já foi registrado");
        }

        var parkingSpotModal = new ParkingSpotModel(); //. Essa instância será usada para armazenar os dados do estacionamento que serão salvos posteriormente no banco de dados.
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModal);// converte o dto em model(o que vai ser convertido, no que ele vai ser convertido)
        parkingSpotModal.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));// setando a data de registro e sendo salva em UTC
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModal));// O código de status definido é HttpStatus.CREATED, indicando que a requisição foi bem-sucedida e que um novo recurso foi criado.
        //O método body é chamado no objeto ResponseEntity para definir o corpo da resposta. O valor passado para o método body é o resultado da chamada do método save do serviço parkingSpotService, que recebe o objeto parkingSpotModal como parâmetro.
        // Esse método save salva a vaga de estacionamento no banco de dados.
        //proximo passo é criar o save no ParkingSpotService
    }

    //Criação do método GET ALL

    //implementação do GET ALL, solicitar toda a listagem de vagas de estacionamento da API
    @GetMapping //ele vai enviar o "/parking-spot" (linha 18) para obter essa listagem
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){ // retorna o ParkingSpotModel, mas irá tornar uma paginação desses models.
                                                                                                                        //como será a ordem da paginação
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable)); //sempre vai enviar uma listagem das vagas de estacionamento, se nao tiver nenhuma vaga, a listagem vai vazia
    }

    //Criação do métodos GET ONE

    //Ele passa o ID e retorna o registro dessa vaga, desse ID específico
    @GetMapping("/{id}") // "/parkingSpot/id"
    //sempre que receber o "/parkingSpot/id", o método "getOneParkingSpot" vai ser acionado
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){ // é usado a anotação @PathVariable para mapear o caminho e pra isso, é necessário colocar o mesmo valor da anotação GetMapping e adicionar o seu tipo
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id); //busca na base de dados o ID
        if (!parkingSpotModelOptional.isPresent()){ // se o parkingSpotModelOptional NÃO estiver presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot não encontrado"); // envia para o cliente que a vaga nao foi encontrada
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());//mas se estiver cadastrada, ele retorna o valor do ID
    }

    //Criação do método de delete

    //ele recebe o ID na URI específico do recurso que vai ser deletado e antes de ser deletado vai ser feito uma verificação se o registro existe ou não
    @DeleteMapping("/{id}") // "/parkingSpot/id"
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){ // é usado a anotação @PathVariable para mapear o caminho e pra isso, é necessário colocar o mesmo valor da anotação GetMapping e adicionar o seu tipo
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id); //busca na base de dados o ID
        if (!parkingSpotModelOptional.isPresent()){ // se o parkingSpotModelOptional NÃO estiver presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot não encontrado"); // envia para o cliente que a vaga nao foi encontrada
        }
        parkingSpotService.delete(parkingSpotModelOptional.get()); //se este registro existir para este ID, ele vai ser deletado
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deletado com sucesso");//após ser deletado, monta-se uma resposta com o status "ok" e retorna para o usuário que foi deletado com sucesso
    }

    //Criação do método PUT

    //Atualização dos campos
    @PutMapping("/{id}") // "/parkingSpot/id"
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id, // é usado a anotação @PathVariable para mapear o caminho e pra isso, é necessário colocar o mesmo valor da anotação GetMapping e adicionar o seu tipo
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){ // campos para serem atualizados e usado o @valid para confirmar as validações feitas
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id); //busca na base de dados o ID
        if (!parkingSpotModelOptional.isPresent()){ // se o parkingSpotModelOptional NÃO estiver presente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot não encontrado"); // envia para o cliente que a vaga nao foi encontrada
        }
        //se estiver ok, ocorre as seguintes atualizações
//        var parkingSpotModel = parkingSpotModelOptional.get();
//        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
//        parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
//        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
//        parkingSpotModel.setBlock(parkingSpotDto.getBlock());
//        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
//        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
//        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
//        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());

        //outro jeito de ser feito:
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); // converte o dto em model(o que vai ser convertido, no que ele vai ser convertido)
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId()); //seta o ID para permanecer o mesmo ID que foi obtido do banco de dados
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate()); //data de registro que já esta salva no banco de dados


        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));// O código de status definido é HttpStatus.CREATED, indicando que a requisição foi bem-sucedida e que um novo recurso foi criado.
        //O método body é chamado no objeto ResponseEntity para definir o corpo da resposta. O valor passado para o método body é o resultado da chamada do método save do serviço parkingSpotService, que recebe o objeto parkingSpotModal como parâmetro.
        // Esse método save atualiza a vaga de estacionamento no banco de dados.
    }
}