package com.bridgeit.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LabelDTO {

	@NotNull
	@NotEmpty(message = "Enter labelName")
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
}