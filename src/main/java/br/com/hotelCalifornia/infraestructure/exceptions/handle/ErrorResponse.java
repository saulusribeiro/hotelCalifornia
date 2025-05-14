package br.com.hotelCalifornia.infraestructure.exceptions.handle;

import java.time.LocalDateTime;
import java.util.Objects;


public class ErrorResponse {
	
	private String message;
	private LocalDateTime data;
	private int status;
	private String path;
	
	public ErrorResponse() {
		
	}
	public ErrorResponse(String message, LocalDateTime data, int status, String path) {
		
		this.message = message;
		this.data = data;
		this.status = status;
		this.path = path;
	}
	@Override
	public int hashCode() {
		return Objects.hash(data, message, path, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorResponse other = (ErrorResponse) obj;
		return Objects.equals(data, other.data) && Objects.equals(message, other.message)
				&& Objects.equals(path, other.path) && status == other.status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
