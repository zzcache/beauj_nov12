package beauj.day03.business;

import beauj.day03.model.Customer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
//This is the default behaviour - CMT
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerBean {

	@Resource SessionContext ctx;

	@PersistenceContext private EntityManager em;

	//Lifecycle methods/hooks
	@PostConstruct
	private void init() { }

	@PreDestroy
	private void destroy() { }

	public Customer updateCustomer(Customer customer) {

		Customer updatedCustomer = em.merge(customer);

		//nothing happens because this is detached
		//customer.setName("abc"); 

		//will update the database if tx commits
		//this is the managed version
		//updatedCustomer.setName("acb");

		return (updatedCustomer);
	}

	//This is the default behaviour
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Customer createCustomer(Customer customer) 
			throws CustomerException {
		//Insert it in to the database

		//tx begins, pc opens

		Customer c = em.find(Customer.class, customer.getCustomerId());
		if (null != c) {
			throw new CustomerException("Customer id exists");
		}

		//customer is new	
		em.persist(customer);
		
		//tx commits, pc closes

		return (customer);
	}
	
}
