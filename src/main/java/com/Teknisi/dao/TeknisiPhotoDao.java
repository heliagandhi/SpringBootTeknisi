package com.Teknisi.dao;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoDao {
	List<TeknisiPhoto> getAllTeknisiPhoto();
	public TeknisiPhoto findTeknisiPhotoById(long id);
	void insert(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void update(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	int deleteById(Long id);
	boolean TeknisiPhotoIdExists(long id);
	boolean TeknisiPhotoIdAndTeknisiIdExists(Long id, long teknisi_id);
	boolean TeknisiPhotoIdOrTeknisiIdExists(Long id, long teknisi_id);
}
