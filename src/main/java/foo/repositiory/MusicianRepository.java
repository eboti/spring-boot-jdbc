package foo.repositiory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import foo.domain.Musician;

@Log4j
@Repository
public class MusicianRepository {

	private static final String SELECT_QUERY = "SELECT id, name, email, created_date FROM musician";
	private static final String SELECT_QUERY_BY_NAME = "SELECT id, name, email, created_date FROM musician where name = ?";
	private static final String INSERT_QUERY = "INSERT INTO musician (name, email, created_date) VALUES (?,?,?)";

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Musician> findAll() {

		List<Musician> result = jdbcTemplate.query(
				SELECT_QUERY,
				(rs, rowNum) -> Musician.builder()
						.id(rs.getLong("id"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.date(rs.getDate("created_date")).build());

		return result;
	}

	public List<Musician> findMusician(String name) {
		List<Musician> result = new ArrayList<Musician>();

		try (Connection connection = dataSource.getConnection(
				env.getProperty("spring.datasource.username"),
				env.getProperty("spring.datasource.password"));
				PreparedStatement ps = connection.prepareStatement(SELECT_QUERY_BY_NAME);) {
			
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				result.add(Musician.builder()
						.id(rs.getLong("id"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.date(rs.getDate("created_date")).build());
			}
			
			return result;
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	public void addMusician(String name, String email) {
		jdbcTemplate.update(INSERT_QUERY, name, email, new Date());
	}
}