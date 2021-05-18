package com.Teknisi.services;

import java.util.List;

import com.Teknisi.model.TeknisiPhoto;

public interface TeknisiPhotoService {
	List<TeknisiPhoto> showAllTeknisiPhoto();
	void insert(TeknisiPhoto tekphoto);
	void deleteById(Long id);
	void updateTeknisiPhoto(TeknisiPhoto tekphoto);
	TeknisiPhoto getTeknisiPhotoById(long id);
	boolean TeknisiPhototIdExists(long id);
}
