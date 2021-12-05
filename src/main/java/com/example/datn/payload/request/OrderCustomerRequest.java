package com.example.datn.payload.request;

public class OrderCustomerRequest {
    private String ten;
	private int trang;
	private String xepTheo;
	private boolean thuTu;
    private String email;
    private int customer_id;
    
    
    
    public OrderCustomerRequest() {
        this.ten = "";
		this.trang = 0;
		this.xepTheo = "productId";
		this.thuTu = true;
    }



    public OrderCustomerRequest(String ten, int trang, String xepTheo, boolean thuTu, String email,
            int customer_id) {
        this.ten = ten;
        this.trang = trang;
        this.xepTheo = xepTheo;
        this.thuTu = thuTu;
        this.email = email;
        this.customer_id = customer_id;
    }



    public String getTen() {
        return ten;
    }



    public void setTen(String ten) {
        this.ten = ten;
    }



    public int getTrang() {
        return trang;
    }



    public void setTrang(int trang) {
        this.trang = trang;
    }



    public String getXepTheo() {
        return xepTheo;
    }



    public void setXepTheo(String xepTheo) {
        this.xepTheo = xepTheo;
    }



    public boolean isThuTu() {
        return thuTu;
    }



    public void setThuTu(boolean thuTu) {
        this.thuTu = thuTu;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public int getCustomer_id() {
        return customer_id;
    }



    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    


    
}
