package com.example.datn.payload.request;

import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank
	private String email;

    private String reponse;

    private String subject;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    

    
    
}
