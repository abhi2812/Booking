package models;

public interface UpiInterface {
    PaymentStatus makePayment(double amount, String vpaID);
}
