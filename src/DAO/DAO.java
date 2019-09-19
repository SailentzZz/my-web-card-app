package DAO;

import java.util.List;

import Models.MessageBody;

public interface DAO {

	public List<MessageBody> getAll();
	public MessageBody getOne();
	public boolean add(MessageBody messageBody);
	
}
