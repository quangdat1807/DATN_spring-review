package com.example.datn.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.example.datn.models.MessageNotifications;
import com.example.datn.models.Order;
import com.example.datn.models.OrderDetail;
import com.example.datn.models.Product;
import com.example.datn.models.TinhTrangDonHang;
import com.example.datn.payload.request.OrderRequest;
import com.example.datn.payload.request.SendMailRequest;
import com.example.datn.payload.response.MessageResponse;
import com.example.datn.repository.MessageNotificationsRepository;
import com.example.datn.repository.OrderDetailRepository;
import com.example.datn.repository.OrderRepository;
import com.example.datn.repository.ProductRepository;
import com.example.datn.service.CartService;
import com.example.datn.service.CustomerService;
import com.example.datn.service.MailService;
import com.sendgrid.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class OderRescontroller {

    @Autowired
    CustomerService customerService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    MailService mailService;

   @Autowired
   MessageNotificationsRepository messageNotificationsRepository;
    
   public final static TinhTrangDonHang DEFAULT_TTDH = new TinhTrangDonHang();
    static {
		DEFAULT_TTDH.setIdTT(1);
      
    }


    @Autowired
    ProductRepository productRepository;


    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> checkoutByUser(@Valid @RequestBody OrderRequest orderRequest){

        long leftLimit = 1L;
        long rightLimit = 100000L;
        long id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        //order
        Order order = new Order();
        order.setOrderId(id);
        order.setProvince(orderRequest.getProvince());
        order.setDistrict(orderRequest.getDistrict());
        order.setWard(orderRequest.getWard()); 
        order.setAddress(orderRequest.getAddress());
        order.setNote(orderRequest.getNote());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setTotal(cartService.getTotal());
        order.setCustomer(customerService.getCustomer());
        order.setTinhtrang(DEFAULT_TTDH);
        order.setFullName(customerService.getCustomer().getHoten());
        orderRepository.save(order);
        //oder detail

        // luu orderItems
		Map<Product, Integer> listItems = cartService.getGioHang().getChiTietGioHang();

		for (Product product : listItems.keySet()) {

            

			OrderDetail orderItem = new OrderDetail();
			int quantity = listItems.get(product);
            
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			orderItem.setQuantity(quantity);
			orderItem.setProductName(product.getTensanpham());
			orderItem.setPrice(product.getGia());
			//orderItem.setTotal((product.getGia()) * quantity);

            
        Optional<Product> productOptional = Optional
        .ofNullable(productRepository.findByProductId(product.getProductId()));
Product pr = productOptional.get();
double quantityOrder = listItems.get(product);
 double sl = pr.getSoluong();

 double slcl = sl - quantityOrder;

 System.out.println("sonne" + slcl);

 pr.setSoluong(slcl);
 productRepository.save(pr);

 SendMailRequest emailrequest = new SendMailRequest();
        
        emailrequest.setOrderId(id);
        emailrequest.setToEmail(orderRequest.getEmail());
        emailrequest.setTieuDe("Cảm ơn bạn đã tin tưởng và đặt hàng tại Đức Phát");
        
        
        Response response =mailService.sendemail(emailrequest);

            orderDetailRepository.save(orderItem);
			
		}


        MessageNotifications ntn = new MessageNotifications();
        ntn.setOrderId(id);

        messageNotificationsRepository.save(ntn);

        //mailService.sendMailWithOrderId(order.getOrderId());

        cartService.getGioHang().getChiTietGioHang().clear();

        return ResponseEntity.ok(new MessageResponse("check out success!"));
    }


    

    @PostMapping("/checkoutByUser")
    public ResponseEntity<?> checkOut(@Valid @RequestBody OrderRequest orderRequest){

        long leftLimit = 1L;
        long rightLimit = 100000L;
        long id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        
        //order
        
        Order order = new Order();
        order.setOrderId(id);
        order.setProvince(orderRequest.getProvince());
        order.setDistrict(orderRequest.getDistrict());
        order.setWard(orderRequest.getWard()); 
        order.setAddress(orderRequest.getAddress());
        order.setNote(orderRequest.getNote());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setEmail(orderRequest.getEmail());
        order.setFullName(orderRequest.getName());
        order.setTotal(cartService.getTotal());
        order.setTinhtrang(DEFAULT_TTDH);
        
        orderRepository.save(order);
        //oder detail

        // luu orderItems
		Map<Product, Integer> listItems = cartService.getGioHang().getChiTietGioHang();

		for (Product product : listItems.keySet()) {

            

			OrderDetail orderItem = new OrderDetail();
			int quantity = listItems.get(product);
            
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			orderItem.setQuantity(quantity);
			orderItem.setProductName(product.getTensanpham());
			orderItem.setPrice(product.getGia());
			//orderItem.setTotal((product.getGia()) * quantity);

            orderDetailRepository.save(orderItem);
			
		}


        MessageNotifications ntn = new MessageNotifications();
        ntn.setOrderId(order.getOrderId());

        messageNotificationsRepository.save(ntn);

        //mailService.sendMailWithOrderId(order.getOrderId());

        SendMailRequest emailrequest = new SendMailRequest();
        
        emailrequest.setOrderId(id);
        emailrequest.setToEmail(orderRequest.getEmail());
        emailrequest.setTieuDe("Cảm ơn bạn đã tin tưởng và đặt hàng tại Đức Phát");
        
        
        Response response =mailService.sendemail(emailrequest);

        cartService.getGioHang().getChiTietGioHang().clear();

        return ResponseEntity.ok(new MessageResponse("check out success!"));
    }
}
