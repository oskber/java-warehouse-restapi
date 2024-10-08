package entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    public void productWithEmptyNameShouldThrowException() {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            Product product = new Product("1", "", Category.ELECTRONICS, 5, java.time.LocalDate.now(), java.time.LocalDate.now());
            Set<ConstraintViolation<Product>> violations = validator.validate(product);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> violation.getMessage().equals("Empty names not allowed"));
        }
    }
}