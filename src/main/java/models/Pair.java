package models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Pair<T, U> {
    T first;
    U second;
}
