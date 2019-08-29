package com.bridgeit.fundoo.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundoo.note.model.Label;

@Repository
public interface LabelRespository extends JpaRepository<Label,Long> {
	
	public Label findByLabelIdAndUserId(long lableId , long userId);
	public Optional<Label> findByUserIdAndLabelName(long userId, String labelName);
	public List<Label> findByUserId(long UserId);
	public Optional<Label> findByUserIdAndLabelName(long userId, long labelId);

}
