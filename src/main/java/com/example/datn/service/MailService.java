package com.example.datn.service;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.datn.models.Order;
import com.example.datn.models.OrderDetail;
import com.example.datn.payload.request.ForgotPasswordRequest;
import com.example.datn.payload.request.SendMailRequest;
import com.example.datn.repository.OrderDetailRepository;
import com.example.datn.repository.OrderRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.bouncycastle.asn1.pkcs.SafeBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailService {
   
//
//    @Autowired
//	JavaMailSender sender;
//	
//private List<MimeMessage> mailList = new ArrayList<>();
//	
///*
// * public void pushMail(String to) { MimeMessage message =
// * sender.createMimeMessage(); MimeMessageHelper helper = new
// * MimeMessageHelper(message); try {
// * helper.setFrom("Thanh Son <sonthanh111@outlook.com>"); helper.setTo(to);
// * helper.setSubject("Thank you"); helper.setText("Đặt hàng thành công."); }
// * catch (MessagingException e) {
// * System.out.println("Không thể thêm mail vào hàng đợi"); e.printStackTrace();
// * } mailList.add(message); }
// */
//	
//	
//	
//
//	public void sendMailWithResetPassword(String email, String urlRest) {
//
//		// lay data
//		
//
//		MimeMessage message = sender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//
//
//        String content = "<p>Hello,</p>"
//            + "<p>You have requested to reset your password.</p>"
//            + "<p>Click the link below to change your password:</p>"
//            + "<p><a href=\"" + urlRest + "\">Change my password</a></p>"
//            + "<br>"
//            + "<p>Ignore this email if you do remember your password, "
//            + "or you have not made the request.</p>";
//		try {
//			
//			helper.setFrom("Thanh Son <sondtpk01429@fpt.edu.vn>");
//			helper.setTo(email);
//			helper.setSubject("Thank you");
//			helper.setText(content, true);
//		} catch (MessagingException e) {
//			System.out.println("Không thể thêm mail vào hàng đợi");
//			e.printStackTrace();
//		}
//		mailList.add(message);
//		// Send Message!
//		this.sender.send(message);
//
//	}
//
//
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

    
	
	@Autowired
	SendGrid sendGrid;
	
	public Response sendemail(SendMailRequest emailrequest) 
	{
		long orderId = emailrequest.getOrderId();
		 //lay data
		Order order = orderRepository.getById(orderId);
		List<OrderDetail> orderItems = orderDetailRepository.findOrderItems(orderId);


		String totalFormat = String.format("%,.0f", order.getTotal());

		// tao noi dung
		

		
				String sb = "<!DOCTYPE html>" +
				"<html" +
				"  xmlns=\"http://www.w3.org/1999/xhtml\"" +
				"  xmlns:v=\"urn:schemas-microsoft-com:vml\"" +
				"  xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
				">" +
				"  <head>" +
				"    <title>[[data:firstname:\"\"]]</title>" +
				"    <!--[if !mso]><!-->" +
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />" +
				"    <!--<![endif]-->" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" +
				"    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\" />" +
				"    <style type=\"text/css\">" +
				"      #outlook a {" +
				"        padding: 0;" +
				"      }" +
				"      body {" +
				"        margin: 0;" +
				"        padding: 0;" +
				"        -webkit-text-size-adjust: 100%;" +
				"        -ms-text-size-adjust: 100%;" +
				"      }" +
				"      table," +
				"      td {" +
				"        border-collapse: collapse;" +
				"        mso-table-lspace: 0pt;" +
				"        mso-table-rspace: 0pt;" +
				"      }" +
				"      img {" +
				"        border: 0;" +
				"        height: auto;" +
				"        line-height: 100%;" +
				"        outline: none;" +
				"        text-decoration: none;" +
				"        -ms-interpolation-mode: bicubic;" +
				"      }" +
				"      p {" +
				"        display: block;" +
				"        margin: 13px 0;" +
				"      }" +
				"    </style>" +
				"    <!--[if mso]>" +
				"      <noscript>" +
				"        <xml>" +
				"          <o:OfficeDocumentSettings>" +
				"            <o:AllowPNG />" +
				"            <o:PixelsPerInch>96</o:PixelsPerInch>" +
				"          </o:OfficeDocumentSettings>" +
				"        </xml>" +
				"      </noscript>" +
				"    <![endif]-->" +
				"    <!--[if lte mso 11]>" +
				"      <style type=\"text/css\">" +
				"        .mj-outlook-group-fix {" +
				"          width: 100% !important;" +
				"        }" +
				"      </style>" +
				"    <![endif]-->" +
				"    <style type=\"text/css\">" +
				"      @media only screen and (min-width: 480px) {" +
				"        .mj-column-per-100 {" +
				"          width: 100% !important;" +
				"          max-width: 100%;" +
				"        }" +
				"        .mj-column-per-66 {" +
				"          width: 66% !important;" +
				"          max-width: 66%;" +
				"        }" +
				"        .mj-column-per-33 {" +
				"          width: 33% !important;" +
				"          max-width: 33%;" +
				"        }" +
				"      }" +
				"    </style>" +
				"    <style media=\"screen and (min-width:480px)\">" +
				"      .moz-text-html .mj-column-per-100 {" +
				"        width: 100% !important;" +
				"        max-width: 100%;" +
				"      }" +
				"      .moz-text-html .mj-column-per-66 {" +
				"        width: 66% !important;" +
				"        max-width: 66%;" +
				"      }" +
				"      .moz-text-html .mj-column-per-33 {" +
				"        width: 33% !important;" +
				"        max-width: 33%;" +
				"      }" +
				"    </style>" +
				"    <style type=\"text/css\">" +
				"      [owa] .mj-column-per-100 {" +
				"        width: 100% !important;" +
				"        max-width: 100%;" +
				"      }" +
				"      [owa] .mj-column-per-66 {" +
				"        width: 66% !important;" +
				"        max-width: 66%;" +
				"      }" +
				"      [owa] .mj-column-per-33 {" +
				"        width: 33% !important;" +
				"        max-width: 33%;" +
				"      }" +
				"    </style>" +
				"    <style type=\"text/css\">" +
				"      @media only screen and (max-width: 480px) {" +
				"        table.mj-full-width-mobile {" +
				"          width: 100% !important;" +
				"        }" +
				"        td.mj-full-width-mobile {" +
				"          width: auto !important;" +
				"        }" +
				"      }" +
				"    </style>" +
				"  </head>" +
				"  <body style=\"word-spacing: normal; background-color: #f4f4f4\">" +
				"    <div style=\"background-color: #f4f4f4\">" +
				"      <!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->" +
				"      <div style=\"margin: 0px auto; max-width: 600px\">" +
				"        <table" +
				"          align=\"center\"" +
				"          border=\"0\"" +
				"          cellpadding=\"0\"" +
				"          cellspacing=\"0\"" +
				"          role=\"presentation\"" +
				"          style=\"width: 100%\"" +
				"        >" +
				"          <tbody>" +
				"            <tr>" +
				"              <td" +
				"                style=\"" +
				"                  direction: ltr;" +
				"                  font-size: 0px;" +
				"                  padding: 20px 0px 20px 0px;" +
				"                  text-align: center;" +
				"                \"" +
				"              >" +
				"                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-100 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: top;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    style=\"vertical-align: top\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td" +
				"                          align=\"left\"" +
				"                          style=\"" +
				"                            font-size: 0px;" +
				"                            padding: 0px 0px 0px 25px;" +
				"                            padding-top: 0px;" +
				"                            padding-bottom: 0px;" +
				"                            word-break: break-word;" +
				"                          \"" +
				"                        >" +
				"                          <div" +
				"                            style=\"" +
				"                              font-family: Arial, sans-serif;" +
				"                              font-size: 13px;" +
				"                              letter-spacing: normal;" +
				"                              line-height: 1;" +
				"                              text-align: left;" +
				"                              color: #000000;" +
				"                            \"" +
				"                          >" +
				"                            <p style=\"margin: 10px 0\"></p>" +
				"                          </div>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"              </td>" +
				"            </tr>" +
				"          </tbody>" +
				"        </table>" +
				"      </div>" +
				"      <!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"#ffffff\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->" +
				"      <div" +
				"        style=\"" +
				"          background: #ffffff;" +
				"          background-color: #ffffff;" +
				"          margin: 0px auto;" +
				"          max-width: 600px;" +
				"        \"" +
				"      >" +
				"        <table" +
				"          align=\"center\"" +
				"          border=\"0\"" +
				"          cellpadding=\"0\"" +
				"          cellspacing=\"0\"" +
				"          role=\"presentation\"" +
				"          style=\"background: #ffffff; background-color: #ffffff; width: 100%\"" +
				"        >" +
				"          <tbody>" +
				"            <tr>" +
				"              <td" +
				"                style=\"" +
				"                  direction: ltr;" +
				"                  font-size: 0px;" +
				"                  padding: 30px 0 0 0;" +
				"                  padding-top: 30px;" +
				"                  text-align: center;" +
				"                \"" +
				"              >" +
				"                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:396px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-66 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: top;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td style=\"vertical-align: top; padding: 0 0 0 0\">" +
				"                          <table" +
				"                            border=\"0\"" +
				"                            cellpadding=\"0\"" +
				"                            cellspacing=\"0\"" +
				"                            role=\"presentation\"" +
				"                            width=\"100%\"" +
				"                          >" +
				"                            <tbody>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 16px 25px 0px 25px;" +
				"                                    padding-top: 16px;" +
				"                                    padding-right: 25px;" +
				"                                    padding-left: 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <table" +
				"                                    border=\"0\"" +
				"                                    cellpadding=\"0\"" +
				"                                    cellspacing=\"0\"" +
				"                                    role=\"presentation\"" +
				"                                    style=\"" +
				"                                      border-collapse: collapse;" +
				"                                      border-spacing: 0px;" +
				"                                    \"" +
				"                                  >" +
				"                                    <tbody>" +
				"                                      <tr>" +
				"                                        <td style=\"width: 285px\">" +
				"                                          <img" +
				"                                            alt=\"\"" +
				"                                            height=\"auto\"" +
				"                                            src=\"https://012qg.mjt.lu/tplimg/012qg/b/10li3/utx.png\"" +
				"                                            style=\"" +
				"                                              border: none;" +
				"                                              border-radius: px;" +
				"                                              display: block;" +
				"                                              outline: none;" +
				"                                              text-decoration: none;" +
				"                                              height: auto;" +
				"                                              width: 100%;" +
				"                                              font-size: 13px;" +
				"                                            \"" +
				"                                            width=\"285\"" +
				"                                          />" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </tbody>" +
				"                                  </table>" +
				"                                </td>" +
				"                              </tr>" +
				"                            </tbody>" +
				"                          </table>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td><td class=\"\" style=\"vertical-align:top;width:198px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-33 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: top;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td style=\"vertical-align: top; padding: 0 0 0 0\">" +
				"                          <table" +
				"                            border=\"0\"" +
				"                            cellpadding=\"0\"" +
				"                            cellspacing=\"0\"" +
				"                            role=\"presentation\"" +
				"                            width=\"100%\"" +
				"                          >" +
				"                            <tbody>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 0 0 0;" +
				"                                    padding-top: 0px;" +
				"                                    padding-bottom: 0px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  ></div>" +
				"                                </td>" +
				"                              </tr>" +
				"                            </tbody>" +
				"                          </table>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"              </td>" +
				"            </tr>" +
				"          </tbody>" +
				"        </table>" +
				"      </div>" +
				"      <!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"#FFFFFF\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->" +
				"      <div" +
				"        style=\"" +
				"          background: #ffffff;" +
				"          background-color: #ffffff;" +
				"          margin: 0px auto;" +
				"          max-width: 600px;" +
				"        \"" +
				"      >" +
				"        <table" +
				"          align=\"center\"" +
				"          border=\"0\"" +
				"          cellpadding=\"0\"" +
				"          cellspacing=\"0\"" +
				"          role=\"presentation\"" +
				"          style=\"background: #ffffff; background-color: #ffffff; width: 100%\"" +
				"        >" +
				"          <tbody>" +
				"            <tr>" +
				"              <td" +
				"                style=\"" +
				"                  direction: ltr;" +
				"                  font-size: 0px;" +
				"                  padding: 0 0 0px 0;" +
				"                  padding-bottom: 0px;" +
				"                  text-align: center;" +
				"                \"" +
				"              >" +
				"                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-100 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: top;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td style=\"vertical-align: top; padding: 0 0 0 0\">" +
				"                          <table" +
				"                            border=\"0\"" +
				"                            cellpadding=\"0\"" +
				"                            cellspacing=\"0\"" +
				"                            role=\"presentation\"" +
				"                            width=\"100%\"" +
				"                          >" +
				"                            <tbody>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 35px 25px 0 25px;" +
				"                                    padding-top: 35px;" +
				"                                    padding-right: 25px;" +
				"                                    padding-bottom: 0px;" +
				"                                    padding-left: 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  >" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      data-testid=\"-MzID1hu1mY\"" +
				"                                      style=\"" +
				"                                        margin: 10px 0;" +
				"                                        margin-top: 10px;" +
				"                                        margin-bottom: 10px;" +
				"                                      \"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          color: #575757;" +
				"                                          font-family: Arial;" +
				"                                          font-size: 20px;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"" +
				"                                      >" +
				"                                        Cảm ơn bạn đã đặt hàng tại Đức Phát</span" +
				"                                      >" +
				"                                    </p>" +
				"                                  </div>" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 25px 0 25px;" +
				"                                    padding-top: 0px;" +
				"                                    padding-right: 25px;" +
				"                                    padding-bottom: 0px;" +
				"                                    padding-left: 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  >" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      data-testid=\"oE3Rm9kQNyl3\"" +
				"                                      style=\"margin: 10px 0; margin-top: 10px\"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          color: #575757;" +
				"                                          font-family: Arial;" +
				"                                          font-size: 14px;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"" +
				"                                      >" +
				"										Xin chào <strong>"+order.getFullName()+"</strong> ,</span>"+
				"                                    </p>" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      data-testid=\"oE3Rm9kQNyl3\"" +
				"                                      style=\"margin: 10px 0\"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          color: #575757;" +
				"                                          font-family: Arial;" +
				"                                          font-size: 14px;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"" +
				"" +
				"" +
				"" +
				"                                      >" +
				"Đức phát đã nhận được yêu cầu đặt hàng của bạn và đang xử lý nhé. Bạn sẽ nhận được thông báo tiếp theo khi đơn hàng đã sẵn sàng được giao." +"</span>"+
				"                                    </p>" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      data-testid=\"oE3Rm9kQNyl3\"" +
				"                                      style=\"" +
				"                                        margin: 10px 0;" +
				"                                        margin-bottom: 10px;" +
				"                                      \"" +
				"                                    >" +
				"                                      &nbsp;" +
				"                                    </p>" +
				"                                  </div>" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 10px 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <p" +
				"                                    style=\"" +
				"                                      border-top: solid 1px #d9d9d9;" +
				"                                      font-size: 1px;" +
				"                                      margin: 0px auto;" +
				"                                      width: 100%;" +
				"                                    \"" +
				"                                  ></p>" +
				"                                  <!--[if mso | IE" +
				"                                    ]><table" +
				"                                      align=\"center\"" +
				"                                      border=\"0\"" +
				"                                      cellpadding=\"0\"" +
				"                                      cellspacing=\"0\"" +
				"                                      style=\"" +
				"                                        border-top: solid 1px #d9d9d9;" +
				"                                        font-size: 1px;" +
				"                                        margin: 0px auto;" +
				"                                        width: 550px;" +
				"                                      \"" +
				"                                      role=\"presentation\"" +
				"                                      width=\"550px\"" +
				"                                    >" +
				"                                      <tr>" +
				"                                        <td style=\"height: 0; line-height: 0\">" +
				"                                          &nbsp;" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </table><!" +
				"                                  [endif]-->" +
				"                                </td>" +
				"                              </tr>" ;

				for (int i = 0; i < orderItems.size(); i++) {
					OrderDetail orderItem = orderItems.get(i);
					
					double price = (orderItem.getPrice()).doubleValue();
					double totalprice = orderItem.getQuantity() * price;
					DecimalFormat formatter = new DecimalFormat("###,###");
					String totalPriceFormat = String.format("%,.0f", totalprice);
					
					
					sb = sb + "<tr>" +
					"                                <td" +
					"                                  align=\"left\"" +
					"                                  style=\"" +
					"                                    font-size: 0px;" +
					"                                    padding: 0 25px 0 25px;" +
					"                                    padding-top: 0px;" +
					"                                    padding-right: 25px;" +
					"                                    padding-bottom: 0px;" +
					"                                    padding-left: 25px;" +
					"                                    word-break: break-word;" +
					"                                  \"" +
					"                                >" +
					"                                  <div" +
					"                                    style=\"" +
					"                                      font-family: Arial, sans-serif;" +
					"                                      font-size: 13px;" +
					"                                      letter-spacing: normal;" +
					"                                      line-height: 1;" +
					"                                      text-align: left;" +
					"                                      color: #000000;" +
					"                                    \"" +
					"                                  >" +
					"                                    <p" +
					"                                      style=\"" +
					"                                        text-align: left;" +
					"                                        margin: 10px 0;" +
					"                                        margin-top: 10px;" +
					"                                        margin-bottom: 10px;" +
					"                                      \"" +
					"                                    >" +
					"                                      <span" +
					"                                        style=\"" +
					"                                          font-size: 14px;" +
					"                                          text-align: left;" +
					"                                          color: #575757;" +
					"                                          font-family: Arial;" +
					"                                          line-height: 22px;" +
					"                                        \"" +
					"                                        ><b>"+orderItem.getProductName()+"</b>" +
					"										<b style=\"padding-left:20px;\" id=\"quantity\">x"+orderItem.getQuantity()+"</b>" +
					"										<b style=\"float:right;\" id=\"price\">"+totalPriceFormat+"đ</b>" +
					"										" +
					"										</span" +
					"                                      >" +
					"                                    </p>" +
					"                                  </div>" +
					"                                </td>" +
					"                              </tr>";
				}
				
				
				sb+= 
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 10px 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <p" +
				"                                    style=\"" +
				"                                      border-top: solid 1px #d9d9d9;" +
				"                                      font-size: 1px;" +
				"                                      margin: 0px auto;" +
				"                                      width: 100%;" +
				"                                    \"" +
				"                                  ></p>" +
				"                                  <!--[if mso | IE" +
				"                                    ]><table" +
				"                                      align=\"center\"" +
				"                                      border=\"0\"" +
				"                                      cellpadding=\"0\"" +
				"                                      cellspacing=\"0\"" +
				"                                      style=\"" +
				"                                        border-top: solid 1px #d9d9d9;" +
				"                                        font-size: 1px;" +
				"                                        margin: 0px auto;" +
				"                                        width: 550px;" +
				"                                      \"" +
				"                                      role=\"presentation\"" +
				"                                      width=\"550px\"" +
				"                                    >" +
				"                                      <tr>" +
				"                                        <td style=\"height: 0; line-height: 0\">" +
				"                                          &nbsp;" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </table><!" +
				"                                  [endif]-->" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 25px 0 25px;" +
				"                                    padding-top: 0px;" +
				"                                    padding-right: 25px;" +
				"                                    padding-bottom: 0px;" +
				"                                    padding-left: 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  >" +
				"                                    <p" +
				"                                      style=\"" +
				"                                        text-align: left;" +
				"                                        margin: 10px 0;" +
				"                                        margin-top: 10px;" +
				"                                        margin-bottom: 10px;" +
				"                                      \"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          font-size: 14px;" +
				"                                          text-align: left;" +
				"                                          color: #575757;" +
				"                                          font-family: Arial;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"                                        ><b" +
				"                                          >Tổng tiền                                " +
				"                                                                               " +
				"                                                                               " +
				"                                                      </b" +
				"											<b style=\"float:right;\" id=\"price\">"+totalFormat+"đ</b>" +
				"                                        </span" +
				"                                      >" +
				"                                    </p>" +
				"                                  </div>" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 10px 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <p" +
				"                                    style=\"" +
				"                                      border-top: solid 1px #d9d9d9;" +
				"                                      font-size: 1px;" +
				"                                      margin: 0px auto;" +
				"                                      width: 100%;" +
				"                                    \"" +
				"                                  ></p>" +
				"                                  <!--[if mso | IE" +
				"                                    ]><table" +
				"                                      align=\"center\"" +
				"                                      border=\"0\"" +
				"                                      cellpadding=\"0\"" +
				"                                      cellspacing=\"0\"" +
				"                                      style=\"" +
				"                                        border-top: solid 1px #d9d9d9;" +
				"                                        font-size: 1px;" +
				"                                        margin: 0px auto;" +
				"                                        width: 550px;" +
				"                                      \"" +
				"                                      role=\"presentation\"" +
				"                                      width=\"550px\"" +
				"                                    >" +
				"                                      <tr>" +
				"                                        <td style=\"height: 0; line-height: 0\">" +
				"                                          &nbsp;" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </table><!" +
				"                                  [endif]-->" +
				"                                </td>" +
				"                              </tr>" +
				"                            </tbody>" +
				"                          </table>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"              </td>" +
				"            </tr>" +
				"          </tbody>" +
				"        </table>" +
				"      </div>" +
				"      <!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"#ffffff\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->" +
				"      <div" +
				"        style=\"" +
				"          background: #ffffff;" +
				"          background-color: #ffffff;" +
				"          margin: 0px auto;" +
				"          max-width: 600px;" +
				"        \"" +
				"      >" +
				"        <table" +
				"          align=\"center\"" +
				"          border=\"0\"" +
				"          cellpadding=\"0\"" +
				"          cellspacing=\"0\"" +
				"          role=\"presentation\"" +
				"          style=\"background: #ffffff; background-color: #ffffff; width: 100%\"" +
				"        >" +
				"          <tbody>" +
				"            <tr>" +
				"              <td" +
				"                style=\"" +
				"                  direction: ltr;" +
				"                  font-size: 0px;" +
				"                  padding: 10px 0 0 0;" +
				"                  padding-top: 10px;" +
				"                  text-align: center;" +
				"                \"" +
				"              >" +
				"                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:top;width:600px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-100 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: top;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td style=\"vertical-align: top; padding: 0 0 0 0\">" +
				"                          <table" +
				"                            border=\"0\"" +
				"                            cellpadding=\"0\"" +
				"                            cellspacing=\"0\"" +
				"                            role=\"presentation\"" +
				"                            width=\"100%\"" +
				"                          >" +
				"                            <tbody>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 0 0 0;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <table" +
				"                                    border=\"0\"" +
				"                                    cellpadding=\"0\"" +
				"                                    cellspacing=\"0\"" +
				"                                    role=\"presentation\"" +
				"                                    style=\"" +
				"                                      border-collapse: collapse;" +
				"                                      border-spacing: 0px;" +
				"                                    \"" +
				"                                  >" +
				"                                    <tbody>" +
				"                                      <tr>" +
				"                                        <td style=\"width: 64px\">" +
				"                                          <img" +
				"                                            alt=\"\"" +
				"                                            height=\"auto\"" +
				"                                            src=\"http://o4o9.mj.am/img/o4o9/b/1o96w/viog.png\"" +
				"                                            style=\"" +
				"                                              border: none;" +
				"                                              border-radius: px;" +
				"                                              display: block;" +
				"                                              outline: none;" +
				"                                              text-decoration: none;" +
				"                                              height: auto;" +
				"                                              width: 100%;" +
				"                                              font-size: 13px;" +
				"                                            \"" +
				"                                            width=\"64\"" +
				"                                          />" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </tbody>" +
				"                                  </table>" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    background: #ffffff;" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 0 0 0;" +
				"                                    padding-top: 0px;" +
				"                                    padding-bottom: 0px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  >" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      style=\"" +
				"                                        text-align: center;" +
				"                                        margin: 10px 0;" +
				"                                        margin-top: 10px;" +
				"                                        margin-bottom: 10px;" +
				"                                      \"" +
				"                                      data-testid=\"LcbKlTtgYJhp\"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          color: #575757;" +
				"                                          font-family: Arial;" +
				"                                          font-size: 14px;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"                                        >Đức phát thắp sáng ước mơ</span" +
				"                                      >" +
				"                                    </p>" +
				"                                  </div>" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 10px 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <p" +
				"                                    style=\"" +
				"                                      border-top: solid 1px #d9d9d9;" +
				"                                      font-size: 1px;" +
				"                                      margin: 0px auto;" +
				"                                      width: 100%;" +
				"                                    \"" +
				"                                  ></p>" +
				"                                  <!--[if mso | IE" +
				"                                    ]><table" +
				"                                      align=\"center\"" +
				"                                      border=\"0\"" +
				"                                      cellpadding=\"0\"" +
				"                                      cellspacing=\"0\"" +
				"                                      style=\"" +
				"                                        border-top: solid 1px #d9d9d9;" +
				"                                        font-size: 1px;" +
				"                                        margin: 0px auto;" +
				"                                        width: 550px;" +
				"                                      \"" +
				"                                      role=\"presentation\"" +
				"                                      width=\"550px\"" +
				"                                    >" +
				"                                      <tr>" +
				"                                        <td style=\"height: 0; line-height: 0\">" +
				"                                          &nbsp;" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </table><!" +
				"                                  [endif]-->" +
				"                                </td>" +
				"                              </tr>" +
				"                            </tbody>" +
				"                          </table>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"              </td>" +
				"            </tr>" +
				"          </tbody>" +
				"        </table>" +
				"      </div>" +
				"      <!--[if mso | IE]></td></tr></table><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"\" role=\"presentation\" style=\"width:600px;\" width=\"600\" bgcolor=\"#FFFFFF\" ><tr><td style=\"line-height:0px;font-size:0px;mso-line-height-rule:exactly;\"><![endif]-->" +
				"      <div" +
				"        style=\"" +
				"          background: #ffffff;" +
				"          background-color: #ffffff;" +
				"          margin: 0px auto;" +
				"          max-width: 600px;" +
				"        \"" +
				"      >" +
				"        <table" +
				"          align=\"center\"" +
				"          border=\"0\"" +
				"          cellpadding=\"0\"" +
				"          cellspacing=\"0\"" +
				"          role=\"presentation\"" +
				"          style=\"background: #ffffff; background-color: #ffffff; width: 100%\"" +
				"        >" +
				"          <tbody>" +
				"            <tr>" +
				"              <td" +
				"                style=\"" +
				"                  direction: ltr;" +
				"                  font-size: 0px;" +
				"                  padding: 0px 0 0px 0;" +
				"                  padding-bottom: 0px;" +
				"                  padding-top: 0px;" +
				"                  text-align: center;" +
				"                \"" +
				"              >" +
				"                <!--[if mso | IE]><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"\" style=\"vertical-align:middle;width:600px;\" ><![endif]-->" +
				"                <div" +
				"                  class=\"mj-column-per-100 mj-outlook-group-fix\"" +
				"                  style=\"" +
				"                    font-size: 0px;" +
				"                    text-align: left;" +
				"                    direction: ltr;" +
				"                    display: inline-block;" +
				"                    vertical-align: middle;" +
				"                    width: 100%;" +
				"                  \"" +
				"                >" +
				"                  <table" +
				"                    border=\"0\"" +
				"                    cellpadding=\"0\"" +
				"                    cellspacing=\"0\"" +
				"                    role=\"presentation\"" +
				"                    width=\"100%\"" +
				"                  >" +
				"                    <tbody>" +
				"                      <tr>" +
				"                        <td style=\"vertical-align: middle; padding: 0 0 0 0\">" +
				"                          <table" +
				"                            border=\"0\"" +
				"                            cellpadding=\"0\"" +
				"                            cellspacing=\"0\"" +
				"                            role=\"presentation\"" +
				"                            width=\"100%\"" +
				"                          >" +
				"                            <tbody>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"center\"" +
				"                                  style=\"" +
				"                                    font-size: 0px;" +
				"                                    padding: 10px 25px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <!--[if mso | IE]><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" ><tr><td><![endif]-->" +
				"                                  <table" +
				"                                    align=\"center\"" +
				"                                    border=\"0\"" +
				"                                    cellpadding=\"0\"" +
				"                                    cellspacing=\"0\"" +
				"                                    role=\"presentation\"" +
				"                                    style=\"float: none; display: inline-table\"" +
				"                                  >" +
				"                                    <tbody>" +
				"                                      <tr>" +
				"                                        <td" +
				"                                          style=\"" +
				"                                            padding: 4px;" +
				"                                            vertical-align: middle;" +
				"                                          \"" +
				"                                        >" +
				"                                          <table" +
				"                                            border=\"0\"" +
				"                                            cellpadding=\"0\"" +
				"                                            cellspacing=\"0\"" +
				"                                            role=\"presentation\"" +
				"                                            style=\"" +
				"                                              background: #3b5998;" +
				"                                              border-radius: 3px;" +
				"                                              width: 20px;" +
				"                                            \"" +
				"                                          >" +
				"                                            <tbody>" +
				"                                              <tr>" +
				"                                                <td" +
				"                                                  style=\"" +
				"                                                    font-size: 0;" +
				"                                                    height: 20px;" +
				"                                                    vertical-align: middle;" +
				"                                                    width: 20px;" +
				"                                                  \"" +
				"                                                >" +
				"                                                  <img" +
				"                                                    height=\"20\"" +
				"                                                    src=\"https://www.mailjet.com/images/theme/v1/icons/ico-social/facebook.png\"" +
				"                                                    style=\"" +
				"                                                      border-radius: 3px;" +
				"                                                      display: block;" +
				"                                                    \"" +
				"                                                    width=\"20\"" +
				"                                                  />" +
				"                                                </td>" +
				"                                              </tr>" +
				"                                            </tbody>" +
				"                                          </table>" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </tbody>" +
				"                                  </table>" +
				"                                  <!--[if mso | IE]></td><td><![endif]-->" +
				"                                  <table" +
				"                                    align=\"center\"" +
				"                                    border=\"0\"" +
				"                                    cellpadding=\"0\"" +
				"                                    cellspacing=\"0\"" +
				"                                    role=\"presentation\"" +
				"                                    style=\"float: none; display: inline-table\"" +
				"                                  >" +
				"                                    <tbody>" +
				"                                      <tr>" +
				"                                        <td" +
				"                                          style=\"" +
				"                                            padding: 4px;" +
				"                                            vertical-align: middle;" +
				"                                          \"" +
				"                                        >" +
				"                                          <table" +
				"                                            border=\"0\"" +
				"                                            cellpadding=\"0\"" +
				"                                            cellspacing=\"0\"" +
				"                                            role=\"presentation\"" +
				"                                            style=\"" +
				"                                              background: #405de6;" +
				"                                              border-radius: 3px;" +
				"                                              width: 20px;" +
				"                                            \"" +
				"                                          >" +
				"                                            <tbody>" +
				"                                              <tr>" +
				"                                                <td" +
				"                                                  style=\"" +
				"                                                    font-size: 0;" +
				"                                                    height: 20px;" +
				"                                                    vertical-align: middle;" +
				"                                                    width: 20px;" +
				"                                                  \"" +
				"                                                >" +
				"                                                  <img" +
				"                                                    height=\"20\"" +
				"                                                    src=\"https://www.mailjet.com/images/theme/v1/icons/ico-social/instagram.png\"" +
				"                                                    style=\"" +
				"                                                      border-radius: 3px;" +
				"                                                      display: block;" +
				"                                                    \"" +
				"                                                    width=\"20\"" +
				"                                                  />" +
				"                                                </td>" +
				"                                              </tr>" +
				"                                            </tbody>" +
				"                                          </table>" +
				"                                        </td>" +
				"                                      </tr>" +
				"                                    </tbody>" +
				"                                  </table>" +
				"                                  <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"                                </td>" +
				"                              </tr>" +
				"                              <tr>" +
				"                                <td" +
				"                                  align=\"left\"" +
				"                                  style=\"" +
				"                                    background: #ffffff;" +
				"                                    font-size: 0px;" +
				"                                    padding: 0 0 0 0;" +
				"                                    padding-top: 0px;" +
				"                                    padding-bottom: 0px;" +
				"                                    word-break: break-word;" +
				"                                  \"" +
				"                                >" +
				"                                  <div" +
				"                                    style=\"" +
				"                                      font-family: Arial, sans-serif;" +
				"                                      font-size: 13px;" +
				"                                      letter-spacing: normal;" +
				"                                      line-height: 1;" +
				"                                      text-align: left;" +
				"                                      color: #000000;" +
				"                                    \"" +
				"                                  >" +
				"                                    <p" +
				"                                      class=\"text-build-content\"" +
				"                                      style=\"" +
				"                                        text-align: center;" +
				"                                        margin: 10px 0;" +
				"                                        margin-top: 10px;" +
				"                                        margin-bottom: 10px;" +
				"                                      \"" +
				"                                      data-testid=\"C71AE0cbRbPP\"" +
				"                                    >" +
				"                                      <span" +
				"                                        style=\"" +
				"                                          color: #4aabf7;" +
				"                                          font-family: Arial;" +
				"                                          font-size: 14px;" +
				"                                          line-height: 22px;" +
				"                                        \"" +
				"                                        >&nbsp; &nbsp; ducphat.com" +
				"                                        &nbsp;&nbsp;</span" +
				"                                      >" +
				"                                    </p>" +
				"                                  </div>" +
				"                                </td>" +
				"                              </tr>" +
				"                            </tbody>" +
				"                          </table>" +
				"                        </td>" +
				"                      </tr>" +
				"                    </tbody>" +
				"                  </table>" +
				"                </div>" +
				"                <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"              </td>" +
				"            </tr>" +
				"          </tbody>" +
				"        </table>" +
				"      </div>" +
				"      <!--[if mso | IE]></td></tr></table><![endif]-->" +
				"    </div>" +
				"  </body>" +
				"</html>";
				
		
		Mail mail = new Mail(new Email("dinhthanhson21122000@gmail.com"), emailrequest.getTieuDe(), new Email(emailrequest.getToEmail()),new Content("text/html", sb));
		mail.setReplyTo(new Email("abc@gmail.com"));
		Request request = new Request();

		Response response = null;

		try {

			request.setMethod(Method.POST);

			request.setEndpoint("mail/send");

			request.setBody(mail.build());

			response=this.sendGrid.api(request);

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		}

		return response;
		
		
	}

	public Response sendMailResetPassword(ForgotPasswordRequest forgotPasswordRequest) 
	{
		String content = "<!doctype html>" +
		"<html lang=\"en-US\">" +
		"<head>" +
		"    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />" +
		"    <title>Reset Password Email Template</title>" +
		"    <meta name=\"description\" content=\"Reset Password Email Template.\">" +
		"    <style type=\"text/css\">" +
		"        a:hover {text-decoration: underline !important;}" +
		"    </style>" +
		"</head>" +
		"<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">" +
		"    <!--100% body table-->" +
		"    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"" +
		"        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">" +
		"        <tr>" +
		"            <td>" +
		"                <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"" +
		"                    align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
		"                    <tr>" +
		"                        <td style=\"height:80px;\">&nbsp;</td>" +
		"                    </tr>" +
		"                    " +
		"                    <tr>" +
		"                        <td style=\"height:20px;\">&nbsp;</td>" +
		"                    </tr>" +
		"                    <tr>" +
		"                        <td>" +
		"                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"" +
		"                                style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">" +
		"                                <tr>" +
		"                                    <td style=\"height:40px;\">&nbsp;</td>" +
		"                                </tr>" +
		"								<tr>" +
		"                        <td style=\"text-align:center;\">" +
		"                          <a href=\"#\" title=\"logo\" target=\"_blank\">" +
		"                            <img width=\"30%\" src=\"https://012qg.mjt.lu/tplimg/012qg/b/10li3/utx.png\" title=\"logo\" alt=\"logo\">" +
		"                          </a>" +
		"                        </td>" +
		"                    </tr>" +
		"                                <tr>" +
		"                                    <td style=\"padding:0 35px;\">" +
		"											<h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">Bạn đã yêu cầu đặt lại mật khẩu của mình</h1>" +
		"                                        <span" +
		"                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>" +
		"                                        <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">" +
		"											Chúng tôi không thể chỉ gửi cho bạn mật khẩu cũ của bạn. Một liên kết duy nhất để đặt lại mật khẩu của bạn đã được tạo cho bạn. Để đặt lại mật khẩu của bạn, hãy nhấp vào liên kết sau và làm theo hướng dẫn." +
		"                                        </p>" +
		"                                        <a href=\""+forgotPasswordRequest.getReponse()+"\"" +
		"                                            style=\"background:#2A6395;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">Đặt lại mật khẩu" +
		"                                            </a>" +
		"                                    </td>" +
		"                                </tr>" +
		"                                <tr>" +
		"                                    <td style=\"height:40px;\">&nbsp;</td>" +
		"                                </tr>" +
		"                            </table>" +
		"                        </td>" +
		"                    <tr>" +
		"                        <td style=\"height:20px;\">&nbsp;</td>" +
		"                    </tr>" +
		"                    <tr>" +
		"                        <td style=\"text-align:center;\">" +
		"                            <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>www.ducphatelectric.com</strong></p>" +
		"                        </td>" +
		"                    </tr>" +
		"                    <tr>" +
		"                        <td style=\"height:80px;\">&nbsp;</td>" +
		"                    </tr>" +
		"                </table>" +
		"            </td>" +
		"        </tr>" +
		"    </table>" +
		"    <!--/100% body table-->" +
		"</body>" +
		"</html>";
		
				
		
		Mail mail = new Mail(new Email("dinhthanhson21122000@gmail.com"), forgotPasswordRequest.getSubject(), new Email(forgotPasswordRequest.getEmail()),new Content("text/html", content));
		mail.setReplyTo(new Email("abc@gmail.com"));
		Request request = new Request();

		Response response = null;

		try {

			request.setMethod(Method.POST);

			request.setEndpoint("mail/send");

			request.setBody(mail.build());

			response=this.sendGrid.api(request);

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		}

		return response;
		
		
	}
}