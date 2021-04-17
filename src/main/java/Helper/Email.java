/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import com.sun.corba.se.impl.oa.toa.TOA;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dedhi
 */
public class Email {
    public static void main(String args[]) {
    }
    
    public void sendEmail(){
    String configEmail = "email-bean.xml";
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(configEmail);
 
		// @Service("crunchifyEmail") <-- same annotation you specified in CrunchifyEmailAPI.java
		ApproveEmail approveEmail = (ApproveEmail) context.getBean("approveEmail");
		String toAddr = "dedhiadimpi@gmail.com";
		String fromAddr = "dedhiadimpi@gmail.com";
		// email subject
		String subject = "Hey.. This email sent by Crunchify's Spring MVC Tutorial";
		// email body
		String body = "There you go.. You got an email.. Let's understand details on how Spring MVC works -- By Crunchify Admin";
		approveEmail.sendEmail(toAddr, fromAddr, subject, body);
	}
    
}
