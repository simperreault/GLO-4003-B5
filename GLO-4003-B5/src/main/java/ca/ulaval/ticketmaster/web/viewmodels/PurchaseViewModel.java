package ca.ulaval.ticketmaster.web.viewmodels;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckEmail;

public class PurchaseViewModel {
	@NotEmpty(message = "Veuillez indiquer un prénom")
	private String firstName;
	@NotEmpty(message = "Veuillez indiquer un nom de famille")
	private String lastName;
	@NotEmpty(message = "Veuillez indiquer une adresse courriel")
	@CheckEmail(message = "L'adresse courriel fournie est invalide")
	private String email;
	@NotEmpty(message = "Veuillez indiquer une adresse")
	private String adress;
	@NotEmpty(message = "Veuillez indiquer un type de paiement")
	private PaymentType paymentType;
	@NotEmpty(message = "Veuillez indiquer un numéro de carte de crédit")
	private String cardNumber;
	@NotEmpty(message = "Veuillez indiquer le numéro de vérification")
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
