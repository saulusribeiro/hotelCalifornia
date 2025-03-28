package br.com.hotelCalifornia.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/app/hotel") // localhost:8080/api/hotel  e http://localhost:8090/swagger-ui/index.html
//@RequiredArgsConstructor
public class HotelCaliforniaController {
	
	@Autowired
	private HotelCaliforniaService service;
	
	@Autowired
	private HotelCaliforniaRepository repository;
	
	@GetMapping(value= "/listar")
	@ResponseBody
	public ResponseEntity<List> listarTudo() {
		 return ResponseEntity.ok(service.findAll());
	}
	@GetMapping("/cnpj/{cnpj}")
	@ResponseBody
    public ResponseEntity<HotelCaliforniaModel> AcharPeloPorCnpj(@PathVariable String cnpj) {
		return service.buscarPorCnpj(cnpj);
    }
	
	@PostMapping(value= "/inserir")
	public HotelCaliforniaModel criar(@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
		
		return service.create(hotelCaliforniaModel);
		
	}
    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscarId(@PathVariable(value="id") Long id) {
    	Optional<HotelCaliforniaModel> californiaModel = service.acharId(id);
    	
    	if(!californiaModel.isPresent()) 
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");
 		return  ResponseEntity.status(HttpStatus.OK).body(service.acharId(id));
    
    }
    
    @PutMapping(value="/editar/{id}")
    @ResponseBody
    public ResponseEntity<Object> editar(@PathVariable(value="id")Long id,@RequestBody HotelCaliforniaModel hotelCaliforniaModel) {
    	
    	Optional<HotelCaliforniaModel> hotelOptional = service.acharId(id);
    	
    	if(!hotelOptional.isPresent()) {
    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel não encontrado");}
    	service.atualizar(id, hotelCaliforniaModel);
    	return ResponseEntity.status(HttpStatus.OK).body(hotelCaliforniaModel);
    }
    
    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        	service.deletar(id);
        	return ResponseEntity.noContent().build();
    }	  
    
}
