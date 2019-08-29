package com.bridgeit.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NoteDTO {

	@NotNull
	@NotEmpty(message = "Enter title")
	private String title;
	
	@NotNull
	@NotEmpty(message = "Enter description")
	private String description;
	
	
	public NoteDTO(@NotNull @NotEmpty(message = "Enter title") String title,
			@NotNull @NotEmpty(message = "Enter description") String description) {
		super();
		this.title = title;
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Notedto [title=" + title + ", description=" + description + "]";
	}
	
	
}
