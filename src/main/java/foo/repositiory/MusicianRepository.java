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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import foo.domain.Musician;

@Log4j
@Repository
public class MusicianRepository {

	private static final String SELECT_QUERY_COUNT_BY_NAME = "select count(*) from musician where name = :name";
	private static final String SELECT_QUERY = "select id, name, email, created_date from musician";
	private static final String SELECT_QUERY_BY_NAME = "select id, name, email, created_date from musician where name = ?";
	private static final String INSERT_QUERY = "insert into musician (name, email, created_date) values (?,?,?)";

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// spring jdbc template
	public List<Musician> findAll() {

		List<Musician> result = jdbcTemplate.query(
				SELECT_QUERY,
				(rs, rowNum) -> Musician.builder().id(rs.getLong("id"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.date(rs.getDate("created_date")).build());

		return result;
	}

	// plain jdbc
	public List<Musician> findMusician(String name) {
		List<Musician> result = new ArrayList<Musician>();

		try (Connection connection = dataSource.getConnection(
				env.getProperty("spring.datasource.username"),
				env.getProperty("spring.datasource.password"));
				PreparedStatement ps = connection
						.prepareStatement(SELECT_QUERY_BY_NAME);) {

			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				result.add(Musician.builder().id(rs.getLong("id"))
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

	// spring jdbc template
	public void addMusician(String name, String email) {
		jdbcTemplate.update(INSERT_QUERY, name, email, new Date());
	}

	// spring named parameter jdbc template
	public int countMusicians(String name) {
		SqlParameterSource namedParameter = new MapSqlParameterSource("name",
				name);
		return namedParameterJdbcTemplate.queryForObject(
				SELECT_QUERY_COUNT_BY_NAME,
				namedParameter,
				Integer.class);
		// Collections.singletonMap("name", name));
	}
}