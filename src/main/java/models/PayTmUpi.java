package models;

import java.util.Random;

public class PayTmUpi implements UpiInterface {

    @Override
    public PaymentStatus makePayment(double amount, String vpaID) {
        Random rand = new Random();
        int randInt = rand.nextInt(10);
        return randInt < 7 ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE;
    }
}
