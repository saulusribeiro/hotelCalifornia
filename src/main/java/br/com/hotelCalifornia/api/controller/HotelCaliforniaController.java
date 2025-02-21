package br.com.hotelCalifornia.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(("/api/hotel")) // localhost:8080/api/hotel
@RequiredArgsConstructor
public class HotelCaliforniaController {
	
	
	private final HotelCaliforniaRepository repository = null;
	
	@GetMapping
	public List findAll() {
		return repository.findAll();
		
	}
	
	@PostMapping
	public HotelCaliforniaModel create(@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		return repository.save(hotelCaliforniaModel);
		
	}
    @GetMapping(value = "{id}")
    public ResponseEntity<HotelCaliforniaModel> acharId(@PathVariable UUID id) {
    	return repository.findById(id).map(mapping->ResponseEntity.ok().body(mapping))
    			.orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping
    public ResponseEntity<HotelCaliforniaModel> update(@PathVariable UUID id, @RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
    	return repository.findById(id)
                 .map(mapping -> {
                	 mapping.setName(hotelCaliforniaModel.getName());
                	 mapping.setLocal(hotelCaliforniaModel.getLocal());
                	 mapping.setCapacidade(hotelCaliforniaModel.getCapacidade());
                	 mapping.setCnpj(hotelCaliforniaModel.getCnpj());
                	 
                	 HotelCaliforniaModel update = repository.save(mapping);
                	 return ResponseEntity.ok().body(update);
                 })
                 .orElse(ResponseEntity.notFound().build());                                               
    			
    			
    }
    
    @DeleteMapping(path = "/id")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        	return repository.findById(id).map(mapping->{
        		   repository.deleteById(id);
         	
        	       return ResponseEntity.ok().body("DELETADO COM SUCESSO");}
            ).orElse(ResponseEntity.notFound().build());   

    }	  
    
}
