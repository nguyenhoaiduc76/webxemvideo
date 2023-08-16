package com.poly.service.impl;

import javax.servlet.ServletContext;

import com.poly.entity.User;
import com.poly.service.EmailService;
import com.poly.utils.SendEmailUtil;

public class EmailServiceImpl implements EmailService {
	
	private static final String EMAIL_WELCOME_SUBJECT = "Welcome to Online Entertainment";
	private static final String EMAIL_FORGOT_PASSWORD = "Online Entertainment - Your New Password";

	@Override
	public void sendEmail(ServletContext context, User recipient, String type) {
		String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String user = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");
        
        try {
			String subject = null;
			String content = null;
			switch(type) {
				case "welcome":
					subject = EMAIL_WELCOME_SUBJECT;
					content = "Dear,"+recipient.getUsername()+"Welcome to my website^^";
					break;
				case "forgot":
					subject = EMAIL_FORGOT_PASSWORD;
					content = "Dear,"+recipient+"Your new password here :"+recipient.getPassword();
					break;
				default :
					subject = "Online entertainment";
					content = "this is email is wrong , don't care about it^^";
			}
			SendEmailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
