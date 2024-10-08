package entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record Product(@NotNull String id, @NotNull String name, @NotNull Category category, @Min(value = 0, message = "Rating should be between 1-5") @Max(value = 5, message = "Rating should be between 1-5") int rating, @PastOrPresent LocalDate createdDate,
                     @PastOrPresent LocalDate modifiedDate) {
    public Product {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (createdDate == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }
        if (modifiedDate == null) {
            modifiedDate = createdDate;
        }
    }
}

