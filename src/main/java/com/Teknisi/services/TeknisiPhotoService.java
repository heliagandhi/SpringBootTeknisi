package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoService {
	List<TeknisiPhoto> showAllTeknisiPhoto();
	TeknisiPhoto getTeknisiPhotoById(Long id);
	void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64);
	void deleteTeknisiPhotoById(Long id);
	boolean TeknisiPhotoIdExists(Long id);
	boolean TeknisiPhotoIdOrTeknisiIdExists(Long id, long teknisi_id);
	boolean TeknisiPhotoIdAndTeknisiIdExists(Long id, long teknisi_id);
}
