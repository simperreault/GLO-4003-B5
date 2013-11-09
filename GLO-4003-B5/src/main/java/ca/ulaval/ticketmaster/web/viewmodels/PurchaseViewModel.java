package ca.ulaval.ticketmaster.web.viewmodels;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckEmail;

public class PurchaseViewModel {
	@NotEmpty(message = "Veuillez indiquer un prénom")
	public String firstName;
	@NotEmpty(message = "Veuillez indiquer un nom de famille")
	public String lastName;
	@NotEmpty(message = "Veuillez indiquer une adresse courriel")
	@CheckEmail(message = "L'adresse courriel fournie est invalide")
	public String email;
	@NotEmpty(message = "Veuillez indiquer une adresse")
	public String adress;
	@NotEmpty(message = "Veuillez indiquer un type de paiement")
	public PaymentType paymentType;
	@NotEmpty(message = "Veuillez indiquer un numéro de carte de crédit")
	public String cardNumber;
	@NotEmpty(message = "Veuillez indiquer le numéro de vérification")
	public String verificationNumber;

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
