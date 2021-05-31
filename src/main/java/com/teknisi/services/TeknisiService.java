package com.teknisi.services;

import java.util.List;

import com.teknisi.model.Teknisi;

public interface TeknisiService {
	List<Teknisi> showAllTeknisi();
	void insert(Teknisi teknisi);
	void deleteById(Long id);
	void updateTeknisi(Teknisi teknisi);
	List<Teknisi> getTeknisiById(Long id);
	boolean TeknisiIdExists(Long id);
}
