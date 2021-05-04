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
	public void showAllTeknisi() {
		List<Teknisi> listTek = teknisiDao.getAllTeknisi();
		for(Teknisi teknisi: listTek){
			System.out.println(teknisi.toString());
		}
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
	public void getTeknisiById(long id) {
		Teknisi teknisi = teknisiDao.findTeknisiById(id);
		System.out.println(teknisi);
	}

}

