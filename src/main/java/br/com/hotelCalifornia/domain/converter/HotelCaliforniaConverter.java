package br.com.hotelCalifornia.domain.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.com.hotelCalifornia.api.dto.HotelCaliforniaDto;
import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;

public class HotelCaliforniaConverter {
	
	
  	@SuppressWarnings("unused")
	public HotelCaliforniaModel toModel(HotelCaliforniaDto dto) {
  		
  		 		
  		
    	HotelCaliforniaModel hotel = new HotelCaliforniaModel();
    	BeanUtils.copyProperties(dto, hotel);
    	
    	return hotel;
    	
    	
  	}
  	
 	@SuppressWarnings("unused")
	public HotelCaliforniaDto toDto(HotelCaliforniaModel hotel) {
  		HotelCaliforniaDto dto = new HotelCaliforniaDto();
    	BeanUtils.copyProperties(hotel, dto);
    	
    	return dto;
  		
  	}
 	
 	
	public List<HotelCaliforniaDto> toDtoList(List<HotelCaliforniaModel> listaModel) {
 	    return listaModel.stream()
 	                     .map(model -> toDto(model))  // Usando uma expressão mais explícita
 	                     .collect(Collectors.toList());
 	}

	public HotelCaliforniaDto toEntityUpdate(HotelCaliforniaModel hotelExist, HotelCaliforniaDto hotelCaliforniaDto,
			String cnpj) {
		// TODO Auto-generated method stub
		return null;
	}



}
