package br.com.hotelCalifornia.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app/hotel") // localhost:8080/api/hotel
@RequiredArgsConstructor
public class HotelCaliforniaController {
	
	private final HotelCaliforniaService service = null;
	
	@Autowired
	private HotelCaliforniaRepository repository;
	
	@GetMapping(value= "/listar")
	@ResponseBody
	public ResponseEntity<List> listarTudo() {
		 return ResponseEntity.ok(service.findAll());
	}
	
	@PostMapping
	
	public HotelCaliforniaModel criar(@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		return service.create(hotelCaliforniaModel);
		
	}
    @GetMapping(value = "/buscar/{id}")
    @ResponseBody
    public ResponseEntity<HotelCaliforniaModel> buscarId(@PathVariable Long id) {
    	return service.acharId(id);
    }
    
    @PutMapping(value="/editar/{id}")
    @ResponseBody
    public HotelCaliforniaModel editar(@PathVariable Long id,@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
            return service.atualizar(id, hotelCaliforniaModel);
    }
    
    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        	service.deletar(id);
        	return ResponseEntity.noContent().build();
    }	  
    
}
