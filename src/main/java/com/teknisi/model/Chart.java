package com.teknisi.model;

import java.util.Date;

public class Chart {
	public Date created_date;
	public int count;
	public String status;
	
	public Chart(){	}

	public Chart(Date created_date, int count, String status) {
		super();
		this.created_date = created_date;
		this.count = count;
		this.status = status;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
		builder.append("Chart [created_date=");
		builder.append(created_date);
		builder.append(", count=");
		builder.append(count);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
	
		
}
