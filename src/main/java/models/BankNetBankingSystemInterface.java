package models;

public interface BankNetBankingSystemInterface {
    PaymentStatus makePayment(double amount, String userId, String password);
}
