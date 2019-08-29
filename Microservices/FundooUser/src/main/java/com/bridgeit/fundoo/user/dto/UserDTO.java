package com.bridgeit.fundoo.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserDTO {
	@NotEmpty(message = "Enter firstName")
	@Length(min = 3, max = 32, message = "length :3 to 32 characters")
	private String firstName;
	

    @NotEmpty(message = "Enter lastName")
    @Length(min = 3, max = 32, message = "length :3 to 32 characters")
	private String lastName;
	
    @NotEmpty(message = "Enter email address")
    @Pattern(regexp = "^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$", message = "enetr valid email address..!")
	private String emailId;
	
    @NotEmpty(message = "Enter password ...!")
    @Length(min = 8, max = 32, message = "length :6 to 32 characters")
	private String password;
	
//    @NotEmpty(message = "Enter mobile number")
	// @Size(min=10, message="Mobile number should have digit")
    @NotNull
	private String mobileNumber;

	
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", password="
				+ password + ", mobileNum=" + mobileNumber + "]";
	}

}
