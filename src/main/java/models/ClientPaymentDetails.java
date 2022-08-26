package models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ClientPaymentDetails {
    CardDetail cardDetail;
    String netBankingUserId;
    String netBankingPassword;
    String vpaId;
}
