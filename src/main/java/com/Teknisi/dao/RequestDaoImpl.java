package com.Teknisi.dao;

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

import com.Teknisi.model.Request;

@Repository
public class RequestDaoImpl extends JdbcDaoSupport implements RequestDao{
	
	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    
	@Override
	public List<Request> getAllRequest() {
		String query =
				"SELECT r.request_id, r.merchant_name, r.address, r.city, r.postal_code, r.phone, r.pic, t.id, r.created_date,"
				+ "r.created_by, r.update_date, r.update_by from request r right join teknisi t on r.teknisi_id = t.id";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();

		List<Map<String,Object>> requestRows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> requestColumn : requestRows){
			Request request = new Request();
			request.setRequest_id(String.valueOf(requestColumn.get("request_id")));
			request.setMerchant_name(String.valueOf(requestColumn.get("merchant_name")));
			request.setAddress(String.valueOf(requestColumn.get("address")));
			request.setCity(String.valueOf(requestColumn.get("city")));
			request.setPostal_code(String.valueOf(requestColumn.get("postal_code")));
			request.setPhone(String.valueOf(requestColumn.get("phone")));
			request.setPic(String.valueOf(requestColumn.get("pic")));
//			request.setTeknisi_id(Integer.parseInt(requestColumn.get("teknisi_id").toString()));
			request.setCreated_date((Date)(requestColumn.get("created_date")));
			request.setCreated_by(String.valueOf(requestColumn.get("created_by")));
			request.setUpdate_date((Date)(requestColumn.get("update_date")));
			request.setUpdate_by(String.valueOf(requestColumn.get("update_by")));
			requestList.add(request);
		}
		return requestList;
	}

	
	@Override
	public void insert(Request request) {
		String query = 
	    		 "INSERT INTO request( request_id, merchant_name, address, city, postal_code, phone, pic, teknisi_id, created_date,"
	    		 + " created_by, update_date, update_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	     Date created_date = new Date();
		 String created_by = "Merchant";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     		request.getRequest_id(), request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(),
	     		request.getPhone(), request.getPic(), request.getTeknisi_id(), created_date, created_by, request.getUpdate_date(),
	     		request.getUpdate_by()
	     		});
	} //(SELECT teknisi_id FROM teknisi WHERE teknisi_id = ?)

	
	@Override
	public int deleteById(String request_id) {
		return getJdbcTemplate().update("delete from request where request_id = ?", request_id);
	}

	
	@Override
	public void updateRequest(Request request) {
		String query = "update request set merchant_name=? , address=? , city=?, postal_code=?, phone=?, pic=?, "
				+ "teknisi_id=?, created_date=?, created_by=?, update_date=?, update_by=? where request_id = ?";
		Date update_date = new Date();
		String update_by = "Admine";
		getJdbcTemplate()
     	.update(query, new Object[]{
     		request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(), request.getPhone(),
     		request.getPic(), request.getTeknisi_id(), request.getCreated_date(), request.getCreated_by(), update_date,
     		update_by, request.getRequest_id()
     		});
	}
	

	@Override
	public Request findRequestById(String request_id) {
		String query =
				"SELECT request_id, merchant_name, address, city, postal_code, phone, pic, teknisi_id, created_date, created_by, "
				+ "update_date, update_by from request where request_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		@SuppressWarnings("deprecation")
		Request request = jdbcTemplate.queryForObject(query, new Object[]{request_id}, new RowMapper<Request>(){

			@Override
			public Request mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Request request = new Request();
				request.setRequest_id((rs.getString("request_id")));
				request.setMerchant_name(rs.getString("merchant_name"));
				request.setAddress(rs.getString("address"));
				request.setCity(rs.getString("city"));
				request.setPostal_code(rs.getString("postal_code"));
				request.setPhone(rs.getString("phone"));
				request.setPic(rs.getString("pic"));
//				request.setTeknisi_id(rs.getInt("teknisi_id"));
				request.setCreated_date(rs.getDate("created_date"));
				request.setCreated_by(rs.getString("created_by"));
				request.setUpdate_date(rs.getDate("update_date"));
				request.setUpdate_by(rs.getString("update_by"));
				return request;
			}});
		return request;
	}

}
