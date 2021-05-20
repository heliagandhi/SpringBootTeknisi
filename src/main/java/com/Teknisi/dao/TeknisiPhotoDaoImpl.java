package com.Teknisi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.Teknisi.model.TeknisiPhoto;

@Repository
public class TeknisiPhotoDaoImpl extends JdbcDaoSupport implements TeknisiPhotoDao{
	
	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

	@Override
	public List<TeknisiPhoto> getAllTeknisiPhoto() {
		String query = "select id, teknisi_id, file_type, name, created_date, created_by, images, update_date, update_by from teknisi_photo";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<TeknisiPhoto> teknisiPhotoList = new ArrayList<TeknisiPhoto>();
		
		List<Map<String,Object>> teknisiPhotoRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> teknisiPhotoColumn : teknisiPhotoRows){
			TeknisiPhoto teknisiPhoto = new TeknisiPhoto();
			teknisiPhoto.setId(Long.parseLong(teknisiPhotoColumn.get("id").toString()));
			teknisiPhoto.setTeknisi_id(Integer.parseInt(teknisiPhotoColumn.get("teknisi_id").toString()));
			teknisiPhoto.setFile_type(String.valueOf(teknisiPhotoColumn.get("file_type")));
			teknisiPhoto.setName(String.valueOf(teknisiPhotoColumn.get("name")));
			teknisiPhoto.setCreated_date((Date)(teknisiPhotoColumn.get("created_date")));
			teknisiPhoto.setCreated_by(String.valueOf(teknisiPhotoColumn.get("created_by")));
			teknisiPhoto.setImages(String.valueOf(teknisiPhotoColumn.get("images")));
			teknisiPhoto.setUpdate_date((Date)(teknisiPhotoColumn.get("update_date")));
			teknisiPhoto.setUpdate_by(String.valueOf(teknisiPhotoColumn.get("update_by")));
			teknisiPhotoList.add(teknisiPhoto);
		}
		return teknisiPhotoList;
	}

	@Override
	public TeknisiPhoto findTeknisiPhotoById(long id) {
		String query = "select id, teknisi_id, file_type, name, created_date, created_by, images, update_date, update_by from teknisi_photo "
					 + "where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		@SuppressWarnings("deprecation")
		TeknisiPhoto teknisiPhoto = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<TeknisiPhoto>(){

			@Override
			public TeknisiPhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
				TeknisiPhoto teknisiPhoto = new TeknisiPhoto();
				teknisiPhoto.setId((rs.getLong("id")));
				teknisiPhoto.setTeknisi_id((rs.getInt("teknisi_id")));
				teknisiPhoto.setFile_type((rs.getString("file_type")));
				teknisiPhoto.setName((rs.getString("name")));
				teknisiPhoto.setCreated_date((rs.getDate("created_date")));
				teknisiPhoto.setCreated_by((rs.getString("created_by")));
				teknisiPhoto.setImages((rs.getString("images")));
				teknisiPhoto.setUpdate_date((rs.getDate("update_date")));
				teknisiPhoto.setUpdate_by((rs.getString("update_by")));
				return teknisiPhoto;
			}
			
		});
		
		return teknisiPhoto;
	}

	@Override
	public void insert(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		String query = 
				"INSERT INTO teknisi_photo(id, teknisi_id, file_type, name, images, created_date, created_by, update_date, update_by) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
		 Date created_date = new Date();
		 String created_by = "User";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			teknisiPhoto.getId(), teknisiPhoto.getTeknisi_id(), fileType, fileName, base64, created_date,
	     			created_by, teknisiPhoto.getUpdate_date(), teknisiPhoto.getUpdate_by()
	     		});
		
	}


	@Override
	public void update(TeknisiPhoto teknisiPhoto, String fileName, String fileType, String base64) {
		String query = 
	    		 "update teknisi_photo set teknisi_id=? , file_type=? , name=? , images=? , update_date=? , update_by=? "
	    		 + "where id = ?" ;
		Date update_date = new Date();
		String update_by = "Admine";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			teknisiPhoto.getTeknisi_id(), fileType, fileName, base64, update_date, update_by, teknisiPhoto.getId()
	     		});
	}

	@Override
	public int deleteById(Long id) {
		return getJdbcTemplate().update("delete from teknisi_photo where id = ?", id);
	}

	@Override
	public boolean TeknisiPhotoIdExists(long id) {
		String sql = "select count(*) from teknisi_photo where id= ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id}, Long.class);
		return count > 0;
	}
	
	@Override
	public boolean TeknisiPhotoIdOrTeknisiIdExists(Long id, long teknisi_id) {
		String sql = "select count(*) from teknisi_photo where id= ? or teknisi_id = ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id, teknisi_id }, Long.class);
		return count > 0;
	}
	
	@Override
	public boolean TeknisiPhotoIdAndTeknisiIdExists(Long id, long teknisi_id) {
		String sql = "select count(*) from teknisi_photo where id= ? and teknisi_id = ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id, teknisi_id }, Long.class);
		return count > 0;
	}

}
