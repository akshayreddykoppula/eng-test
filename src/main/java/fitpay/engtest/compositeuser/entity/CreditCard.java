package fitpay.engtest.compositeuser.entity;

public class CreditCard {

	private String creditCardId;
	private String state;
	
	public String getCreditCardId() {
		return creditCardId;
	}
	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "CreditCard [creditCardId=" + creditCardId + ", state=" + state + "]";
	}
}
