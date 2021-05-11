package com.Teknisi.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Teknisi.dao.TeknisiDao;
import com.Teknisi.model.Teknisi;

@Service
public class TeknisiServiceImpl implements TeknisiService{

	@Autowired TeknisiDao teknisiDao;
	
	
	@Override
	public List<Teknisi> showAllTeknisi() {
		return teknisiDao.getAllTeknisi();
	}

	
	@Override
	public void insert(Teknisi teknisi) {
		teknisiDao.insert(teknisi);	
	}

	
	@Override
	public void deleteById(Long id) {
		teknisiDao.deleteById(id);	
	}
	

	@Override
	public void updateTeknisi(Teknisi teknisi) {
		teknisiDao.updateTeknisi(teknisi);	
	}

	
	@Override
	public Teknisi getTeknisiById(long id) {
		return teknisiDao.findTeknisiById(id);
		
	}


	@Override
	public boolean TeknisiIdExists(long id) {
		return teknisiDao.TeknisiIdExists(id);
	}

}

