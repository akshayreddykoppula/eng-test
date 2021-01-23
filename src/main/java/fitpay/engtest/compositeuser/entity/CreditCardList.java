package fitpay.engtest.compositeuser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardList {

	@JsonProperty("results")
	private List<CreditCard> creditCards;

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
