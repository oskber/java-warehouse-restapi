package entities;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record Product(
        @NotNull String id,
        @NotEmpty(message = "Empty names not allowed") String name,
        @NotNull Category category,
        @Min(value = 0, message = "Rating should be between 1-5") @Max(value = 5, message = "Rating should be between 1-5") int rating,
        @PastOrPresent LocalDate createdDate,
        @PastOrPresent LocalDate modifiedDate
) {
    public Product {
        if (modifiedDate == null) {
            modifiedDate = createdDate;
        }
    }
}

