package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoDao {
	List<TeknisiPhoto> getAllTeknisiPhoto();
	void insert(TeknisiPhoto teknisiPhoto);
	int deleteById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto);
	public TeknisiPhoto findTeknisiPhotoById(long id);
	boolean TeknisiPhotoIdExists(long id);
}
