package com.teknisi.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.teknisi.model.AppUser;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired private JavaMailSender javaMailSender;
	
	@Value("${template.mail.subject: Testing from SpringBoot Teknisi}")
	private String subject;
	
	@Value("${template.mail.message: <table width=\"100%\">\r\n"
			+ "  <tr>\r\n"
			+ "    <td align=\"center\"><img src=\"https://llndcz.stripocdn.email/content/guids/CABINET_2663efe83689b9bda1312f85374f56d2/images/10381620386430630.png\" width=\"100\">\r\n"
			+ "    </td>\r\n"
			+ "  </tr>\r\n"
			+ "  <tr>\r\n"
			+ "    <td align=\"center\"><h2 style=\"Margin:0;line-height:43px;mso-line-height-rule:exactly;font-family:Montserrat, sans-serif;font-size:36px;font-style:normal;font-weight:normal;color:#333333\\\">Thank you!! <br /> you\r\n"
			+ "    have successfully registered</h2>\r\n"
			+ "    </td>\r\n"
			+ "  </tr>\r\n"
			+ "  <tr>\r\n"
			+ "    <td align=\"center\">\r\n"
			+ "      <table border=\"0\" width=\"40%\" height=\"100%\" style=\"border-spacing:0px;width:40% !important;display:inline-table\\\">\r\n"
			+ "        <tr>\r\n"
			+ "        	<td style=\"padding:1;Margin:0;border-bottom:1px solid #CCCCCC;background:none;height:2px;width:100%;margin:0px\"></td>\r\n"
			+ "        </tr>\r\n"
			+ "      </table>\r\n"
			+ "    </td>\r\n"
			+ "  </tr>\r\n"
			+ "</table>}")
	private String message;

	@Override
	public void sendEmail(AppUser appUser) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(appUser.getEmail());
        helper.setSubject(subject);
        helper.setText(message, true);
        javaMailSender.send(msg);
    }
}
