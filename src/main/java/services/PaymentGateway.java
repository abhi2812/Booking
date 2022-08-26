package services;

import lombok.AllArgsConstructor;
import models.*;

import java.util.*;

public class PaymentGateway implements PaymentGatewayInterface {

    Map<UUID, PaymentMethodsSupported> clientPaymentMethodsSupportedMapping;
    Map<UUID, PaymentGatewayClient> uuidPaymentGatewayClientMap;
    PaymentMethodsSupported paymentMethodsSupportedGateway;

    public PaymentGateway() {
        this.clientPaymentMethodsSupportedMapping = new HashMap<>();
        this.uuidPaymentGatewayClientMap = new HashMap<>();
        this.paymentMethodsSupportedGateway = PaymentMethodsSupported.builder().
                netBankingSupported(true).creditCardSupported(true).upiSupported(true)
                .debitCardSupported(true).build();
    }

    @Override
    public OnboardingStatus onBoardClient(PaymentMethodsSupported paymentMethodsSupported,
                                          PaymentGatewayClient paymentGatewayClient) {
        UUID clientId = paymentGatewayClient.getClientID();
        if (Objects.nonNull(clientPaymentMethodsSupportedMapping.get(clientId))) {
            return OnboardingStatus.ALREADY_ONBOARDED;
        }
        clientPaymentMethodsSupportedMapping.put(clientId, paymentMethodsSupported);
        uuidPaymentGatewayClientMap.put(clientId, paymentGatewayClient);
        return OnboardingStatus.SUCCESS;
    }

    @Override
    public boolean removeClient(UUID clientId) {
        if(Objects.isNull(clientPaymentMethodsSupportedMapping.get(clientId))) {
            clientPaymentMethodsSupportedMapping.remove(clientId);
            uuidPaymentGatewayClientMap.remove(clientId);
            System.out.println("Client removed");
            return true;
        }
        System.out.println("Client not found");
        return false;
    }

    @Override
    public boolean hasClient() {
        return clientPaymentMethodsSupportedMapping.size() > 0;
    }

    @Override
    public List<PaymentMethods> listSupportedPayModes() {
        List<PaymentMethods> listSupportedPayModes =
                getPayModes(paymentMethodsSupportedGateway);
        for (PaymentMethods payMode: listSupportedPayModes) {
            System.out.println(payMode.toString());
        }
        return listSupportedPayModes;
    }

    private List<PaymentMethods> getPayModes(PaymentMethodsSupported paymentMethodsSupported) {
        List<PaymentMethods> paymentMethodsList = new ArrayList<>();
        if(paymentMethodsSupported.isCreditCardSupported() && paymentMethodsSupportedGateway.isCreditCardSupported()) {
            paymentMethodsList.add(PaymentMethods.CREDIT_CARD);
        }
        if(paymentMethodsSupported.isDebitCardSupported() && paymentMethodsSupportedGateway.isDebitCardSupported()) {
            paymentMethodsList.add(PaymentMethods.DEBIT_CARD);
        }
        if(paymentMethodsSupported.isUpiSupported() && paymentMethodsSupportedGateway.isUpiSupported()) {
            paymentMethodsList.add(PaymentMethods.UPI);
        }
        if(paymentMethodsSupported.isNetBankingSupported() && paymentMethodsSupportedGateway.isNetBankingSupported()) {
            paymentMethodsList.add(PaymentMethods.NET_BANKING);
        }
        return paymentMethodsList;
    }

    @Override
    public List<PaymentMethods> listSupportedPayModes(UUID clientId) {
        List<PaymentMethods> listSupportedPayModes =
                getPayModes(clientPaymentMethodsSupportedMapping.get(clientId));
        for (PaymentMethods payMode: listSupportedPayModes) {
            System.out.println(payMode.toString());
        }
        return listSupportedPayModes;
    }

    @Override
    public void removePayMode(PaymentMethods paymentMethod) {
        switch (paymentMethod) {
            case UPI:
                paymentMethodsSupportedGateway = paymentMethodsSupportedGateway.toBuilder().upiSupported(false).build();
            case DEBIT_CARD:
                paymentMethodsSupportedGateway = paymentMethodsSupportedGateway.toBuilder().debitCardSupported(false).build();
            case CREDIT_CARD:
                paymentMethodsSupportedGateway = paymentMethodsSupportedGateway.toBuilder().creditCardSupported(false).build();
            case NET_BANKING:
                paymentMethodsSupportedGateway = paymentMethodsSupportedGateway.toBuilder().netBankingSupported(false).build();
        }
    }

    @Override
    public List<Pair<String, String>> showDistribution() {
        return null;
    }

    @Override
    public PaymentStatus makePayment(ClientPaymentDetails clientPaymentDetails, PaymentMethods paymentMethod, double amount) {
        switch (paymentMethod) {
            case UPI:
                UpiInterface upiInterface = new PayTmUpi();
                return upiInterface.makePayment(amount, clientPaymentDetails.getVpaId());
            case DEBIT_CARD:
                BankCardSystemInterface cardSystemInterface = new IciciDebitCard();
                return cardSystemInterface.makePayment(clientPaymentDetails.getNetBankingUserId(), clientPaymentDetails.getCardDetail(), amount);
            case CREDIT_CARD:
                BankCardSystemInterface hdfcCreditCard = new HdfcCreditCard();
                return hdfcCreditCard.makePayment(clientPaymentDetails.getNetBankingUserId(), clientPaymentDetails.getCardDetail(), amount);
            case NET_BANKING:
               return PaymentStatus.FAILURE;
        }
        return PaymentStatus.FAILURE;
    }
}
