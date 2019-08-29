package com.bridgeit.fundoo.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private Long userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String mobileNumber;
	private boolean isVerify;
	private LocalDateTime registerDate = LocalDateTime.now();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNum() {
		return mobileNumber;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNumber = mobileNum;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", password=" + password + ", mobileNum=" + mobileNumber + ", isVerify=" + isVerify
				+ ", registerDate=" + registerDate + "]";
	}

	/*
	 * @OneToMany(cascade = CascadeType.ALL) private List<Note> notes;
	 * 
	 * public List<Note> getNotes() { return notes; }
	 * 
	 * public void setNotes(List<Note> notes) { this.notes = notes; }
	 * 
	 * @JsonIgnore
	 * 
	 * @ManyToMany(cascade = CascadeType.ALL) private Set<Note> collaboratedNotes;
	 * 
	 * public Set<Note> getCollaboratedNotes() { return collaboratedNotes; }
	 * 
	 * public void setCollaboratedNotes(Set<Note> collaboratedNotes) {
	 * this.collaboratedNotes = collaboratedNotes; }
	 */
}
