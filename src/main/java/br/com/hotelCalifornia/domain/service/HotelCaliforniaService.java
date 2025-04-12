package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hotelCalifornia.api.dto.HotelCaliforniaDto;
import br.com.hotelCalifornia.domain.converter.HotelCaliforniaConverter;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;


@Service
public class HotelCaliforniaService {
	
	private final HotelCaliforniaRepository repository;

	
	private Logger logger = Logger.getLogger(HotelCaliforniaService.class.getName());
	
	
  
    HotelCaliforniaService(HotelCaliforniaRepository repository) {
        this.repository = repository;
    }
    
    public List<HotelCaliforniaDto> listando(){
    	List<HotelCaliforniaModel> hotelList = 	repository.findAll();
    	return  converter.toDtoList(hotelList);
    }
    
    HotelCaliforniaConverter converter = new HotelCaliforniaConverter();

	@Transactional
    public HotelCaliforniaDto salvando(HotelCaliforniaDto dto) {
    	HotelCaliforniaModel hotel = converter.toModel(dto);
    	HotelCaliforniaModel hotelSalvo = repository.save(hotel);
        	
    	return converter.toDto(hotelSalvo);
    }
    
 	public ResponseEntity<Object> acharId(Long id) {
		
    		logger.info("Metodo acharId");
    		
    		Optional<HotelCaliforniaModel> californiaModel = repository.findById(id);
    		
    		if(!californiaModel.isPresent()) {
    			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado"); }
    		HotelCaliforniaModel cm = californiaModel.get();
    		
     		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
	}
	public ResponseEntity<Object> buscarPorCnpj(String cnpj) {
		
	     	logger.info("Metodo acharPorCNPJ");
	     	
	    	Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorCnpj(cnpj);
    		if(!californiaModel.isPresent()) 
        		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");
            HotelCaliforniaModel cm = californiaModel.get();
    		
     		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
	        }
	public ResponseEntity<Object> buscarPorlocal(String local) {
		
     	logger.info("Metodo acharPorLocal");
     	
        Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorLocal(local);
		if(!californiaModel.isPresent()) 
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel neste CNPJ não encontrado");
        HotelCaliforniaModel cm = californiaModel.get();
		
 		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
        }
	@Transactional
	public ResponseEntity<Object> atualizar(Long id, HotelCaliforniaDto hotelCaliforniaDto) {
		logger.info("Metodo Atualizar");
  
        Optional<HotelCaliforniaModel> hotelOptional = repository.findById(id);
    	
    	if(!hotelOptional.isPresent()) {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");}
		
		HotelCaliforniaModel novoHotel = repository.findById(id).get();
    	BeanUtils.copyProperties(hotelCaliforniaDto, novoHotel,"id"); // o terceiro parametro "id" assegura a alteração do registro
    	                                                                // se não colocar, a biblioteca vai criar um novo registro com um
    	                                                               // com um novo id, com os dados alterados  
    	
    	try {
    		repository.save(novoHotel);  
    		
	        return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(novoHotel));  
			
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
