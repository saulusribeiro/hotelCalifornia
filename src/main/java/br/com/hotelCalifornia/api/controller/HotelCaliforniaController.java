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

import br.com.hotelCalifornia.api.dto.HotelCaliforniaDto;
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
	public ResponseEntity<List<HotelCaliforniaDto>> listarTudo() {
		 return ResponseEntity.ok(service.listando());
	}
	
	
	@PostMapping(value= "/salvar")
	@ResponseBody
	public ResponseEntity<HotelCaliforniaDto> save(@RequestBody HotelCaliforniaDto hotelDto) {
		 return ResponseEntity.status(HttpStatus.OK).body(service.salvando(hotelDto));
	}

	
	
	@GetMapping("/cnpj/{cnpj}")
	@ResponseBody
    public ResponseEntity<Object> AcharPeloPorCnpj(@PathVariable String cnpj) {
		return service.buscarPorCnpj(cnpj);
    }
	@GetMapping("/local/{local}")
	@ResponseBody
    public ResponseEntity<HotelCaliforniaModel> AcharPorlocal(@PathVariable String local) {
		return service.buscarPorlocal(local);
    }

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscarId(@PathVariable(value="id") Long id) {
           return service.acharId(id);
    
    }
    
    @PutMapping(value="/atualizar/{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable(value="id")Long id,@RequestBody HotelCaliforniaDto hotelCaliforniaDto) {
  
    	return service.atualizar(id, hotelCaliforniaDto); 
    
    }
    
    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        return	service.deletar(id);
        	
    }	  
    
}
