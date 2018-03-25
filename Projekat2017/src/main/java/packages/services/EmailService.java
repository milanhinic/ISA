package packages.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import packages.beans.Korisnik;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment environment;
	
	@Async
	public void sendConfirmationMail(Korisnik korisnik) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setFrom(environment.getProperty("spring.mail.username"));
		helper.setTo(korisnik.getEmail());
		helper.setSubject("Isa Pozorista i Biskopi potvrda naloga");
		String link = "http://localhost:8081/bioskopi-pozorista.com/app/aktivirajNalog/"+korisnik.getId();
		String mailMessage = "<html><body><p>Pozdrav " +korisnik.getIme()+",<br>Da biste aktivirali vas nalog kliknite ovaj link: "+link+"</p></body></html>";
		helper.setText(mailMessage,true);
		
		mailSender.send(message);
		
		
	}
	
	
}
