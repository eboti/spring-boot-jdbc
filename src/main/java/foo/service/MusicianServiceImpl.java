package foo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foo.domain.Musician;
import foo.repositiory.MusicianRepository;

@Service
public class MusicianServiceImpl implements MusicianService {

	@Autowired
	private MusicianRepository musicianRepository;

	@Override
	public List<Musician> retrieveAllMusicians() {
		return musicianRepository.findAll();
	}

	@Override
	public List<Musician> findMusicians(String name) {
		return musicianRepository.findMusician(name);
	}

	@Override
	public void addMusician(String name, String email) {
		musicianRepository.addMusician(name, email);
	}
}
