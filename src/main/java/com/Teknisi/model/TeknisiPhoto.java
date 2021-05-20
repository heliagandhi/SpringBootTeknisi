package com.Teknisi.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Teknisi Photo Model")
public class TeknisiPhoto implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "ID of the Teknisi Photo", name = "id", required = true, example = "10")
	@NotNull(message = "Teknisi Photo ID cannot be blank")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	private Long id;
	
	@NotNull(message = "Teknisi ID cannot be null")
	@Max(value = 1000, message = "ID should not be greater than 1000")
	@ApiModelProperty(notes = "teknisi_id of the Teknisi Photo", name = "teknisi_id", required = true, example = "1")
	private Long teknisi_id;
	
	@ApiModelProperty(hidden = true)
	private String file_type;
	
	@ApiModelProperty(hidden = true)
	private String name;
	
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date created_date;
	
    @ApiModelProperty(hidden = true)
	private String created_by;
    
    @ApiModelProperty(hidden = true)
	private String images;
	
	@PastOrPresent
	@ApiModelProperty(hidden = true)
	private Date update_date;
	
    @ApiModelProperty(hidden = true)
	private String update_by;
    
    public TeknisiPhoto () {
    	
    }

	public TeknisiPhoto(
			@NotNull(message = "Teknisi Photo ID cannot be blank") @Max(value = 1000, message = "ID should not be greater than 1000") Long id,
			@NotNull(message = "Teknisi ID cannot be null") @Max(value = 1000, message = "ID should not be greater than 1000") Long teknisi_id,
			String file_type, String name, String images) {
		super();
		this.id = id;
		this.teknisi_id = teknisi_id;
		this.file_type = file_type;
		this.name = name;
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTeknisi_id() {
		return teknisi_id;
	}

	public void setTeknisi_id(Long teknisi_id) {
		this.teknisi_id = teknisi_id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
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
		builder.append("TeknisiPhoto [id=");
		builder.append(id);
		builder.append(", teknisi_id=");
		builder.append(teknisi_id);
		builder.append(", file_type=");
		builder.append(file_type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", created_date=");
		builder.append(created_date);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", images=");
		builder.append(images);
		builder.append(", update_date=");
		builder.append(update_date);
		builder.append(", update_by=");
		builder.append(update_by);
		builder.append("]");
		return builder.toString();
	}

}