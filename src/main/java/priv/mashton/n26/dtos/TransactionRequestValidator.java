package priv.mashton.n26.dtos;

import java.time.DateTimeException;
import java.time.Instant;

public final class TransactionRequestValidator {

    public static void validate(TransactionRequest request) {
        if (request == null || request.getAmount() == null || request.getTimestamp() == null) {
            throw new IllegalArgumentException();
        }

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException();
        }

        try {
            Instant.ofEpochMilli(request.getTimestamp());
        } catch (DateTimeException ex) {
            throw new IllegalArgumentException();
        }
    }
}
