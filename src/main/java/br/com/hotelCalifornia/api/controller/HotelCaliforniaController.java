package br.com.hotelCalifornia.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;

@RestController
@RequestMapping("/api/hotel") // localhost:8080/api/hotel
//@RequiredArgsConstructor
public class HotelCaliforniaController {
	
	@Autowired(required=true)
	private HotelCaliforniaService service;
	
	@GetMapping
	public ResponseEntity<List> listarTudo() {
		 return ResponseEntity.ok(service.findAll());
	}
	
	@PostMapping
	public HotelCaliforniaModel criar(@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		return service.create(hotelCaliforniaModel);
		
	}
    @GetMapping(value = "{id}")
    public ResponseEntity<HotelCaliforniaModel> buscarId(@PathVariable Long id) {
    	return service.acharId(id);
    }
    
    @PutMapping
    public ResponseEntity<HotelCaliforniaModel> atualizar(@PathVariable Long id, @RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
    	return service.update(id, hotelCaliforniaModel);
    }
    
    @DeleteMapping(path = "/id")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        	return service.deletar(id);
    }	  
    
}
