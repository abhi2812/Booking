import models.*;
import services.PaymentGateway;

import java.util.List;
import java.util.UUID;

public class PaymentGatewayService {
    public static void main(String[] args) {
        System.out.println("this is the starting point");
        PaymentGatewayInterface paymentGatewayImpl = new PaymentGateway();
        PaymentGatewayClient paymentGatewayClient1 = PaymentGatewayClient.builder()
                .clientID(UUID.randomUUID())
                .clientName("Client1")
                .build();
        PaymentMethodsSupported client1 = PaymentMethodsSupported.builder().upiSupported(true)
                .creditCardSupported(true).build();
        PaymentGatewayClient paymentGatewayClient2 = PaymentGatewayClient.builder()
                .clientID(UUID.randomUUID())
                .clientName("Client2")
                .build();
        PaymentMethodsSupported client2 = PaymentMethodsSupported.builder().netBankingSupported(true)
                .debitCardSupported(true).build();
        paymentGatewayImpl.onBoardClient(client1, paymentGatewayClient1);
        List<PaymentMethods> listSupportedPayModes = paymentGatewayImpl.listSupportedPayModes(paymentGatewayClient1.getClientID());
        paymentGatewayImpl.onBoardClient(client2, paymentGatewayClient2);
        List<PaymentMethods> listSupportedPayModesClient1 = paymentGatewayImpl.listSupportedPayModes(paymentGatewayClient2.getClientID());
        ClientPaymentDetails clientPaymentDetailsClient1 = ClientPaymentDetails.builder().netBankingUserId("Abc").build();
        paymentGatewayImpl.makePayment(clientPaymentDetailsClient1, PaymentMethods.UPI,100.1);
    }
}
