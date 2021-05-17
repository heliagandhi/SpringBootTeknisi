package com.Teknisi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;


public class Teknisi implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(notes = "The database generated teknisi id", name = "id", required = true, example = "10")
	@Column(name="id")
	@NotNull(message = "ID cannot be blank")
	@Max(value = 1000, message = "Id can't be less than 1 or bigger than 1000")
	private Long id;
	
	@ApiModelProperty(notes = "The database generated teknisi phone", name = "phone", required = true, example = "081294749377")
	@Column(name="phone")
	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13")
	private String phone;
	
	@ApiModelProperty(notes = "The database generated teknisi name", name = "name", required = true, example = "Dikas")
	@Column(name="name")
	@NotBlank(message = "Name cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters") // ^[A-Za-z0-9]+$ / ^[A-Za-z0-9]*$
	private String name;
	
	@ApiModelProperty(notes = "The database generated teknisi nik", name = "nik", required = true, example = "2006042600")
	@Column(name="nik")
	@NotBlank(message = "NIK cannot be blank")
	@Pattern(regexp="[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric")
	private String nik;
	
	@ApiModelProperty(notes = "The database generated teknisi address", name = "address", required = true, example = "Jatiasih")
	@Column(name="address")
	@NotBlank(message = "Address cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters")
	private String address;
	
	@ApiModelProperty(notes = "The database generated teknisi email", name = "email", required = true, example = "dika@gmail.com")
	@Column(name="email")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;
	
	@ApiModelProperty(notes = "The database generated teknisi city", name = "city", required = true, example = "Bekasi")
	@Column(name="city")
	@NotBlank(message = "City cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters")
	private String city;
	
	@ApiModelProperty(notes = "The database generated teknisi postal_code", name = "postal_code", required = true, example = "17426")
	@Column(name="postal_code")
	@NotBlank(message = "Postal Code cannot be blank")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	private String postal_code;
	
	@ApiModelProperty(notes = "The database generated teknisi last_login", name = "last_login", required = true)
	@Column(name="last_login")
	@PastOrPresent
	private Date last_login;
	
	@ApiModelProperty(notes = "The database generated teknisi longitude", name = "longitude", required = true, example = "100000")
	@Column(name="longitude")
	@NotBlank
	private String longitude;
	
	@ApiModelProperty(notes = "The database generated teknisi latitude", name = "latitude", required = true, example = "12300")
	@Column(name="latitude")
	@NotBlank
	private String latitude;
	
	@Column(name="created_date")
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date created_date;
	
	@Column(name="created_by")
	@ApiModelProperty(hidden = true)
	private String created_by;
	
	@Column(name="update_date")
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date update_date;
	
	@Column(name="update_by")
	@ApiModelProperty(hidden = true)
	private String update_by;
	
	@OneToMany(mappedBy="teknisidata")
	@ApiModelProperty(hidden = true)
    private List<Request> request = new ArrayList<Request>();
	
	
	public Teknisi(){
		
	}	

	public Teknisi(
			@NotNull(message = "ID cannot be blank") @Max(value = 1000, message = "Id can't be less than 1 or bigger than 1000") Long id,
			@NotBlank(message = "Phone cannot be blank") @Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13") String phone,
			@NotBlank(message = "Name cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters") String name,
			@NotBlank(message = "NIK cannot be blank") @Pattern(regexp = "[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric") String nik,
			@NotBlank(message = "Address cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters") String address,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email,
			@NotBlank(message = "City cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters") String city,
			@NotBlank(message = "Postal Code cannot be blank") @Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
			@PastOrPresent Date last_login, @NotBlank String longitude, @NotBlank String latitude) {
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
