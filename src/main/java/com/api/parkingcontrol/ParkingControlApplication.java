package com.api.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // indica que é uma classe controladora, responsável por tratar requisições HTTP e retornar respotas no formato JSON ou XML. Gera dependencias quando necessário
public class ParkingControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);
	}

	@GetMapping("/") // Permite especificar a rota(URL) na qual o método será adicionado. Nâo adicionamos nenhum  URI então foi definido apenas a raiz (localhost:8080/)
	// e conseguir acessar o "olá mundo"
	public String index(){
			return "Olá mundo";
	}
}
