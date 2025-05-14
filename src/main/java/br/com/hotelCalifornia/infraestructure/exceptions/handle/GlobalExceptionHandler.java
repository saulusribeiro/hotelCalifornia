package br.com.hotelCalifornia.infraestructure.exceptions.handle;

import java.time.LocalDateTime;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.hotelCalifornia.infraestructure.exceptions.BadRequestException;
import br.com.hotelCalifornia.infraestructure.exceptions.BusinessException;
import br.com.hotelCalifornia.infraestructure.exceptions.ConflictException;
import br.com.hotelCalifornia.infraestructure.exceptions.UnprocessableEntityException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusResponseEntity( BusinessException ex, HttpServletRequest request){
		return response(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflictException( ConflictException cf, HttpServletRequest request){
		return response(cf.getMessage(), request, HttpStatus.CONFLICT, LocalDateTime.now());
	}
	
	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<ErrorResponse> handleUnprocessableEntityException( UnprocessableEntityException unp, HttpServletRequest request){
		return response(unp.getMessage(), request, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now());
	}


	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handlBadRequestException(BadRequestException brq, HttpServletRequest request){
		return response(brq.getMessage(), request, HttpStatus.BAD_REQUEST, LocalDateTime.now());
	}

	private ResponseEntity<ErrorResponse> response(final String message, final HttpServletRequest request,
			final HttpStatus status, LocalDateTime data) {
		return ResponseEntity.status(status)
				.body(new ErrorResponse(message, data, status.value(), request.getRequestURI()));
	}

}
