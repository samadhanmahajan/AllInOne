
package com.bridgeit.fundoo.note.service;

import java.util.List;

import com.bridgeit.fundoo.note.dto.LabelDTO;
import com.bridgeit.fundoo.note.model.Label;
import com.bridgeit.fundoo.response.Response;

public interface LabelService {

	Response createLabel(LabelDTO labeldto, long userId);
	String getToken(String email);
    Response deleteLabel(long userId, long labelId);

}
