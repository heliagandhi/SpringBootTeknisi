package com.Teknisi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.Email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "teknisi")
@ApiModel(description = "Teknisi Model")
public class Teknisi implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "ID of the Teknisi", name = "id", required = true, example = "10")
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	@NotNull(message = "ID cannot be blank")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	
	@ApiModelProperty(notes = "Phone of the Teknisi", name = "phone", required = true, example = "081294749377")
	@Column(name="phone")
	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp="[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric")
	private String phone;

	@ApiModelProperty(notes = "Name of the Teknisi", name = "name", required = true, example = "Gandhi")
	@Column(name="name")
	@NotBlank(message = "Name cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters")
	private String name;

	@ApiModelProperty(notes = "Nik of the Teknisi", name = "nik", required = true, example = "2006260400")
	@Column(name="nik")
	@NotBlank(message = "NIK cannot be blank")
	@Pattern(regexp="[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric")
	private String nik;

	@ApiModelProperty(notes = "Address of the Teknisi", name = "address", required = true, example = "Jatiasih")
	@Column(name="address")
	@NotBlank(message = "Address cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters")
	private String address;

	@ApiModelProperty(notes = "Email of the Teknisi", name = "email", required = true, example = "gandhi@gmail.com")
	@Column(name="email")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	private String email;

	@ApiModelProperty(notes = "City of the Teknisi", name = "city", required = true, example = "Bekasi")
	@Column(name="city")
	@NotBlank(message = "City cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters")
	private String city;

	@ApiModelProperty(notes = "Postal_code of the Teknisi", name = "postal_code", required = true, example = "17426")
	@Column(name="postal_code")
	@NotBlank(message = "Postal Code cannot be blank")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	private String postal_code;

	@ApiModelProperty(notes = "Last_login of the Teknisi", name = "last_login", required = true)
	@Column(name="last_login")
	@PastOrPresent
	private Date last_login;

	@ApiModelProperty(notes = "Longitude", name = "longitude", required = true, example = "1234456")
	@NotBlank(message = "Teknisi longitude need to be filled")
    @Column(name="longitude")
	private String longitude;

	@ApiModelProperty(notes = "Latitude", name = "latitude", required = true, example = "654321")
	@NotBlank(message = "Teknisi latitude need to be filled")
    @Column(name="latitude")
	private String latitude;

	@ApiModelProperty(hidden = true)
	@Column(name="created_date")
	@PastOrPresent
	private Date created_date;

    @ApiModelProperty(hidden = true)
    @Column(name="created_by")
	private String created_by;

	@ApiModelProperty(hidden = true)
	@Column(name="update_date")
	@PastOrPresent
	private Date update_date;

    @ApiModelProperty(hidden = true)
    @Column(name="update_by")
	private String update_by;

	@ApiModelProperty(hidden = true)
	@OneToMany(mappedBy="teknisi_id")
    private List<Request> request = new ArrayList<Request>();
	
	
	public Teknisi() {
		
	}

	public Teknisi(
			@NotNull(message = "ID cannot be blank") @Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@NotBlank(message = "Phone cannot be blank") @Pattern(regexp = "[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric") String phone,
			@NotBlank(message = "Name cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}+$", message = "Name should have length between 1 and 50 characters") String name,
			@NotBlank(message = "NIK cannot be blank") @Pattern(regexp = "[\\d]{1,16}", message = "NIK should have length between 1 and 16 numeric") String nik,
			@NotBlank(message = "Address cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Address should have length between 1 and 50 characters") String address,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String email,
			@NotBlank(message = "City cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "City should have length between 1 and 50 characters") String city,
			@NotBlank(message = "Postal Code cannot be blank") @Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
			@PastOrPresent Date last_login, @NotBlank(message = "Teknisi longitude need to be filled") String longitude,
			@NotBlank(message = "Teknisi latitude need to be filled") String latitude) {
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
