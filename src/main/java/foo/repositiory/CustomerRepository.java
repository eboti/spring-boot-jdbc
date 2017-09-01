package foo.repositiory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import foo.domain.Customer;

@Repository
public class CustomerRepository {

	private static final String SELECT_QUERY = "SELECT id, name, email, created_date FROM customer";
	private static final String INSERT_QUERY = "INSERT INTO customer(name, email, created_date) VALUES (?,?,?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Customer> findAll() {

		List<Customer> result = jdbcTemplate.query(
				SELECT_QUERY,
				(rs, rowNum) -> Customer.builder()
					.id(rs.getLong("id"))
					.name(rs.getString("name"))
					.email(rs.getString("email"))
					.date(rs.getDate("created_date"))
					.build());

		return result;
	}

	public void addCustomer(String name, String email) {
		jdbcTemplate.update(INSERT_QUERY, name, email, new Date());
	}
}