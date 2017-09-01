package foo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foo.domain.Customer;
import foo.repositiory.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> retrieveAllCustomers() {
		return customerRepository.findAll();
	}
}
