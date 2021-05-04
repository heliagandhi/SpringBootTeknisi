package com.Teknisi.dao;

import java.util.List;
import com.Teknisi.model.Teknisi;

public interface TeknisiDao {
	List<Teknisi> getAllTeknisi();
	void insert(Teknisi teknisi);
	int deleteById(Long id);
	void updateTeknisi(Teknisi teknisi);
	public Teknisi findTeknisiById(long id);
}