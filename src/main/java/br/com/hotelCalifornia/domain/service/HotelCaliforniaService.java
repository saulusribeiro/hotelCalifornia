package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.SystemException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import br.com.hotelCalifornia.api.dto.HotelCaliforniaDto;
import br.com.hotelCalifornia.domain.converter.HotelCaliforniaConverter;
import br.com.hotelCalifornia.infraestructure.exceptions.BusinessException;
import br.com.hotelCalifornia.infraestructure.exceptions.ConflictException;
import br.com.hotelCalifornia.infraestructure.exceptions.UnprocessableEntityException;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;


@Service
public class HotelCaliforniaService {
	
	private final HotelCaliforniaRepository repository;

	
	private Logger logger = Logger.getLogger(HotelCaliforniaService.class.getName());
	
	
  
    HotelCaliforniaService(HotelCaliforniaRepository repository) {
        this.repository = repository;
    }
    
    HotelCaliforniaConverter converter = new HotelCaliforniaConverter();
    
    @Transactional(readOnly = true)
    public List<HotelCaliforniaDto> listando() {
   
    	try {
    	 	List<HotelCaliforniaModel> hotelList = repository.findAll();
    		
    		if(hotelList.isEmpty()) {
       			throw new UnprocessableEntityException("Não tem nada para listar");}
    	  	return  converter.toDtoList(hotelList);
    	} catch (UnprocessableEntityException unp) {
    		throw new UnprocessableEntityException(unp.getMessage());
    	} catch (Exception e) {
    		throw new  BusinessException("Erro ao buscar hotéis");
    	}
    }


    @Modifying
	@Transactional
    public HotelCaliforniaDto salvando(HotelCaliforniaDto dto){
    	try {
    		if(repository.acharPorCnpj(dto.getCnpj())!=null) {
       			throw new UnprocessableEntityException("CNPJ duplicado");
    		}
    		HotelCaliforniaModel hotel = converter.toModel(dto);
    		HotelCaliforniaModel hotelSalvo = repository.save(hotel);
        	return converter.toDto(hotelSalvo);

 		} catch (ConflictException ce) {
			throw new ConflictException(ce.getMessage());
 		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Erro ao salvar o Hotel "+ e );
		}	
       	           	
    
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
	
	@Modifying
	@Transactional
	public ResponseEntity<HotelCaliforniaDto> atualizar(String cnpj, HotelCaliforniaDto hotelCaliforniaDto) {
		logger.info("Metodo Atualizar");
  
		try {
			 HotelCaliforniaModel hotelExist = Optional.ofNullable(repository.acharPorCnpj(cnpj))
			            .orElseThrow(() -> new UnprocessableEntityException("Não foi possível encontrar o hotel com o CNPJ " + cnpj));
 
    	    salvando(converter.toEntityUpdate(hotelExist, hotelCaliforniaDto, cnpj));
    	    return ResponseEntity.ok(converter.toDto(hotelExist));
	
		} catch (UnprocessableEntityException unp) {
				throw new UnprocessableEntityException(unp.getMessage());
		} catch (Exception e) {
    		throw new  BusinessException("Erro ao atualizar hotel ");
    	}		
		
	}
	 @Modifying
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
