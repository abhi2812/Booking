package models;

import java.util.Random;

public class IciciDebitCard implements BankCardSystemInterface{
    @Override
    public PaymentStatus makePayment(String userId, CardDetail cardDetail, double amount) {
        Random rand = new Random();
        int randInt = rand.nextInt(10);
        return randInt < 8 ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE;
    }
}
