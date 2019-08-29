package com.bridgelabz.fundoo.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.model.EmailInfo;

@Component
public class MailServiceUtility {
		
		@Autowired
		private JavaMailSender javaMailSender;	
		
		@Autowired
		private TokenUtility userToken;	
		
		/**
		 * Purpose : Method to send email 
		 * @param emailInfo
		 */

		public void send(EmailInfo emailInfo) {
			
			SimpleMailMessage message = new SimpleMailMessage(); 
		    message.setTo(emailInfo.getTo()); 
		    message.setSubject(emailInfo.getSubject()); 
		    message.setText(emailInfo.getBody());
		    
		    javaMailSender.send(message);

		}
		
		/**
		 * Purpose :function to generate validation link
		 * @param link : Passing the link of user 
		 * @param id : Passing the user id to create token for that particular userId 
		 * @return : Return validation link
		 * @throws IllegalArgumentException
		 * @throws UnsupportedEncodingException
		 * @return verification link
		 */
		public String getLink(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException 
		{
			return link+userToken.genetateToken(id);
		}

}
