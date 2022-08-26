package models;

public interface BankCardSystemInterface {
    PaymentStatus makePayment(String userId, CardDetail cardDetail, double amount);
}
