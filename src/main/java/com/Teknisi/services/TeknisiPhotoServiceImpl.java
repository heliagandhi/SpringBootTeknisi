package com.Teknisi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Teknisi.dao.TeknisiPhotoDao;
import com.Teknisi.model.TeknisiPhoto;

@Service
public class TeknisiPhotoServiceImpl implements TeknisiPhotoService{

	@Autowired TeknisiPhotoDao teknisiPhotoDao;
	
	@Override
	public List<TeknisiPhoto> showAllTeknisiPhoto() {
		return teknisiPhotoDao.getAllTeknisiPhoto();
	}

	@Override
	public TeknisiPhoto getTeknisiPhotoById(Long id) {
		return teknisiPhotoDao.findTeknisiPhotoById(id);
	}

	@Override
	public void insertTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		teknisiPhotoDao.insert(teknisiPhoto, fileName, fileType, base64);
	}
	
	@Override
	public void updateTeknisiPhoto(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		teknisiPhotoDao.update(teknisiPhoto, fileName, fileType, base64);
	}

	@Override
	public void deleteTeknisiPhotoById(Long id) {
		teknisiPhotoDao.deleteById(id);
	}

	@Override
	public boolean TeknisiPhotoIdExists(Long id) {
		return teknisiPhotoDao.TeknisiPhotoIdExists(id);
	}

	@Override
	public boolean TeknisiPhotoIdOrTeknisiIdExists(Long id, long teknisi_id) {
		return teknisiPhotoDao.TeknisiPhotoIdOrTeknisiIdExists(id, teknisi_id);
	}
	
	@Override
	public boolean TeknisiPhotoIdAndTeknisiIdExists(Long id, long teknisi_id) {
		return teknisiPhotoDao.TeknisiPhotoIdAndTeknisiIdExists(id, teknisi_id);
	}

}
