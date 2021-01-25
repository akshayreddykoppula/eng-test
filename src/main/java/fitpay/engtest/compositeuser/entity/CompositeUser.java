package fitpay.engtest.compositeuser.entity;

import java.util.List;

public class CompositeUser {
    private String userId;
    private List<CreditCard> creditCardIds;
    private List<Device> deviceIds;

    public CompositeUser() {
    }

    public CompositeUser(String userId, List<CreditCard> creditCardIds, List<Device> deviceIds) {
        super();
        this.userId = userId;
        this.creditCardIds = creditCardIds;
        this.deviceIds = deviceIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CreditCard> getCreditCardIds() {
        return creditCardIds;
    }

    public void setCreditCardIds(List<CreditCard> creditCardIds) {
        this.creditCardIds = creditCardIds;
    }

    public List<Device> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<Device> deviceIds) {
        this.deviceIds = deviceIds;
    }

    @Override
    public String toString() {
        return "CompositeUser [userId=" + userId + ", creditCardIds=" + creditCardIds + ", deviceIds=" + deviceIds
                + "]";
    }
}
