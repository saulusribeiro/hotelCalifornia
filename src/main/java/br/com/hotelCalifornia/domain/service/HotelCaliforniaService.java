package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.UUID;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;


import java.util.logging.Logger;


@Service
public class HotelCaliforniaService {
	
	private HotelCaliforniaRepository repository;

	private Logger logger = Logger.getLogger(HotelCaliforniaService.class.getName());

	public List<?> findAll() {
	
		logger.info("Metodo findAll");
         
		return repository.findAll();
		
	}
	public HotelCaliforniaModel create(@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		
		logger.info("Metodo create");

		return repository.save(hotelCaliforniaModel);
		
	}
	public ResponseEntity<HotelCaliforniaModel> acharId(@PathVariable UUID id) {
		
    		logger.info("Metodo acharId");
	
	    	return repository.findById(id).map(mapping->ResponseEntity.ok().body(mapping))
	    			.orElse(ResponseEntity.notFound().build());
	}
	public ResponseEntity<HotelCaliforniaModel> update(@PathVariable UUID id, @RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		logger.info("Metodo update");

		
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
	
	 public ResponseEntity<?> deletar(@PathVariable UUID id) {
		 
		logger.info("Metodo delete");
 
     	return repository.findById(id).map(mapping->{
     		   repository.deleteById(id);
      	
     	       return ResponseEntity.ok().body("DELETADO COM SUCESSO");}
         ).orElse(ResponseEntity.notFound().build());   

 }	
	   

}
