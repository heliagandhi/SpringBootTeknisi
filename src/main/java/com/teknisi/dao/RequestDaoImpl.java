package com.teknisi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.teknisi.model.Chart;
import com.teknisi.model.Request;
import com.teknisi.model.Teknisi;

@Repository
public class RequestDaoImpl extends JdbcDaoSupport implements RequestDao{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    
	@Override
	public List<Request> getAllRequest() {
		String query =
				"SELECT * from request";
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
			request.setTeknisi_id(Long.parseLong(requestColumn.get("teknisi_id").toString()));
			request.setCreated_date((Date)(requestColumn.get("created_date")));
			request.setCreated_by(String.valueOf(requestColumn.get("created_by")));
			request.setUpdate_date((Date)(requestColumn.get("update_date")));
			request.setUpdate_by(String.valueOf(requestColumn.get("update_by")));
			request.setStatus(String.valueOf(requestColumn.get("status")));
			requestList.add(request);
		}
		return requestList;
	}
	
	
	@Override
	public Request findRequestById(String request_id) {
		String query =
				"SELECT * from request where request_id = ?";
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
				request.setTeknisi_id(rs.getLong("teknisi_id"));
				request.setCreated_date(rs.getDate("created_date"));
				request.setCreated_by(rs.getString("created_by"));
				request.setUpdate_date(rs.getDate("update_date"));
				request.setUpdate_by(rs.getString("update_by"));
				request.setStatus(rs.getString("status"));
				return request;
			}});
		return request;
	}

	
	@Override
	public void insert(Request request) {
		String query = 
	    		 "INSERT INTO request( request_id, merchant_name, address, city, postal_code, phone, pic, teknisi_id, created_date,"
	    		 + " created_by, update_date, update_by, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
	     Date created_date = new Date();
		 String created_by = "Merchant";
		 String status = "NEW";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     		request.getRequest_id(), request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(),
	     		request.getPhone(), request.getPic(), request.getTeknisi_id(), created_date, created_by, request.getUpdate_date(),
	     		request.getUpdate_by(), status
	     		});
	}
	
	
	@Override
	public void updateRequest(Request request) {
		String query = "update request set merchant_name=? , address=? , city=?, postal_code=?, phone=?, pic=?, "
				+ "teknisi_id=?, update_date=?, update_by=? where request_id = ?";
		Date update_date = new Date();
		String update_by = "Admine";
		getJdbcTemplate()
     	.update(query, new Object[]{
     		request.getMerchant_name(), request.getAddress(), request.getCity(), request.getPostal_code(), request.getPhone(),
     		request.getPic(), request.getTeknisi_id(), update_date, update_by, request.getRequest_id()
     		});
	}
	
	
	@Override
	public void updateRequestMailSent(Request request) {
		String query = "update request set status=? where request_id = ?";
		String status = "MAIL_SENT";
		getJdbcTemplate()
     	.update(query, new Object[]{
     		status, request.getRequest_id()
     		});
	}

	
	@Override
	public int deleteById(String request_id) {
		return getJdbcTemplate().update("delete from request where request_id = ?", request_id);
	}


	@Override
	public boolean RequestIdExists(String request_id) {
		String sql = "select count(*) from request where request_id= ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { request_id }, Long.class);
		return count > 0;
	}

	@Override
	public List<Request> getAllStatusRequest(String status) {
		String query = "SELECT * FROM request WHERE status=? order by request_id asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();
		
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query, new Object[]{status});
		for(Map<String,Object> column : rows){
			Request request = new Request();
			request.setRequest_id(String.valueOf(column.get("request_id")));
			request.setMerchant_name(String.valueOf(column.get("merchant_name")));
			request.setAddress(String.valueOf(column.get("address")));
			request.setCity(String.valueOf(column.get("city")));
			request.setPostal_code(String.valueOf(column.get("postal_code")));
			request.setPhone(String.valueOf(column.get("phone")));
			request.setPic(String.valueOf(column.get("pic")));
			request.setTeknisi_id(Long.parseLong(column.get("teknisi_id").toString()));
			request.setCreated_date((Date)(column.get("created_date")));
			request.setCreated_by(String.valueOf(column.get("created_by")));
			request.setUpdate_date((Date)(column.get("update_date")));
			request.setUpdate_by(String.valueOf(column.get("update_by")));
			request.setStatus(String.valueOf(column.get("status")));
			requestList.add(request);
		}
		return requestList;
	}


	@Override
	public List<Request> getRequestByBeforeDate(String status) {
		String query = "SELECT * FROM request WHERE status=? and created_date < now() - interval '6 hour' order by request_id asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query, new Object[]{status});
		for(Map<String,Object> column : rows){
			Request request = new Request();
			request.setRequest_id(String.valueOf(column.get("request_id")));
			request.setMerchant_name(String.valueOf(column.get("merchant_name")));
			request.setAddress(String.valueOf(column.get("address")));
			request.setCity(String.valueOf(column.get("city")));
			request.setPostal_code(String.valueOf(column.get("postal_code")));
			request.setPhone(String.valueOf(column.get("phone")));
			request.setPic(String.valueOf(column.get("pic")));
			request.setTeknisi_id(Long.parseLong(column.get("teknisi_id").toString()));
			request.setCreated_date((Date)(column.get("created_date")));
			request.setCreated_by(String.valueOf(column.get("created_by")));
			request.setUpdate_date((Date)(column.get("update_date")));
			request.setUpdate_by(String.valueOf(column.get("update_by")));
			request.setStatus(String.valueOf(column.get("status")));
			requestList.add(request);
		}
		return requestList;
	}
	
	
	@Override
	public List<Request> getAllPendingRequest() {
		String query = 
			"select * from request where status in ('NEW', 'MAIL_SENT', 'PROCESSED') order by request_id asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> column : rows){
			Request request = new Request();
			request.setRequest_id(String.valueOf(column.get("request_id")));
			request.setMerchant_name(String.valueOf(column.get("merchant_name")));
			request.setAddress(String.valueOf(column.get("address")));
			request.setCity(String.valueOf(column.get("city")));
			request.setPostal_code(String.valueOf(column.get("postal_code")));
			request.setPhone(String.valueOf(column.get("phone")));
			request.setPic(String.valueOf(column.get("pic")));
			request.setTeknisi_id(Long.parseLong(column.get("teknisi_id").toString()));
			request.setCreated_date((Date)(column.get("created_date")));
			request.setCreated_by(String.valueOf(column.get("created_by")));
			request.setUpdate_date((Date)(column.get("update_date")));
			request.setUpdate_by(String.valueOf(column.get("update_by")));
			request.setStatus(String.valueOf(column.get("status")));
			requestList.add(request);
		}
		return requestList;
	}


	@Override
	public List<Request> getAllRecapitulationRequest() {
		String query = 
			"select "
			+ "req.request_id as requestID, req.teknisi_id as reqTeknisiId, req.merchant_name as merchantName, req.address as requestAddress, req.city as requestCity, "
			+ "req.postal_code as requestPostalCode, req.phone as requestPhone, req.pic as requestPIC, "
			+ "req.created_date as requestCreatedDate, req.created_by as requestCreatedBy, req.update_date as requestUpdateDate, req.update_by as requestUpdateBy, req.status as requestStatus, "
			+ "tek.id as teknisiID, tek.phone as teknisiPhone, tek.name as teknisiName, tek.nik as teknisiNIK, tek.address as teknisiAddress, tek.email as teknisiEmail, tek.city as teknisiCity, "
			+ "tek.postal_code as teknisiPostalCode, tek.last_login as teknisiLastLogin, tek.longitude as teknisiLongitude, tek.latitude as teknisiLatitude, "
			+ "tek.created_date as teknisiCreatedDate, tek.created_by as teknisiCreatedBy, tek.update_date as teknisiUpdateDate, tek.update_by as teknisiUpdateBy "
			+ "from request req "
			+ "right join teknisi tek on req.teknisi_id = tek.id "
			+ "where (req.created_date between "
			+ "    now()::date-extract(dow from now())::integer-7 "
			+ "    and now()::date-extract(dow from now())::integer) or "
			+ "	(req.update_date between "
			+ "    now()::date-extract(dow from now())::integer-7 "
			+ "    and now()::date-extract(dow from now())::integer) "
			+ "order by (case when req.update_date is null then req.created_date else req.update_date end) asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Request> requestList = new ArrayList<Request>();

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> column : rows){
			Teknisi teknisi = new Teknisi();
			Request request = new Request();
			request.setRequest_id(String.valueOf(column.get("requestID")));
			request.setMerchant_name(String.valueOf(column.get("merchantName")));
			request.setAddress(String.valueOf(column.get("requestAddress")));
			request.setCity(String.valueOf(column.get("requestCity")));
			request.setPostal_code(String.valueOf(column.get("requestPostalCode")));
			request.setPhone(String.valueOf(column.get("requestPhone")));
			request.setPic(String.valueOf(column.get("requestPIC")));
			request.setTeknisi_id(column.get("reqTeknisiId")==null?null:Long.parseLong(column.get("reqTeknisiId").toString()));
			request.setCreated_date((Date)(column.get("requestCreatedDate")));
			request.setCreated_by(String.valueOf(column.get("requestCreatedBy")));
			request.setUpdate_date((Date)(column.get("requestUpdateDate")));
			request.setUpdate_by(String.valueOf(column.get("requestUpdateBy")));
			request.setStatus(String.valueOf(column.get("requestStatus")));
			teknisi.setId(Long.parseLong(column.get("teknisiID").toString()));
			teknisi.setPhone(String.valueOf(column.get("teknisiPhone")));
			teknisi.setName(String.valueOf(column.get("teknisiName")));
			teknisi.setNik(String.valueOf(column.get("teknisiNIK")));
			teknisi.setAddress(String.valueOf(column.get("teknisiAddress")));
			teknisi.setEmail(String.valueOf(column.get("teknisiEmail")));
			teknisi.setCity(String.valueOf(column.get("teknisiCity")));
			teknisi.setPostal_code(String.valueOf(column.get("teknisiPostalCode")));
			teknisi.setLast_login((Date)(column.get("teknisiLastLogin")));
			teknisi.setLongitude(String.valueOf(column.get("teknisiLongitude")));
			teknisi.setLatitude(String.valueOf(column.get("teknisiLatitude")));
			teknisi.setCreated_date((Date)(column.get("teknisiCreatedDate")));
			teknisi.setCreated_by(String.valueOf(column.get("teknisiCreatedBy")));
			teknisi.setUpdate_date((Date)(column.get("teknisiUpdateDate")));
			teknisi.setUpdate_by(String.valueOf(column.get("teknisiUpdateBy")));
			request.setTeknisi(teknisi);
			requestList.add(request);
		}
		return requestList;
	}


	@Override
	public List<Chart> getAllRequestCount() {
		String query = "select r.create_date as create_date ,count(r.create_date) as count, r.status as status  from (\r\n"
				+ "SELECT  date_trunc('day', created_date) create_date, status from request  req\r\n"
				+ "where (req.created_date between now()::date-extract(dow from now())::integer-7    \r\n"
				+ "and now()::date-extract(dow from now())::integer) or  (req.update_date between now()::date-extract(dow from now())::integer-7  \r\n"
				+ "and now()::date-extract(dow from now())::integer) order by (case when req.update_date is null then req.created_date else req.update_date end) asc\r\n"
				+ ") r group by r.create_date, r.status order by create_date asc";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Chart> chartList = new ArrayList<Chart>();

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
		for(Map<String,Object> column : rows){
			Chart chart = new Chart();
			chart.setCreated_date((Date)(column.get("create_date")));
			chart.setCount(Integer.parseInt(column.get("count").toString()));
			chart.setStatus(String.valueOf(column.get("status")));
			chartList.add(chart);
		}
		return chartList;
	}

}
