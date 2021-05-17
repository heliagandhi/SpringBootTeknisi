package com.Teknisi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;


public class Teknisi implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "The database generated teknisi id", name = "id", required = true, example = "10")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	
	@ApiModelProperty(notes = "The database generated teknisi phone", name = "phone", required = true, example = "081294749377")
	@Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13")
	private String phone;
	
	@ApiModelProperty(notes = "The database generated teknisi name", name = "name", required = true, example = "Dika")
	@Max(value = 50, message = "Name should not be greater than 50")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$") // ^[A-Za-z0-9]+$ / ^[A-Za-z0-9]*$
	private String name;
	
	@ApiModelProperty(notes = "The database generated teknisi nik", name = "nik", required = true, example = "2006042600")
	@Max(value = 16, message = "NIK should not be greater than 16")
	private String nik;
	
	@ApiModelProperty(notes = "The database generated teknisi address", name = "address", required = true, example = "Jatiasih")
	@Max(value = 50, message = "Address should not be greater than 50")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$")
	private String address;
	
	@ApiModelProperty(notes = "The database generated teknisi email", name = "email", required = true, example = "dika@gmail.com")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	
	@ApiModelProperty(notes = "The database generated teknisi city", name = "city", required = true, example = "Bekasi")
	@Max(value = 50, message = "City should not be greater than 50")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$")
	private String city;
	
	@ApiModelProperty(notes = "The database generated teknisi postal_code", name = "postal_code", required = true, example = "17426")
	@Max(value = 5, message = "Postal code should not be greater than 5")
	private String postal_code;
	
	@ApiModelProperty(notes = "The database generated teknisi last_login", name = "last_login", required = true)
	@PastOrPresent
	private Date last_login;
	
	@ApiModelProperty(notes = "The database generated teknisi longitude", name = "logitudinal", required = true, example = "100000")
	private String longitude;
	
	@ApiModelProperty(notes = "The database generated teknisi latitude", name = "latitude", required = true, example = "12300")
	private String latitude;
	
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date created_date;
	
	@ApiModelProperty(hidden = true)
	private String created_by;
	
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date update_date;
	
	@ApiModelProperty(hidden = true)
	private String update_by;
	
	@OneToMany(mappedBy="teknisidata")
	@ApiModelProperty(hidden = true)
    private List<Request> request = new ArrayList<Request>();
	
	
	public Teknisi(){
		
	}
	

	public Teknisi(@Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13") String phone,
			@Max(value = 50, message = "Name should not be greater than 50") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String name,
			@Max(value = 16, message = "NIK should not be greater than 16") String nik,
			@Max(value = 50, message = "Address should not be greater than 50") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String address,
			@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") String email,
			@Max(value = 50, message = "City should not be greater than 50") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String city,
			@Max(value = 5, message = "Postal code should not be greater than 5") String postal_code,
			@PastOrPresent Date last_login, String longitude, String latitude) {
		super();
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.nik = nik;
		this.address = address;
		this.email = email;
		this.city = city;
		this.postal_code = postal_code;
		this.last_login = last_login;
		this.longitude = longitude;
		this.latitude = latitude;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public List<Request> getRequest() {
		return request;
	}


	public void setRequest(List<Request> request) {
		this.request = request;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Teknisi [id=");
		builder.append(id);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", name=");
		builder.append(name);
		builder.append(", nik=");
		builder.append(nik);
		builder.append(", address=");
		builder.append(address);
		builder.append(", email=");
		builder.append(email);
		builder.append(", city=");
		builder.append(city);
		builder.append(", postal_code=");
		builder.append(postal_code);
		builder.append(", last_login=");
		builder.append(last_login);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", created_date=");
		builder.append(created_date);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_by=");
		builder.append(update_by);
		builder.append(", request=");
		builder.append(request);
		builder.append("]");
		return builder.toString();
	}	
}
