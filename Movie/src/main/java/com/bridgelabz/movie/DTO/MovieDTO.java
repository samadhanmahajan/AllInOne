package com.bridgelabz.movie.DTO;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class MovieDTO {
	@NotEmpty
	String name;
	@NotEmpty
	Date releseYear;
	@NotEmpty
	long producerID;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getReleseYear() {
		return releseYear;
	}
	public void setReleseYear(Date releseYear) {
		this.releseYear = releseYear;
	}
	public long getProducerID() {
		return producerID;
	}
	public void setProducerID(long producerID) {
		this.producerID = producerID;
	}
	@Override
	public String toString() {
		return "MovieDTO [name=" + name + ", releseYear=" + releseYear + ", producerID=" + producerID + "]";
	}
	
}
