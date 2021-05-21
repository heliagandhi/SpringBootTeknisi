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

import com.Teknisi.model.AppUser;

@Repository
public class AppUserDaoImpl extends JdbcDaoSupport implements AppUserDao{
	
	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

	@Override
	public List<AppUser> getAllAppUser() {
		String query =
				"SELECT id, username, password, email, created_date, created_by, update_date, update_by from app_user";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<AppUser> appUserList = new ArrayList<AppUser>();
		
		List<Map<String,Object>> appUserRows = jdbcTemplate.queryForList(query);
		for(Map<String,Object> appUserColumn : appUserRows){
			AppUser appUser = new AppUser();
			appUser.setId(Long.parseLong(appUserColumn.get("id").toString()));
			appUser.setUsername(String.valueOf(appUserColumn.get("username")));
			appUser.setPassword(String.valueOf(appUserColumn.get("password")));
			appUser.setEmail(String.valueOf(appUserColumn.get("email")));
			appUser.setCreated_date((Date)(appUserColumn.get("created_date")));
			appUser.setCreated_by(String.valueOf(appUserColumn.get("created_by")));
			appUser.setUpdate_date((Date)(appUserColumn.get("update_date")));
			appUser.setUpdate_by(String.valueOf(appUserColumn.get("update_by")));
			appUserList.add(appUser);
		}
		return appUserList;
	}

	@Override
	public AppUser findAppUserById(Long id) {
		String query =
				"SELECT id, username, password, email, created_date, created_by, update_date, update_by from app_user where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		@SuppressWarnings("deprecation")
		AppUser appUser = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<AppUser>(){

			@Override
			public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppUser appUser = new AppUser();
				appUser.setId((rs.getLong("id")));
				appUser.setUsername((rs.getString("username")));
				appUser.setPassword((rs.getString("password")));
				appUser.setEmail((rs.getString("email")));
				appUser.setCreated_date((rs.getDate("created_date")));
				appUser.setCreated_by((rs.getString("created_by")));
				appUser.setUpdate_date((rs.getDate("update_date")));
				appUser.setUpdate_by((rs.getString("update_by")));
				return appUser;
			}
		});
		return appUser;
	}

	@Override
	public void insert(AppUser AppUser) {
		String query = 
				"INSERT INTO app_user(id, username, password, email, created_date, created_by, update_date, update_by) "
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)" ;
		 Date created_date = new Date();
		 String created_by = "User";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			AppUser.getId(), AppUser.getUsername(), AppUser.getPassword(), AppUser.getEmail(), created_date, created_by,
	     			AppUser.getUpdate_date(), AppUser.getUpdate_by()
	     		});
	}

	@Override
	public void update(AppUser appUser) {
		String query = 
	    		 "update app_user set username=? , password=? , email=? , update_date=? , update_by=? "
	    		 + "where id = ?" ;
		Date update_date = new Date();
		String update_by = "Admine";
	     getJdbcTemplate()
	     	.update(query, new Object[]{
	     			appUser.getUsername(), appUser.getPassword(), appUser.getEmail(), update_date, update_by, appUser.getId()
	     		});
	}

	@Override
	public int deleteById(Long id) {
		return getJdbcTemplate().update("delete from app_user where id = ?", id);
	}

	@Override
	public boolean AppUserIdExists(Long id) {
		String sql = "select count(*) from app_user where id= ? limit 1";
	    @SuppressWarnings("deprecation")
		long count = getJdbcTemplate().queryForObject(sql, new Object[] { id}, Long.class);
		return count > 0;
	}

}
