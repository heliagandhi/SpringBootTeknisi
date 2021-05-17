package com.Teknisi.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

public class Request {
	@ApiModelProperty(notes = "The database generated request request_id", name = "request_id", required = true, example = "10")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private String request_id;
	
	@ApiModelProperty(notes = "The database generated request merchant_name", name = "merchant_name", required = true, example = "Memey")
	@Max(value = 50, message = "Name should not be greater than 50")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$") // ^[A-Za-z0-9]+$ / ^[A-Za-z0-9]*$
	private String merchant_name;
	
	@ApiModelProperty(notes = "The database generated request address", name = "address", required = true, example = "Malabar")
	@Max(value = 140, message = "Address should not be greater than 140")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$")
	private String address;
	
	@ApiModelProperty(notes = "The database generated request city", name = "city", required = true, example = "Bogor")
	@Max(value = 25, message = "City should not be greater than 25")
	@Pattern(regexp = "^[\\p{Alnum}]{1,32}$")
	private String city;
	
	@ApiModelProperty(notes = "The database generated request postal_code", name = "postal_code", required = true, example = "18243")
	@Max(value = 5, message = "Postal code should not be greater than 5")
	private String postal_code;
	
	@ApiModelProperty(notes = "The database generated request phone", name = "phone", required = true, example = "021665786678")
	@Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13")
	private String phone;
	
	@ApiModelProperty(notes = "The database generated request pic", name = "pic", required = true, example = "Examplepic")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Phone should not be greater than 50")
	private String pic;
	
	@ApiModelProperty(notes = "The database generated request teknisi_id", name = "teknisi_id", required = true, example = "10")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private int teknisi_id;
	
//	@ManyToOne
//	@JoinColumn(name="teknisi_id", referencedColumnName = "id", nullable=false, insertable=false, updatable=false)
//	private Teknisi teknisidata;
	
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
	
	
	public Request(){	}


	public Request(@Max(value = 1000, message = "ID should not be greater than 1000") String request_id,
			@Max(value = 50, message = "Name should not be greater than 50") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String merchant_name,
			@Max(value = 140, message = "Address should not be greater than 140") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String address,
			@Max(value = 25, message = "City should not be greater than 25") @Pattern(regexp = "^[\\p{Alnum}]{1,32}$") String city,
			@Max(value = 5, message = "Postal code should not be greater than 5") String postal_code,
			@Pattern(regexp = "^[\\d]{1,13}$", message = "Phone should not be greater than 13") String phone,
			@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Phone should not be greater than 50") String pic,
			@Max(value = 1000, message = "ID should not be greater than 1000") int teknisi_id) {
		super();
		this.request_id = request_id;
		this.merchant_name = merchant_name;
		this.address = address;
		this.city = city;
		this.postal_code = postal_code;
		this.phone = phone;
		this.pic = pic;
		this.teknisi_id = teknisi_id;
	}


	public String getRequest_id() {
		return request_id;
	}


	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}


	public String getMerchant_name() {
		return merchant_name;
	}


	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPic() {
		return pic;
	}


	public void setPic(String pic) {
		this.pic = pic;
	}


	public int getTeknisi_id() {
		return teknisi_id;
	}


	public void setTeknisi_id(int teknisi_id) {
		this.teknisi_id = teknisi_id;
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


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Request [request_id=");
		builder.append(request_id);
		builder.append(", merchant_name=");
		builder.append(merchant_name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", city=");
		builder.append(city);
		builder.append(", postal_code=");
		builder.append(postal_code);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", pic=");
		builder.append(pic);
		builder.append(", teknisi_id=");
		builder.append(teknisi_id);
		builder.append(", created_date=");
		builder.append(created_date);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_by=");
		builder.append(update_by);
		builder.append("]");
		return builder.toString();
	}
	
	

	
	
	
}
