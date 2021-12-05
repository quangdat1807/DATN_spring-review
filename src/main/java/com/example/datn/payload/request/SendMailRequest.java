package com.example.datn.payload.request;

public class SendMailRequest {
    
    private String toEmail;
	
	private String tieuDe;
	
	private Long orderId;
	
	

	public SendMailRequest(String toEmail, String tieuDe, Long orderId) {
		
		this.toEmail = toEmail;
		this.tieuDe = tieuDe;
		this.orderId = orderId;
	}



	public SendMailRequest() {
		
		// TODO Auto-generated constructor stub
	}



	public String getToEmail() {
		return toEmail;
	}



	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}



	public String getTieuDe() {
		return tieuDe;
	}



	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}



	public Long getOrderId() {
		return orderId;
	}



	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
