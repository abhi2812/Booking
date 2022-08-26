package models;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Builder(toBuilder = true)
@Value
public class PaymentMethodsSupported {
    boolean upiSupported;
    boolean creditCardSupported;
    boolean debitCardSupported;
    boolean netBankingSupported;
}
