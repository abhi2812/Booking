package models;

import java.util.List;
import java.util.UUID;

public interface PaymentGatewayInterface {

    OnboardingStatus onBoardClient(PaymentMethodsSupported paymentMethodsSupported, PaymentGatewayClient paymentGatewayClient);
    boolean removeClient(UUID clientId);
    boolean hasClient();
    List<PaymentMethods> listSupportedPayModes();
    List<PaymentMethods> listSupportedPayModes(UUID clientId);
    void removePayMode(PaymentMethods paymentMethods);
    List<Pair<String ,String> > showDistribution();
    PaymentStatus makePayment(ClientPaymentDetails clientPaymentDetails, PaymentMethods paymentMethods, double amount);
}
