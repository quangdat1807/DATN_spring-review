package com.example.datn.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.datn.models.Category;
import com.example.datn.models.Product;
import com.example.datn.models.ThuongHieu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.datn.repository.CategoryRepository;
import com.example.datn.repository.ProductRepository;
import com.example.datn.repository.TrademakeRepository;
import com.example.datn.service.CartService;
import com.example.datn.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
    

    @Autowired
	CartService cartService;

    @GetMapping("/detail/{productname}")
    public String demo(@PathVariable String productname, Model model, HttpServletRequest request, HttpSession session){
         session = request.getSession();

		 if (session.getAttribute("idth") == null || session.getAttribute("idCate") == null) {
			int idth = 1;
			int idcate = 1;

			List<Product> products = productRepository.findByRelatedProduct(idth, idcate);

	   model.addAttribute("products", products);
		}else {
			int idth = ((Integer) (session.getAttribute("idth")) ).intValue();
		 int idcate = ((Integer) (session.getAttribute("idCate")) ).intValue();
		 
		 List<Product> products = productRepository.findByRelatedProduct(idth, idcate);
		
		
		model.addAttribute("products", products);
		}

		 
		
		model.addAttribute("cart", cartService.getGioHang());
	
        return "demo";
    }

    @Autowired
    ProductRepository productRepository;


	@Autowired
	CategoryRepository categoryRepository;

    @GetMapping("/")
    public String po(Model model) {

    List<Product> productByPanasonic = productRepository.findByTitleContaining();

	List<Product> productByNanoco = productRepository.findByTrade();

	List<Category> categories = categoryRepository.findAll();



	

        model.addAttribute("productByPana", productByPanasonic);
		model.addAttribute("productByNano", productByNanoco);
		model.addAttribute("cate", categories);


		
		long leftLimit = 1L;
    long rightLimit = 100000L;
    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

	System.out.println("testRandom: " +generatedLong );

        return "index";
    }


    @GetMapping("/checkout/success")
    public String oderSuccess(){

        return "ordersuccess";
    }

    private static final int TOI_DA_SAN_PHAM = 3;

    @GetMapping("/product")
    public String listproduct(@RequestParam(name = "sort", defaultValue = "product_id") String sortFeild,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(name = "price_min", defaultValue = "0") Long giamin,
			@RequestParam(name = "price_max", defaultValue = "10000000") Long giamax,
			@RequestParam(name = "sortBy", defaultValue = "ASC") String sortBy, Model model, HttpServletRequest request) {

		Sort sortable = null;

		if (sortBy.equals("DESC")) {
			sortable = Sort.by(Sort.Direction.DESC, sortFeild);
		}

		if (sortBy.equals("ASC")) {
			sortable = Sort.by(Sort.Direction.ASC, sortFeild);
		}

		Pageable pager = PageRequest.of(pageIndex, TOI_DA_SAN_PHAM, sortable);

		
		// l???y s???n ph???m
		Page<Product> productPage = productRepository.findProductAll(giamin, giamax, pager);
		List<ThuongHieu> thuonghieu = trademakeRepository.findAll();
		//category
		List<Category> categories = categoryRepository.findAll();

		String test = request.getRequestURL().toString() + "?" + request.getQueryString();

		System.out.println("sss" + test);
		model.addAttribute("requestUrl", test);


		model.addAttribute("cate", categories);

		model.addAttribute("thuonghieu", thuonghieu);

		model.addAttribute("countProduct", productPage.getTotalElements());

		model.addAttribute("products", productPage.getContent());
		// truy???n v??o s??? l?????ng page t???i ??a
		model.addAttribute("maxPage", productPage.getTotalPages());
		model.addAttribute("demo", productPage.getTotalElements());

		// truy???n v??o page hi???n t???i
		model.addAttribute("currentPage", productPage.getNumber());

		// truy???n v??o s???p x???p theo id, name, price
		model.addAttribute("sortName", sortFeild);

		// truy???n v??o ki???u s???p x???p 
		model.addAttribute("sortDesc", sortBy);

        return "product";
    }


	@GetMapping("/category/{catename}")
    public String listcate(@PathVariable String catename, @RequestParam(name = "sort", defaultValue = "product_id") String sortFeild,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(name = "trademake", defaultValue = "") String th,
			@RequestParam(name = "price_min", defaultValue = "0") Long giamin,
			@RequestParam(name = "price_max", defaultValue = "10000000") Long giamax,
			@RequestParam(name = "sortBy", defaultValue = "ASC") String sortBy, Model model, HttpServletRequest request) {

		Sort sortable = null;

		if (sortBy.equals("DESC")) {
			sortable = Sort.by(Sort.Direction.DESC, sortFeild);
		}

		if (sortBy.equals("ASC")) {
			sortable = Sort.by(Sort.Direction.ASC, sortFeild);
		}

		Pageable pager = PageRequest.of(pageIndex, TOI_DA_SAN_PHAM, sortable);

		if (th.equals("")) {
			Page<Product> productPage = productRepository.findCategory(catename,giamin, giamax, pager);

			model.addAttribute("countProduct", productPage.getTotalElements());

		model.addAttribute("products", productPage.getContent());
		// truy???n v??o s??? l?????ng page t???i ??a
		model.addAttribute("maxPage", productPage.getTotalPages());
		model.addAttribute("demo", productPage.getTotalElements());

		// truy???n v??o page hi???n t???i
		model.addAttribute("currentPage", productPage.getNumber());

		
		}else{
			Page<Product> productPage = productRepository.findCategoryAndTrademake(catename,th,giamin, giamax, pager);

			model.addAttribute("countProduct", productPage.getTotalElements());

		model.addAttribute("products", productPage.getContent());
		// truy???n v??o s??? l?????ng page t???i ??a
		model.addAttribute("maxPage", productPage.getTotalPages());
		model.addAttribute("demo", productPage.getTotalElements());

		// truy???n v??o page hi???n t???i
		model.addAttribute("currentPage", productPage.getNumber());

		
		}

		
		
		
		
		List<ThuongHieu> thuonghieu = trademakeRepository.findAll();
		//category
		List<Category> categories = categoryRepository.findAll();


		model.addAttribute("cate", categories);

		model.addAttribute("thuonghieu", thuonghieu);

		

		// truy???n v??o s???p x???p theo id, name, price
		model.addAttribute("sortName", sortFeild);

		// truy???n v??o ki???u s???p x???p 
		model.addAttribute("sortDesc", sortBy);

		String test = request.getRequestURL().toString() + "?" + request.getQueryString();

		System.out.println("sss" + test);
		model.addAttribute("requestUrl", test);

        return "category";
    }


	@Autowired
	TrademakeRepository trademakeRepository;

	@GetMapping("/trademake/{name}")
    public String filterByTremak(@PathVariable String name, @RequestParam(name = "price_min", defaultValue = "0") Long giamin,
			@RequestParam(name = "price_max", defaultValue = "10000000") Long giamax,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(name = "sortBy", defaultValue = "ASC") String sortBy, Model model) {

		
		Pageable pager = PageRequest.of(pageIndex, TOI_DA_SAN_PHAM);

		// l???y s???n ph???m
		Page<Product> productPage = productRepository.findByTradeMake(name, giamin, giamax, pager);

		List<ThuongHieu> thuonghieu = trademakeRepository.findAll();

		List<Category> categories = categoryRepository.findAll();


		model.addAttribute("cate", categories);

		model.addAttribute("thuonghieu", thuonghieu);

		model.addAttribute("countProduct", productPage.getTotalElements());
		model.addAttribute("demo", productPage.getTotalElements());

		model.addAttribute("products", productPage.getContent());
		// truy???n v??o s??? l?????ng page t???i ??a
		model.addAttribute("maxPage", productPage.getTotalPages());

		// truy???n v??o page hi???n t???i
		model.addAttribute("currentPage", productPage.getNumber());

		model.addAttribute("trademaxName", name);
		model.addAttribute("price_min", giamin);
		model.addAttribute("price_max", giamax);

		// truy???n v??o s???p x???p theo id, name, price
		//model.addAttribute("sortName", sortFeild);

		// truy???n v??o ki???u s???p x???p 
		model.addAttribute("sortDesc", sortBy);

        return "filter_product";
    }


    // Search product
	@GetMapping("search")
	public String search(@RequestParam(name = "sort", defaultValue = "productId") String sortFeild,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
			// th??m t??? kh??a t??m ki???m
			@RequestParam(name = "query", defaultValue = "") String productname, 
			@RequestParam(name = "sortBy", defaultValue = "ASC") String sortBy, Model model
			) {

		Sort sortable = null;

		if (sortBy.equals("DESC")) {
			sortable = Sort.by(Sort.Direction.DESC, sortFeild);
		}

		if (sortBy.equals("ASC")) {
			sortable = Sort.by(Sort.Direction.ASC, sortFeild);
		}

		Pageable pager = PageRequest.of(pageIndex, TOI_DA_SAN_PHAM, sortable);
		// l???y s???n ph???m
		Page<Product> productPage = productRepository.findByTensanphamContainingIgnoreCase(productname, pager);

		List<ThuongHieu> thuonghieu = trademakeRepository.findAll();

		model.addAttribute("thuonghieu", thuonghieu);

		if (productname.equals("") ) {
			model.addAttribute("demo", 0);
		}else {
			model.addAttribute("demo", productPage.getTotalElements());
		}
		model.addAttribute("searchName", productname);
		model.addAttribute("products", productPage.getContent());
		model.addAttribute("maxPage", productPage.getTotalPages());
		// truy???n v??o page hi???n t???i
		model.addAttribute("currentPage", productPage.getNumber());
		
		// truy???n v??o s???p x???p theo id, name, price
		model.addAttribute("sortName", sortFeild);

		// truy???n v??o ki???u s???p x???p desc
		model.addAttribute("sortDesc", sortBy);

		return "search";
	}

@Autowired
CustomerService customerService;

	@GetMapping("/myOrder")
    public String OrderCustomer(Model model) {

		if (customerService.isCustomerLogin()) {
			//model.addAttribute("cartStatus", 0);
			
			model.addAttribute("name",customerService.getCustomer().getHoten());
			model.addAttribute("address",customerService.getCustomer().getDiachi());
			model.addAttribute("email",customerService.getCustomer().getUser().getEmail());
			model.addAttribute("customer_id",customerService.getCustomer().getId());
			
			
			return "OrderCustomer";
	}


        return "404Page";
    }




    
	
    
    

   



  
}
