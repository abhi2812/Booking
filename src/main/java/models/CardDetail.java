package models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CardDetail {
    String nameOnCard;
    String cardNumber;
    String cvv;
}
