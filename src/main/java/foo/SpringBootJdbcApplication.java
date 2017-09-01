package foo;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import foo.service.CustomerService;

@Log4j
@SpringBootApplication
public class SpringBootJdbcApplication {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomerService customerServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcApplication.class, args);
	}

	@PostConstruct
	public void init(){
		customerServiceImpl.retrieveAllCustomers().forEach(customer -> log.debug(customer));
	}

}
