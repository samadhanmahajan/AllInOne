package com.bridgelabz.movie.DTO;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

public class ProducerDTO {
	@NotEmpty
	String name;
	@NotEmpty
	Date dob;
	@NotEmpty
	String sex;
	String bio;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	@Override
	public String toString() {
		return "ProducerDTO [name=" + name + ", dob=" + dob + ", sex=" + sex + ", bio=" + bio + "]";
	}
	
}
