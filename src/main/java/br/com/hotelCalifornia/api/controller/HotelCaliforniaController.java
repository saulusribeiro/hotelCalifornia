package br.com.hotelCalifornia.api.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hotelCalifornia.api.dto.HotelCaliforniaDto;
import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/app/hotel") // localhost:8080/api/hotel  e http://localhost:8090/swagger-ui/index.html
public class HotelCaliforniaController {
	
	@Autowired
	private HotelCaliforniaService service;
	
	@Autowired
	private HotelCaliforniaRepository repository;

	@Operation(summary = "Listar todos os Hotéis", method = "GET",
			responses = {
				@ApiResponse(description = "Hotéis listados com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Não tem hotéis para listar", responseCode = "400", content = @Content),
				@ApiResponse(description = "Operação não autorizada", responseCode = "401", content = @Content),
				@ApiResponse(description = "Hotel não encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Hotéis listados com Erro", responseCode = "500", content = @Content),
			}
		)
	@GetMapping(value= "/listar")
	@ResponseBody
	public ResponseEntity<List<HotelCaliforniaDto>> listarTudo()  {
		 return ResponseEntity.ok(service.listando());
	}
	
	@Operation(summary = "Salvar novos Hotéis", method = "POST",
			responses = {
				@ApiResponse(description = "Hotel salvo com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "CNPJ Duplicado", responseCode = "409", content = @Content),
				@ApiResponse(description = "Erro ao salvar Hotel", responseCode = "500", content = @Content),
			}
		)
	@PostMapping(value= "/salvar")
	@ResponseBody
	public ResponseEntity<HotelCaliforniaDto> save(@Valid @RequestBody HotelCaliforniaDto hotelDto) {
		 return ResponseEntity.status(HttpStatus.OK).body(service.salvando(hotelDto));
	}

	
	@Operation(summary = "Listar Hotel por CNPJ", method = "GET",
			responses = {
				@ApiResponse(description = "Hotel listado por CNPJ com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Operação não autorizada", responseCode = "401", content = @Content),
				@ApiResponse(description = "Hotel não encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Erro ao buscar por CNPJ", responseCode = "500", content = @Content),
			}
		)
	@GetMapping("/cnpj/{cnpj}")
	@ResponseBody
    public ResponseEntity<Object> AcharPeloPorCnpj(@PathVariable String cnpj) {
		return service.buscarPorCnpj(cnpj);
    }
	
	@Operation(summary = "Listar Hotel por Local", method = "GET",
			responses = {
				@ApiResponse(description = "Hotel listado por Local com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Operação não autorizada", responseCode = "401", content = @Content),
				@ApiResponse(description = "Hotel não encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Erro ao buscar por Local", responseCode = "500", content = @Content),
			}
		)
	@GetMapping("/local/{local}")
	@ResponseBody
    public ResponseEntity<Object> AcharPorlocal(@PathVariable String local) {
		return service.buscarPorlocal(local);
    }

	@Operation(summary = "Listar Hotel por Identificador", method = "GET",
			responses = {
				@ApiResponse(description = "Hotel listado por Identificador com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Operação não autorizada", responseCode = "401", content = @Content),
				@ApiResponse(description = "Hotel não encontrado", responseCode = "404", content = @Content),
				@ApiResponse(description = "Erro ao buscar por Identificador", responseCode = "500", content = @Content),
			}
		)
	@GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscarId(@PathVariable(value="id") Long id) {
           return service.acharId(id);
    
    }
	@Operation(summary = "Atualizar dados dos Hotéis", method = "PUT",
			responses = {
				@ApiResponse(description = "Hotel atualizado com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Erro ao atualizar Hotel", responseCode = "500", content = @Content),
			}
		)
    @PutMapping(value="/atualizar/{cnpj}")
    @ResponseBody
    public ResponseEntity<HotelCaliforniaDto> update(@PathVariable(value="cnpj")String cnpj, @RequestBody HotelCaliforniaDto hotelCaliforniaDto) {
  
    	return service.atualizar(cnpj, hotelCaliforniaDto); 
    
    }
	@Operation(summary = "Deletar dados dos Hotéis", method = "DELETE",
			responses = {
				@ApiResponse(description = "Hotel deletado com Successo", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json")
					}),
				@ApiResponse(description = "Erro ao deletar Hotel", responseCode = "500", content = @Content),
			}
		)
    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<?> remover(@PathVariable(value = "id") Long id) {
        return	service.deletar(id);
        	
    }	  
    
}
