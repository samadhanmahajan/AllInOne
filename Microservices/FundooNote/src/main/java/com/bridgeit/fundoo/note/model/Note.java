package com.bridgeit.fundoo.note.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
public class Note implements Serializable {

	private static final long serialVersionUID = 3891749725421598901L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long noteId;
	private String title;
	private String description;
	private String colour;
	private LocalDateTime created;
	private LocalDateTime modified;
	private boolean isTrash;
	private boolean isArchive;
	private boolean isPin;
	private boolean isDeletePermantely;
	private LocalDateTime setReminder;
	private long userId;

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isDeletePermantely() {
		return isDeletePermantely;
	}

	public void setDeletePermantely(boolean isDeletePermantely) {
		this.isDeletePermantely = isDeletePermantely;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public LocalDateTime getSetReminder() {
		return setReminder;
	}

	public void setSetReminder(LocalDateTime setReminder) {
		this.setReminder = setReminder;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", colour=" + colour
				+ "]";
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> label;

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	/*
	 * @ManyToMany(cascade = CascadeType.ALL) private Set<User> collaboratedUser;
	 * 
	 * public Set<User> getCollaboratedUser() { return collaboratedUser; }
	 * 
	 * public void setCollaboratedUser(Set<User> collaboratedUser) {
	 * this.collaboratedUser = collaboratedUser; }
	 */

}
