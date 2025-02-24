package br.com.hotelCalifornia.infraestructure.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name="hotel_california")
public class HotelCaliforniaModel implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
//	private UUID id;
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="local")
	private String local;
	
	@Column(name="capacidade")
	private int capacidade;
	
	@Column(name="cnpj")
	private String cnpj;

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

	public HotelCaliforniaModel() {
		 
	}

	public HotelCaliforniaModel(String name, String local, int capacidade, String cnpj) {
		this.name = name;
		this.local = local;
		this.capacidade = capacidade;
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capacidade, cnpj, local, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelCaliforniaModel other = (HotelCaliforniaModel) obj;
		return capacidade == other.capacidade && Objects.equals(cnpj, other.cnpj) && Objects.equals(local, other.local)
				&& Objects.equals(name, other.name);
	}
	
	
	

}
