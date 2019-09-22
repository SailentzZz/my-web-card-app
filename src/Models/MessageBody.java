package Models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "public.\"MessageInfo\"")
public class MessageBody {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "\"FromName\"")
	@NotNull(message="Name has been input")
	@Size(min = 3, max = 30, message="Length name - max=30/min=3")
	@Pattern(regexp = "^[a-zA-Z0-9$@$!%*?&#^-_.+]+$", message = "Only english letters")
	private String FromName;
	
	@NotNull(message="Email has been input")
	@Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
	      "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
	      "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
	      "+(?:[a-zA-Z]){2,}\\.?)$", 
	      message = "Email not exist")
	@Column(name = "\"Email\"")
	private String Email;
	
	@NotNull(message="Message has been input")
	@Size(min = 3, max = 10000, message="Length name - max=10000/min=3")
	@Pattern(regexp = "^[a-zA-Zа-яА-Я0-9$@$!%*?&#^-_.,+ ]+$", message = "Acessible character a-zA-Zа-яА-Я0-9$@$!%*?&#^-_.,+ ")
	@Column(name = "\"Message\"")
	private String Message;
	
	@Column(name = "date")
	private Date date;
	
	public MessageBody() {}
	
	public MessageBody(int id, String fromName, String email, String message, Date date) {
		super();
		this.id = id;
		FromName = fromName;
		Email = email;
		Message = message;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromName() {
		return FromName;
	}
	public void setFromName(String fromName) {
		FromName = fromName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "MessageBody [id=" + id + ", FromName=" + FromName + ", Email=" + Email + ", Message=" + Message
				+ ", date=" + date + "]";
	}
}
