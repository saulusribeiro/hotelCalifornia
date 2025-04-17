package br.com.hotelCalifornia.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelCaliforniaDto {
	
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	
	@NotNull(message="Nome do Hotel é obrigatório")
	@Size(min = 1, max = 100, message = "Nome do hotel deve ter entre 1 e 100 caracteres" )
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
