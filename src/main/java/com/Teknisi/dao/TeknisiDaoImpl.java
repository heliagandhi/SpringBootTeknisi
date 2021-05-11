package com.Teknisi.dao;

//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.Teknisi.model.Teknisi;
import com.Teknisi.model.Request;

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
		String query = "select tek.id as teknisiID, tek.phone as teknisiPhone, tek.name as teknisiName, tek.nik as teknisiNIK, tek.address as teknisiAddress, tek.email as teknisiEmail, tek.city as teknisiCity, "
				+ "tek.postal_code as teknisiPostalCode, tek.last_login as teknisiLastLogin, tek.longitude as teknisiLongitude, tek.latitude as teknisiLatitude, "
				+ "tek.created_date as teknisiCreatedDate, tek.created_by as teknisiCreatedBy, tek.update_date as teknisiUpdateDate, tek.update_by as teknisiUpdateBy, "
				+ "req.request_id as requestID, req.merchant_name as merchantName, req.address as requestAddress, req.city as requestCity, req.postal_code as requestPostalCode, req.phone as requestPhone, req.pic as requestPIC, "
				+ "req.created_date as requestCreatedDate, req.created_by as requestCreatedBy, req.update_date as requestUpdateDate, req.update_by as requestUpdateBy from teknisi tek "
				+ "left join request req on req.teknisi_id = tek.id "
				+ "where tek.id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Set<Request> listRequest = new HashSet<Request>();
		@SuppressWarnings("deprecation")
		Teknisi teknisi = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Teknisi>(){

			@Override
			public Teknisi mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Teknisi teknisi = new Teknisi();
				Request request = new Request();
				teknisi.setId((rs.getLong("teknisiID")));
				teknisi.setPhone(rs.getString("teknisiPhone"));
				teknisi.setName(rs.getString("teknisiName"));
				teknisi.setNik(rs.getString("teknisiNIK"));
				teknisi.setAddress(rs.getString("teknisiAddress"));
				teknisi.setEmail(rs.getString("teknisiEmail"));
				teknisi.setCity(rs.getString("teknisiCity"));
				teknisi.setPostal_code(rs.getString("teknisiPostalCode"));
				teknisi.setLast_login(rs.getDate("teknisiLastLogin"));
				teknisi.setLongitude(rs.getString("teknisiLongitude"));
				teknisi.setLatitude(rs.getString("teknisiLatitude"));
				teknisi.setCreated_date(rs.getDate("teknisiCreatedDate"));
				teknisi.setCreated_by(rs.getString("teknisiCreatedBy"));
				teknisi.setUpdate_date(rs.getDate("teknisiUpdateDate"));
				teknisi.setUpdate_by(rs.getString("teknisiUpdateBy"));
				request.setRequest_id(rs.getString("requestID"));
				request.setMerchant_name(rs.getString("merchantName"));
				request.setAddress(rs.getString("requestAddress"));
				request.setCity(rs.getString("requestCity"));
				request.setPostal_code(rs.getString("requestPostalCode"));
				request.setPhone(rs.getString("requestPhone"));
				request.setPic(rs.getString("requestPIC"));
				request.setTeknisi_id(rs.getInt("teknisiID"));
				request.setCreated_date(rs.getDate("requestCreatedDate"));
				request.setCreated_by(rs.getString("requestCreatedBy"));
				request.setUpdate_date(rs.getDate("requestUpdateDate"));
				request.setUpdate_by(rs.getString("requestUpdateBy"));
				listRequest.add(request);
				teknisi.setRequest(listRequest);
				return teknisi;
			}});
		return teknisi;
	}

	
	@Override
	public List<Teknisi> getAllTeknisi() {
		String query = "select tek.id as teknisiID, tek.phone as teknisiPhone, tek.name as teknisiName, tek.nik as teknisiNIK, tek.address as teknisiAddress, tek.email as teknisiEmail, tek.city as teknisiCity, "
				+ "tek.postal_code as teknisiPostalCode, tek.last_login as teknisiLastLogin, tek.longitude as teknisiLongitude, tek.latitude as teknisiLatitude, "
				+ "tek.created_date as teknisiCreatedDate, tek.created_by as teknisiCreatedBy, tek.update_date as teknisiUpdateDate, tek.update_by as teknisiUpdateBy, "
				+ "req.request_id as requestID, req.merchant_name as merchantName, req.address as requestAddress, req.city as requestCity, req.postal_code as requestPostalCode, req.phone as requestPhone, req.pic as requestPIC, "
				+ "req.created_date as requestCreatedDate, req.created_by as requestCreatedBy, req.update_date as requestUpdateDate, req.update_by as requestUpdateBy from teknisi tek "
				+ "left join request req on req.teknisi_id = tek.id";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Teknisi> teknisiList = new ArrayList<Teknisi>();

		List<Map<String,Object>> teknisiRows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> teknisiColumn : teknisiRows){
			Teknisi teknisi = new Teknisi();
			Set<Request> listRequest = new HashSet<Request>();
			Request request = new Request();
			teknisi.setId(Long.parseLong(teknisiColumn.get("teknisiID").toString()));
			teknisi.setPhone(String.valueOf(teknisiColumn.get("teknisiPhone")));
			teknisi.setName(String.valueOf(teknisiColumn.get("teknisiName")));
			teknisi.setNik(String.valueOf(teknisiColumn.get("teknisiNIK")));
			teknisi.setAddress(String.valueOf(teknisiColumn.get("teknisiAddress")));
			teknisi.setEmail(String.valueOf(teknisiColumn.get("teknisiEmail")));
			teknisi.setCity(String.valueOf(teknisiColumn.get("teknisiCity")));
			teknisi.setPostal_code(String.valueOf(teknisiColumn.get("teknisiPostalCode")));
			teknisi.setLast_login((Date)(teknisiColumn.get("teknisiLastLogin")));
			teknisi.setLongitude(String.valueOf(teknisiColumn.get("teknisiLongitude")));
			teknisi.setLatitude(String.valueOf(teknisiColumn.get("teknisiLatitude")));
			teknisi.setCreated_date((Date)(teknisiColumn.get("teknisiCreatedDate")));
			teknisi.setCreated_by(String.valueOf(teknisiColumn.get("teknisiCreatedBy")));
			teknisi.setUpdate_date((Date)(teknisiColumn.get("teknisiUpdateDate")));
			teknisi.setUpdate_by(String.valueOf(teknisiColumn.get("teknisiUpdateBy")));
			request.setRequest_id(String.valueOf(teknisiColumn.get("requestID")));
			request.setMerchant_name(String.valueOf(teknisiColumn.get("merchantName")));
			request.setAddress(String.valueOf(teknisiColumn.get("requestAddress")));
			request.setCity(String.valueOf(teknisiColumn.get("requestCity")));
			request.setPostal_code(String.valueOf(teknisiColumn.get("requestPostalCode")));
			request.setPhone(String.valueOf(teknisiColumn.get("requestPhone")));
			request.setPic(String.valueOf(teknisiColumn.get("requestPIC")));
			request.setTeknisi_id(Integer.valueOf(teknisiColumn.get("teknisiID").toString()));
			request.setCreated_date((Date)(teknisiColumn.get("requestCreatedDate")));
			request.setCreated_by(String.valueOf(teknisiColumn.get("requestCreatedBy")));
			request.setUpdate_date((Date)(teknisiColumn.get("requestUpdateDate")));
			request.setUpdate_by(String.valueOf(teknisiColumn.get("requestUpdateBy")));
			listRequest.add(request);
			teknisi.setRequest(listRequest);
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


	@Override
	public boolean TeknisiIdExists(long id) {
		String sql = "select count(*) from teknisi where id = ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id }, Long.class);
		return count > 0;
	}

}