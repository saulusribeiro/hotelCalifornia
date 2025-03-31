package br.com.hotelCalifornia.infraestructure.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import lombok.RequiredArgsConstructor;

@Repository
public interface HotelCaliforniaRepository extends JpaRepository<HotelCaliforniaModel, Long> {
	
	@Query(value="SELECT * from hotel_california where cnpj =:cnpj", nativeQuery = true)
	Optional<HotelCaliforniaModel> acharPorCnpj(@PathVariable(value = "cnpj")String cnpj);
	
	@Query(value="SELECT * from hotel_california where local =:local", nativeQuery = true)
	Optional<HotelCaliforniaModel> acharPorLocal(@PathVariable(value = "local")String local);


}
