package packages.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import packages.beans.Korisnik;
import packages.beans.Rezervacija;

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
	
	@Async
	public void sendRezervacijaMail(ArrayList<Rezervacija> rezervacije) throws MessagingException{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setFrom(environment.getProperty("spring.mail.username"));
		helper.setTo(rezervacije.get(0).getRegKorisnik().getReg_korisnik_id().getEmail());
		helper.setSubject("Isa Pozorista i Biskopi vasa rezeracija");
		String datumProjekcije = new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Timestamp(rezervacije.get(0).getKarta().getProjekcija().getDatum().getTime()));
		
		String rezPodaci = "";
		for(Rezervacija rezervacija : rezervacije) {
			
			rezPodaci+="<tr>"+
					" <td>"+rezervacija.getKarta().getProjekcija().getPredFilm().getNaziv()+"</td>" + 
					"            <td>"+rezervacija.getKarta().getProjekcija().getSala().getPozBio().getNaziv()+"</td>" + 
					"            <td>" + rezervacija.getKarta().getProjekcija().getSala().getNaziv()+"</td>"+ 
					"            <td>RSD "+rezervacija.getKarta().getSediste().getSegment().getTip().getCena()+" "+rezervacija.getKarta().getSediste().getSegment().getTip().getNaziv()+"</td>" + 
					"            <td>"+rezervacija.getKarta().getSediste().getId()+"</td>" + 
					"            <td>"+datumProjekcije+"</td>"						
					+"</tr>";	
		}
		
		String mailMessage = "<html><body><p>Postovani "+rezervacije.get(0).getRegKorisnik().getReg_korisnik_id().getIme()+", ovo su podaci o vasoj rezervaciji: <br>"
				+"<table><tr>" + 
				"          <th>Film/Predstava</th>" + 
				"          <th>Bioskop/Pozoriste</th>"+ 
				"          <th>Sala</th>" + 
				"          <th>Cena</th>" + 
				"          <th>Sifra mesta</th>" + 
				"          <th>Datum</th>" + 
				"        </tr>"+
				
				
				rezPodaci+
				
				"</table><br></p><p>Vasu rezervaciju mozete otkazati do pola sata pred pocetak predstave ili projekcije"
				+ "</p></body></html>";
			helper.setText(mailMessage, true);
			
			mailSender.send(message);
	}
	
	@Async
	public void sendprijateljMail(Rezervacija rezervacija, Korisnik pozivalac) throws MessagingException{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setFrom(environment.getProperty("spring.mail.username"));
		helper.setTo(rezervacija.getRegKorisnik().getReg_korisnik_id().getEmail());
		helper.setSubject("Isa Pozorista i Biskopi poziv na projekciju/predstavu");
		String link = "http://localhost:8081/bioskopi-pozorista.com/app/odbijPoziv/"+rezervacija.getId();
		
		String datumProjekcije = new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Timestamp(rezervacija.getKarta().getProjekcija().getDatum().getTime()));
		String mailMessage = "<html><body><p>Postovani "+rezervacija.getRegKorisnik().getReg_korisnik_id().getIme()+", korisnik "+pozivalac.getIme()+" "+pozivalac.getPrezime()+ " pozvao/la vas je da zajedno prisustvujete projekciji/predstavi: <br>"
				+"<table><tr>" + 
				"          <th>Film/Predstava</th>" + 
				"          <th>Bioskop/Pozoriste</th>"+ 
				"          <th>Sala</th>" + 
				"          <th>Cena</th>" + 
				"          <th>Sifra mesta</th>" + 
				"          <th>Datum</th>" + 
				"        </tr>"+
				"<tr>"+
				" <td>"+rezervacija.getKarta().getProjekcija().getPredFilm().getNaziv()+"</td>" + 
				"            <td>"+rezervacija.getKarta().getProjekcija().getSala().getPozBio().getNaziv()+"</td>" + 
				"            <td>" + rezervacija.getKarta().getProjekcija().getSala().getNaziv()+"</td>"+ 
				"            <td>RSD "+rezervacija.getKarta().getSediste().getSegment().getTip().getCena()+" "+rezervacija.getKarta().getSediste().getSegment().getTip().getNaziv()+"</td>" + 
				"            <td>"+rezervacija.getKarta().getSediste().getId()+"</td>" + 
				"            <td>"+datumProjekcije+"</td>"						
				+"</tr></table><br></p><p>Vasu rezervaciju mozete otkazati do pola sata pred pocetak predstave ili projekcije posecivanjem ovog linka <br>"+link
				+ "</p></body></html>";
			helper.setText(mailMessage, true);
			
			mailSender.send(message);
		
		
	}
	
	
}
