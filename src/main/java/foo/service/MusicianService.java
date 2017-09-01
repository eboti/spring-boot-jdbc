package foo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import foo.domain.Musician;

@Service
public interface MusicianService {

	List<Musician> findMusicians(String name);
	
	List<Musician> retrieveAllMusicians();
	
	void addMusician(String name, String email);
	
}
