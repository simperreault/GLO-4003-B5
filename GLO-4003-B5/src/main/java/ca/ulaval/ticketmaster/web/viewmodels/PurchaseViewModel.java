package ca.ulaval.ticketmaster.web.viewmodels;

import ca.ulaval.ticketmaster.model.enums.PaymentType;

public class PurchaseViewModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String adress;
	private PaymentType paymentType;
	private String cardNumber;
	private String verificationNumber;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getVerificationNumber() {
		return verificationNumber;
	}
	public void setVerificationNumber(String verificationNumber) {
		this.verificationNumber = verificationNumber;
	}
	
}
