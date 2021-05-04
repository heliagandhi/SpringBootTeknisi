package com.Teknisi.services;

import com.Teknisi.model.Teknisi;

public interface TeknisiService {
	void showAllTeknisi();
	void insert(Teknisi teknisi);
	void deleteById(Long id);
	void updateTeknisi(Teknisi teknisi);
	void getTeknisiById(long id);
}
