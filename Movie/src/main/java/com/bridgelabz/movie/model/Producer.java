package com.bridgelabz.movie.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Entity
@Data
public class Producer {
	@Id
	long producerID;
	@NotEmpty
	String name;
	@NotEmpty
	String sex;
	@NotEmpty
	Date dob;
	String bio;
	@OneToMany
	List<Movie> movies;
	public long getProducerID() {
		return producerID;
	}
	public void setProducerID(long producerID) {
		this.producerID = producerID;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
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
		return "Producer [producerID=" + producerID + ", name=" + name + ", sex=" + sex + ", dob=" + dob + ", bio="
				+ bio + ", movies=" + movies + "]";
	}
	
}
