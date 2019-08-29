package com.bridgelabz.movie.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Entity
@Data
public class Actor {
	@Id
	long actorID;
	@NotEmpty
	String name;
	@NotEmpty
	String sex;
	@NotEmpty
	Date birthDate;
	String bio;
	@ManyToMany(mappedBy = "actors")
	List<Movie> movies;
	public long getActorID() {
		return actorID;
	}
	public void setActorID(long actorID) {
		this.actorID = actorID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	@Override
	public String toString() {
		return "Actor [actorID=" + actorID + ", name=" + name + ", sex=" + sex + ", birthDate=" + birthDate + ", bio="
				+ bio + ", movies=" + movies + "]";
	}
	
}
