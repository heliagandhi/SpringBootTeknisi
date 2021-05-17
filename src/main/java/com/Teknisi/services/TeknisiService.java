package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.Teknisi;

public interface TeknisiService {
	List<Teknisi> showAllTeknisi();
	void insert(Teknisi teknisi);
	void deleteById(Long id);
	void updateTeknisi(Teknisi teknisi);
	List<Teknisi> getTeknisiById(long id);
	boolean TeknisiIdExists(long id);
}
