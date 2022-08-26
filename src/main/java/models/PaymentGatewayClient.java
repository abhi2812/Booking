package models;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class PaymentGatewayClient {
    String clientName;
    UUID clientID;
}
