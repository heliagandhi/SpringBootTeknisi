package com.teknisi.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

@ApiModel(description = "Request Model")
public class Request {
	
	@NotBlank(message = "Request ID cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "Request ID should have length between 1 and 10 Alphanumeric characters")
	@ApiModelProperty(notes = "The database generated request request_id", name = "request_id", required = true, example = "10")
	private String request_id;
	
	@NotBlank(message = "Merchant name cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters")
	@ApiModelProperty(notes = "The database generated request merchant_name", name = "merchant_name", required = true, example = "Memey")
	private String merchant_name;
	
	@NotBlank(message = "Address cannot be blank")
	@Pattern(regexp = "^[-a-zA-Z0-9,]{5,140}$", message = "Address should have length between 5 and 140 characters")
	@ApiModelProperty(notes = "The database generated request address", name = "address", required = true, example = "Malabar")
	private String address;
	
	@NotBlank(message = "City cannot be blank")
	@Pattern(regexp = "^[-a-zA-Z0-9,]{1,25}$", message = "City should have length between 1 and 25 characters")
	@ApiModelProperty(notes = "The database generated request city", name = "city", required = true, example = "Bogor")
	private String city;
	
	@NotBlank(message = "Postal code cannot be blank")
	@Pattern(regexp="[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric")
	@ApiModelProperty(notes = "The database generated request postal_code", name = "postal_code", required = true, example = "18243")
	private String postal_code;
	
	@NotBlank(message = "Phone cannot be blank")
	@Pattern(regexp="[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric")
	@ApiModelProperty(notes = "The database generated request phone", name = "phone", required = true, example = "021665786678")
	private String phone;
	
	@NotBlank(message = "Person in charge cannot be blank")
	@Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters")
	@ApiModelProperty(notes = "The database generated request pic", name = "pic", required = true, example = "Examplepic")
	private String pic;
	
	@NotNull(message = "Teknisi ID cannot be null")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	@ApiModelProperty(notes = "The database generated request teknisi_id", name = "teknisi_id", required = true, example = "10")
	private Long teknisi_id;
	
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
	
	@ApiModelProperty(hidden = true)
	private String status;
	
	
	public Request(){	}

	public Request(
			@NotBlank(message = "Request ID cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "Request ID should have length between 1 and 10 Alphanumeric characters") String request_id,
			@NotBlank(message = "Merchant name cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "Name should have length between 1 and 50 characters") String merchant_name,
			@NotBlank(message = "Address cannot be blank") @Pattern(regexp = "^[-a-zA-Z0-9,]{5,140}$", message = "Address should have length between 5 and 140 characters") String address,
			@NotBlank(message = "City cannot be blank") @Pattern(regexp = "^[-a-zA-Z0-9,]{1,25}$", message = "City should have length between 1 and 25 characters") String city,
			@NotBlank(message = "Postal code cannot be blank") @Pattern(regexp = "[\\d]{1,5}", message = "Postal Code should have length between 1 and 5 numeric") String postal_code,
			@NotBlank(message = "Phone cannot be blank") @Pattern(regexp = "[\\d]{1,13}", message = "NIK should have length between 1 and 13 numeric") String phone,
			@NotBlank(message = "Person in charge cannot be blank") @Pattern(regexp = "^[A-Za-z0-9]{1,50}$", message = "PIC should have length between 1 and 50 characters") String pic,
			@NotNull(message = "Teknisi ID cannot be null") @Max(value = 1000, message = "ID should not be greater than 1000") Long teknisi_id) {
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


	public Long getTeknisi_id() {
		return teknisi_id;
	}


	public void setTeknisi_id(Long teknisi_id) {
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


	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
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
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}	
	
}
