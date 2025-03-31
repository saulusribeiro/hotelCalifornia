package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;


@Service
public class HotelCaliforniaService {
	
	private final HotelCaliforniaRepository repository;

	private Logger logger = Logger.getLogger(HotelCaliforniaService.class.getName());


    HotelCaliforniaService(HotelCaliforniaRepository repository) {
        this.repository = repository;
    }
	
	
	public ResponseEntity<Object> findAll() {
	
		logger.info("Metodo findAll");
		
		List<HotelCaliforniaModel> listaTodos = repository.findAll();
		
		if(listaTodos.isEmpty()) {
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");}
		
		return ResponseEntity.status(HttpStatus.OK).body(listaTodos); 
		
		
		
	}
	@Transactional
	public HotelCaliforniaModel create(HotelCaliforniaModel hotelCaliforniaModel) {
		
		logger.info("Metodo create");

		return repository.save(hotelCaliforniaModel);
		
	}
	public ResponseEntity<Object> acharId(Long id) {
		
    		logger.info("Metodo acharId");
    		
    		Optional<HotelCaliforniaModel> californiaModel = repository.findById(id);
    		if(!californiaModel.isPresent()) 
        		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");
			return ResponseEntity.status(HttpStatus.OK).body(californiaModel);
	}
	public ResponseEntity<Object> buscarPorCnpj(String cnpj) {
		
	     	logger.info("Metodo acharPorCNPJ");
	     	
	    	Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorCnpj(cnpj);
    		if(!californiaModel.isPresent()) 
        		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");
    		
    		return ResponseEntity.status(HttpStatus.OK).body(californiaModel);  
	        
	        }
	public ResponseEntity<HotelCaliforniaModel> buscarPorlocal(String local) {
		
     	logger.info("Metodo acharPorLocal");
     	
        return repository.acharPorLocal(local).map(mapping->ResponseEntity.ok().body(mapping))
    			.orElse(ResponseEntity.notFound().build());
        }
	@Transactional
	public ResponseEntity<Object> atualizar(Long id, HotelCaliforniaModel hotelCaliforniaModel) {
		logger.info("Metodo Atualizar");
  
        Optional<HotelCaliforniaModel> hotelOptional = repository.findById(id);
    	
    	if(!hotelOptional.isPresent()) {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");}
		
		HotelCaliforniaModel novoHotel = repository.findById(id).get();
    	BeanUtils.copyProperties(hotelCaliforniaModel, novoHotel,"id"); // o terceiro parametro "id" assegura a alteração do registro
    	                                                                // se não colocar, a biblioteca vai criar um novo registro com um
    	                                                               // com um novo id, com os dados alterados  
    	
    	try {
    		repository.save(novoHotel);
	        return ResponseEntity.status(HttpStatus.OK).body(novoHotel);  
			
		} catch (Exception e) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ! Hotel não atualizado");
		}
    	       			
	  }
	 @Transactional
	 public ResponseEntity<?> deletar(@PathVariable Long id) {
		 
		logger.info("Metodo delete");
		Optional<HotelCaliforniaModel> hotelOptional = repository.findById(id);
	    	
	    if(!hotelOptional.isPresent()) {
	    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");}
			
	    try {
	    	return repository.findById(id).map(mapping->{
	     		   repository.deleteById(id);
	      	
	     	       return ResponseEntity.ok().body("DELETADO COM SUCESSO");}
	         ).orElse(ResponseEntity.noContent().build());   
		} catch (Exception e) {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ! Hotel não deletado");
		}
 
     	

 }	
	 
	   

}
