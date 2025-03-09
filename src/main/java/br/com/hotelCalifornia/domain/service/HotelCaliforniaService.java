package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;


@Service
public class HotelCaliforniaService {
	
	@Autowired
	private HotelCaliforniaRepository repository;

	private Logger logger = Logger.getLogger(HotelCaliforniaService.class.getName());
	
	
	public List<HotelCaliforniaModel> findAll() {
	
		logger.info("Metodo findAll");
         
		return repository.findAll();
		
	}
	public HotelCaliforniaModel create(HotelCaliforniaModel hotelCaliforniaModel) {
		
		logger.info("Metodo create");

		return repository.save(hotelCaliforniaModel);
		
	}
	public ResponseEntity<HotelCaliforniaModel> acharId(Long id) {
		
    		logger.info("Metodo acharId");
	
	    	return repository.findById(id).map(mapping->ResponseEntity.ok().body(mapping))
	    			.orElse(ResponseEntity.notFound().build());
	}
	public HotelCaliforniaModel atualizar(Long id, HotelCaliforniaModel hotelCaliforniaModel) {
		logger.info("Metodo update");

		
		HotelCaliforniaModel novoHotel = repository.findById(id).get();
    	BeanUtils.copyProperties(hotelCaliforniaModel, novoHotel,"id"); // o terceiro parametro "id" assegura a alteração do registro
    	                                                                // se não colocar, a biblioteca vai criar um novo registro com um
    	                                                               // com um novo id, com os dados alterados  
    	
    	return repository.save(novoHotel);                                        
	    			
	    			
	  }
	
	 public ResponseEntity<?> deletar(@PathVariable Long id) {
		 
		logger.info("Metodo delete");
 
     	return repository.findById(id).map(mapping->{
     		   repository.deleteById(id);
      	
     	       return ResponseEntity.ok().body("DELETADO COM SUCESSO");}
         ).orElse(ResponseEntity.notFound().build());   

 }	
	   

}
