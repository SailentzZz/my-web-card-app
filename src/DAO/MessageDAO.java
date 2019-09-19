package DAO;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Models.MessageBody;

@Component
public class MessageDAO implements DAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<MessageBody> getAll() {
		List<MessageBody> list = sessionFactory.openSession().createQuery("from MessageBody", MessageBody.class).list();
		if (list != null) return list;
		else return null;
	}

	@Override
	public MessageBody getOne() {
		Query query = sessionFactory.openSession().createQuery("from MessageBody order by id DESC", MessageBody.class);
		query.setMaxResults(1);
		MessageBody messageBody = (MessageBody) query.uniqueResult();
		if (messageBody != null) return messageBody;
		else return null;
	}

	@Override
	public boolean add(MessageBody messageBody) {
		try {
			sessionFactory.openSession().save(messageBody);
			return true;
		} catch (Exception e) {
		}
		return false;		
	}	
	
}
