package conrollers;
 
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import DAO.DAO;
import Models.MessageBody;
import Service.EmailService;

@Controller
public class DispatcherHelloController2 {
	
	private static Logger logger = Logger.getLogger(DispatcherHelloController2.class);
	
	@Autowired
	private DAO dao;

	@Autowired
	private EmailService emailService;
 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String myCardPage(ModelAndView mvc) {	

		return "index";
		
	}
	
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ModelAndView contactWithMe(@ModelAttribute() MessageBody messageBody, RedirectAttributes redir) throws IOException, ServletException {
		
		messageBody.setDate(new Date());
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
		logger.info(messageBody.toString());
		logger.info(messageBody.getMessage());
		Map<String, String> err = validate(messageBody, validator);	
		
		if (err == null) {			
			boolean emailflag = emailService.sendSimpleMessage(messageBody.getEmail(),
					"Security", "Thanks for contact with me!\nThis is my relevant email: mishatravin99@gmail.com, if you need direct contact with me.");
			
			if (emailflag) {				
				boolean flag = dao.add(messageBody);
				logger.info(flag);
				
				if (flag) {				
					MessageBody body = dao.getOne();
					emailService.sendSimpleMessage("mishatravin99@gmail.com", "Attach!", "Contact: " + body.toString());
					
					redir.addFlashAttribute("message", "<script>alert('Thanks!')</script>");
					ModelAndView modelAndView = new ModelAndView();
					modelAndView.setViewName("redirect:/");
					return modelAndView;
					
				} else {
					redir.addFlashAttribute("message", "<script>alert('Incorrect input')</script>");
					ModelAndView modelAndView = new ModelAndView();
					modelAndView.setViewName("redirect:/");
					return modelAndView;		
				}
				
			} else {
				redir.addFlashAttribute("message", "<script>alert('Incorrect email')</script>");
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("redirect:/");
				return modelAndView;		
			}
			
		} else {
			redir.addFlashAttribute("message", "<script>alert('Incorrect input')</script>");
			redir.addFlashAttribute("msgErr", err);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		
	}
	
    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public RedirectView generateReport(HttpServletResponse response) throws Exception {
    	
        return new RedirectView("https://drive.google.com/open?id=1wRTQs1OVPv16q3vMaWRAXGetbGDEV_uS");
    }
    
    @RequestMapping(value = "/participation", method = RequestMethod.GET)
    public RedirectView generatePart(HttpServletResponse response) throws Exception {
    	
        return new RedirectView("https://drive.google.com/open?id=1q-dsJ6noCOqa46qifCoPMFYvX2JgpAxf");
    }
	
	public Map<String, String> validate(Object object, Validator validator) throws IOException, ServletException {
	    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
	    String msgName = "";
	    String msgEmail = "";
	    String msgMessage = "";
	    logger.info(String.format("Errors: %d", constraintViolations.size()));
	    
	    if(constraintViolations.size() == 0) {
	    	logger.info("Validation complete");
	    	return null;
	    	
	    } else {
	    	Map<String, String> err = new HashMap<>();
		    for (ConstraintViolation<Object> cv : constraintViolations) {	
		    	if (cv.getPropertyPath().toString().equals("FromName")) {
		    		msgName += cv.getMessage() + "<br>";
		    		err.put("nameErr", msgName);		    		
		    		logger.info(err.get("nameErr"));	 
		    	}
		    	if (cv.getPropertyPath().toString().equals("Message")) {
		    		msgMessage += cv.getMessage() + "<br>";
		    		err.put("messageinErr", msgMessage);		    		
		    		logger.info(err.get("messageErr"));
		    	}
		    	if (cv.getPropertyPath().toString().equals("Email")) {
		    		msgEmail += cv.getMessage() + "<br>";
		    		err.put("EmailErr", msgEmail);		    		
		    		logger.info(err.get("EmailErr"));
		    	}
		    }
		    
		    return err;
	    }
	}
}