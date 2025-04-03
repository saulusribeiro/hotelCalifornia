package br.com.hotelCalifornia.api.dto;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class HotelCaliforniaDto {
	
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	
	@NotNull(message="Nome do Hotel é obrigatório")
	private String name;

	@NotNull(message="Nome do Local é obrigatório")
	private String local;
	
	@NotNull(message="Capacidade do Hotel é obrigatório")
	private int capacidade;
	
	@NotNull(message="CNPJ é obrigatório")
	@Size(min=1, max=14, message="CNPJ deve ter no máximo 14 caracteres")
	private String cnpj;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public HotelCaliforniaDto() {
		 
	}
		

}
