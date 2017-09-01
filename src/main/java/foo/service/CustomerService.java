package foo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import foo.domain.Customer;

@Service
public interface CustomerService {

	List<Customer> retrieveAllCustomers();
}
