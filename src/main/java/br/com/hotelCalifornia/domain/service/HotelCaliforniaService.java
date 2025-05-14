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
import br.com.hotelCalifornia.infraestructure.exceptions.BadRequestException;
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
    	} catch (BadRequestException brq) {
			throw new BadRequestException(brq.getMessage());
    	} catch (BusinessException e) {
			throw new BusinessException("Erro ao listar Hotéis" );
		} catch (Exception e) {
    		throw new  BusinessException("Erro ao listar hotéis "+e );
    	}
    }


    @Modifying
	@Transactional
    public HotelCaliforniaDto salvando(HotelCaliforniaDto dto){
        Optional<HotelCaliforniaModel> hotelExist = repository.acharPorCnpj(dto.getCnpj());
       
		if(!hotelExist.isEmpty()) {
			throw new ConflictException("CNPJ duplicado " + dto.getCnpj());
		}else{
			try {
				HotelCaliforniaModel hotel = converter.toModel(dto);
		    	HotelCaliforniaModel hotelSalvo = repository.save(hotel);
		    	return converter.toDto(hotelSalvo);
			} catch (ConflictException ce) {
				throw new ConflictException(ce.getMessage());
			} catch (BadRequestException brq) {
					throw new BadRequestException("Erro requisição "+brq.getMessage());
     		} catch (BusinessException e) {
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
    		try {
    			return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));	
	        } catch (BadRequestException brq) {
					throw new BadRequestException("Erro requisição "+brq.getMessage());
	    	} catch (BusinessException e) {
				throw new BusinessException("Erro ao buscar Hotel "+ e );
			}	
	}
	public ResponseEntity<Object> buscarPorCnpj(String cnpj) {
		
	     	logger.info("Metodo acharPorCNPJ");
	     	
	    	Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorCnpj(cnpj);
    		if(!californiaModel.isPresent()) 
        		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado no CNPJ "+cnpj);
            HotelCaliforniaModel cm = californiaModel.get();
            try {
         		return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
            } catch (BadRequestException brq) {
				throw new BadRequestException("Erro requisição "+brq.getMessage());
        	} catch (BusinessException e) {
				throw new BusinessException("Erro ao buscar por CNPJ "+ e );
			}	
           }
	public ResponseEntity<Object> buscarPorlocal(String local) {
		
     	logger.info("Metodo acharPorLocal");
     	
        Optional<HotelCaliforniaModel> californiaModel =  repository.acharPorLocal(local);
		if(!californiaModel.isPresent()) 
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado no Local "+local);
        HotelCaliforniaModel cm = californiaModel.get();
        try {
		  return ResponseEntity.status(HttpStatus.OK).body(converter.toDto(cm));
        		
	    } catch (BadRequestException brq) {
			throw new BadRequestException("Erro requisição "+brq.getMessage());
		} catch (BusinessException e) {
			throw new BusinessException("Erro ao buscar por local o Hotel "+ e );
		}	
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
	        try {
              salvarHotel(hotelUpdate);
	    	  return ResponseEntity.ok(converter.toDto(hotelUpdate));
	        } catch (UnprocessableEntityException unp) {
			  throw new UnprocessableEntityException(unp.getMessage());
	        } catch (BadRequestException brq) {
				throw new BadRequestException("Erro requisição "+brq.getMessage());
	    	} catch (BusinessException e) {
				throw new BusinessException("Erro ao atualizar o Hotel "+ e );
			}	
		}
	}
	private HotelCaliforniaModel salvarHotel(HotelCaliforniaModel entityUpdate) {
		
		 try {
			 return repository.save(entityUpdate);
		 } catch (BadRequestException brq) {
				throw new BadRequestException("Erro requisição "+brq.getMessage());
		 } catch (BusinessException e) {
					throw new BusinessException("Erro ao salvar o Hotel "+ e );
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
	    } catch (UnprocessableEntityException unp) {
			throw new UnprocessableEntityException(unp.getMessage()); 
	    } catch (BadRequestException brq) {
			throw new BadRequestException("Erro requisição "+brq.getMessage());
	    } catch (BusinessException be) {
				throw new BusinessException("Erro ao salvar o Hotel "+ be );
	    } catch (Exception e) {
			throw new BusinessException("erro ao deletar "+e); 
		}	
	     	

 }	
	 
	   

}
