package com.example.datn.models;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "tinhtrangdonhang")
public class TinhTrangDonHang {
    @Id
	@GeneratedValue
	private int idTT;
	
	private String tinhTrang;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY
			, mappedBy = "tinhtrang")
	private List<Order> orders;

    public TinhTrangDonHang() {
    }

    public TinhTrangDonHang(int idTT, String tinhTrang, List<Order> orders) {
        this.idTT = idTT;
        this.tinhTrang = tinhTrang;
        this.orders = orders;
    }

    public int getIdTT() {
        return idTT;
    }

    public void setIdTT(int idTT) {
        this.idTT = idTT;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

	

}