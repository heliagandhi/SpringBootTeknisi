package com.teknisi.dao;

import java.util.List;

import com.teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoDao {
	List<TeknisiPhoto> getAllTeknisiPhoto();
	public TeknisiPhoto findTeknisiPhotoById(long id);
	void insert(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void update(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	int deleteById(Long id);
	boolean TeknisiPhotoIdExists(Long id);
	boolean TeknisiPhotoIdAndTeknisiIdExists(Long id, Long teknisi_id);
	boolean TeknisiPhotoIdOrTeknisiIdExists(Long id, Long teknisi_id);
}
