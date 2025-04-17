package br.com.hotelCalifornia.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
    		else
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
        Optional<HotelCaliforniaModel> hotelExist = repository.acharPorCnpj(dto.getCnpj());
       
		if(!hotelExist.isEmpty()) {
			throw new UnprocessableEntityException("CNPJ duplicado " + dto.getCnpj());
		}else{
			try {
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
        		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado no CNPJ "+cnpj);
            HotelCaliforniaModel cm = californiaModel.get();
    		
     		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
	        }
	public ResponseEntity<Object> buscarPorlocal(String local) {
		
     	logger.info("Metodo acharPorLocal");
     	
        Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorLocal(local);
		if(!californiaModel.isPresent()) 
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado no Local "+local);
        HotelCaliforniaModel cm = californiaModel.get();
		
 		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
        }
	
	@Modifying
	@Transactional
	public ResponseEntity<HotelCaliforniaDto> atualizar(String cnpj, HotelCaliforniaDto hotelCaliforniaDto) {
		logger.info("Metodo Atualizar");
		HotelCaliforniaModel hotelUpdate;
		
		Optional<HotelCaliforniaModel> hotelExist = repository.acharPorCnpj(cnpj);
		
		if(hotelExist==null) {
			throw new UnprocessableEntityException("Não foi possível encontrar o hotel com o CNPJ " + cnpj);
		}else{
			
			hotelUpdate = hotelExist.get();
			hotelUpdate = converter.toEntityUpdate(hotelUpdate, hotelCaliforniaDto, cnpj);
		    salvarHotel(hotelUpdate);
	        try {
	          return ResponseEntity.ok(converter.toDto(hotelUpdate));
	        } catch (UnprocessableEntityException unp) {
			  throw new UnprocessableEntityException(unp.getMessage());
	        }
		}
	}
	private HotelCaliforniaModel salvarHotel(HotelCaliforniaModel entityUpdate) {
		 try {
			 return repository.save(entityUpdate);
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
