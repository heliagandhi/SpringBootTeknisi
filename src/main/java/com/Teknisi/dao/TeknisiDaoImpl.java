package com.Teknisi.dao;

//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.Teknisi.model.Teknisi;

@Repository
public class TeknisiDaoImpl extends JdbcDaoSupport implements TeknisiDao{

    @Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    
    
	@Override
	public void insert(Teknisi teknisi) {
	     String query = 
	    		 "INSERT INTO teknisi( id, phone, name, nik, address, email, city, postal_code, last_login, longitude, latitude,"
	    		 + " created_date, created_by, update_date, update_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	     Date created_date = new Date();
		 String created_by = "User";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     		teknisi.getId(), teknisi.getPhone(), teknisi.getName(), teknisi.getNik(), teknisi.getAddress(), teknisi.getEmail(),
	     		teknisi.getCity(), teknisi.getPostal_code(), teknisi.getLast_login(), teknisi.getLongitude(), teknisi.getLatitude(),
	     		created_date, created_by, teknisi.getUpdate_date(), teknisi.getUpdate_by()
	     		});
		
	}

	
	@Override
	public Teknisi findTeknisiById(long id) {
		String query =
				"SELECT id, phone, name, nik, address, email, city, postal_code, last_login, longitude, latitude,"
				+ "created_date, created_by, update_date, update_by from teknisi where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		@SuppressWarnings("deprecation")
		Teknisi teknisi = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Teknisi>(){

			@Override
			public Teknisi mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Teknisi teknisi = new Teknisi();
				teknisi.setId((rs.getLong("id")));
				teknisi.setPhone(rs.getString("phone"));
				teknisi.setName(rs.getString("name"));
				teknisi.setNik(rs.getString("nik"));
				teknisi.setAddress(rs.getString("address"));
				teknisi.setEmail(rs.getString("email"));
				teknisi.setCity(rs.getString("city"));
				teknisi.setPostal_code(rs.getString("postal_code"));
				teknisi.setLast_login(rs.getDate("last_login"));
				teknisi.setLongitude(rs.getString("longitude"));
				teknisi.setLatitude(rs.getString("latitude"));
				teknisi.setCreated_date(rs.getDate("created_date"));
				teknisi.setCreated_by(rs.getString("created_by"));
				teknisi.setUpdate_date(rs.getDate("update_date"));
				teknisi.setUpdate_by(rs.getString("update_by"));
				return teknisi;
			}});
		return teknisi;
	}

	
	@Override
	public List<Teknisi> getAllTeknisi() {
		String query =
				"SELECT id, phone, name, nik, address, email, city, postal_code, last_login, longitude, latitude, "
				+ "created_date, created_by, update_date, update_by from teknisi";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Teknisi> teknisiList = new ArrayList<Teknisi>();

		List<Map<String,Object>> teknisiRows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> teknisiColumn : teknisiRows){
			Teknisi teknisi = new Teknisi();
			teknisi.setId(Long.parseLong(teknisiColumn.get("id").toString()));
			teknisi.setPhone(String.valueOf(teknisiColumn.get("phone")));
			teknisi.setName(String.valueOf(teknisiColumn.get("name")));
			teknisi.setNik(String.valueOf(teknisiColumn.get("nik")));
			teknisi.setAddress(String.valueOf(teknisiColumn.get("address")));
			teknisi.setEmail(String.valueOf(teknisiColumn.get("email")));
			teknisi.setCity(String.valueOf(teknisiColumn.get("city")));
			teknisi.setPostal_code(String.valueOf(teknisiColumn.get("postal_code")));
			teknisi.setLast_login((Date)(teknisiColumn.get("last_login")));
			teknisi.setLongitude(String.valueOf(teknisiColumn.get("longitude")));
			teknisi.setLatitude(String.valueOf(teknisiColumn.get("latitude")));
			teknisi.setCreated_date((Date)(teknisiColumn.get("created_date")));
			teknisi.setCreated_by(String.valueOf(teknisiColumn.get("created_by")));
			teknisi.setUpdate_date((Date)(teknisiColumn.get("update_date")));
			teknisi.setUpdate_by(String.valueOf(teknisiColumn.get("update_by")));
			teknisiList.add(teknisi);
		}
		return teknisiList;
	}

	
	@Override
    public int deleteById(Long id) {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        return jdbcTemplate.update("delete from teknisi where id = ?",id);
		return getJdbcTemplate().update("delete from teknisi where id = ?", id);
        
    }
	
	
	@Override
	public void updateTeknisi(Teknisi teknisi) {
		String query = "update teknisi set phone=? , name=? , nik=?, address=?, email=?, city=?, "
				+ "postal_code=?, last_login=?, longitude=?, latitude=?, "
				+ "created_date=?, created_by=?, update_date=?, update_by=? where id=?";
		Date update_date = new Date();
		String update_by = "Admin";
		getJdbcTemplate()
     	.update(query, new Object[]{
     		teknisi.getPhone(), teknisi.getName(), teknisi.getNik(), teknisi.getAddress(), teknisi.getEmail(), teknisi.getCity(),
     		teknisi.getPostal_code(), teknisi.getLast_login(), teknisi.getLongitude(), teknisi.getLatitude(),
     		teknisi.getCreated_date(), teknisi.getCreated_by(), update_date, update_by, teknisi.getId()
     		});
		
	}

}