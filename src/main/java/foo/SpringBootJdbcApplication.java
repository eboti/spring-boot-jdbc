package foo;

import lombok.extern.log4j.Log4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import foo.service.MusicianService;

@Log4j
@SpringBootApplication
public class SpringBootJdbcApplication {

	private final MusicianService musicianServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcApplication.class, args);
	}

	public SpringBootJdbcApplication(MusicianService musicianServiceImpl) {
		this.musicianServiceImpl = musicianServiceImpl;
		
		doSpringJdbcTemplateQuery();
		doPlainJdbcQuery();
	}

	public void doSpringJdbcTemplateQuery() {

		musicianServiceImpl.addMusician("Frank Sinatra", "frank@asdf.hu");

		musicianServiceImpl.retrieveAllMusicians().forEach(
				musician -> log.debug(musician));
	}
	
	public void doPlainJdbcQuery(){
		
		musicianServiceImpl.findMusicians("Frank Sinatra").forEach(
				musician -> log.debug(musician));		
	}
}
