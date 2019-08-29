package com.bridgelabz.movie.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Table
@Data
@Entity
public class Movie {
	@Id()
	long movieID;
	@NotEmpty
	String name;
	@NotEmpty
	Date releseYear;
	String poster;
	@NotEmpty
	long producerID;
	@ManyToMany
	List<Actor> actors;
	public long getMovieID() {
		return movieID;
	}
	public void setMovieID(long movieID) {
		this.movieID = movieID;
	}
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
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public long getProducerID() {
		return producerID;
	}
	public void setProducerID(long producerID) {
		this.producerID = producerID;
	}
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	@Override
	public String toString() {
		return "Movie [movieID=" + movieID + ", name=" + name + ", releseYear=" + releseYear + ", poster=" + poster
				+ ", producerID=" + producerID + ", actors=" + actors + "]";
	}
	
}
