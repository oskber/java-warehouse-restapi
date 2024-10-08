package entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private final Validator validator;

    public ProductTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    public void productWithEmptyNameShouldThrowException() {

            Product product = new Product("1", "", Category.ELECTRONICS, 5, java.time.LocalDate.now(), java.time.LocalDate.now());
            Set<ConstraintViolation<Product>> violations = validator.validate(product);
            Set<String> messages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> violation.getMessage().equals("Empty names not allowed"));
    }

    @Test
    public void productWithValidDataShouldPassValidation() {
        Product product = new Product("1", "Laptop", Category.ELECTRONICS, 4, java.time.LocalDate.now(), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isEmpty();
    }

    @Test
    public void productWithNullIdShouldThrowException() {
        Product product = new Product(null, "Laptop", Category.ELECTRONICS, 4, java.time.LocalDate.now(), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("must not be null"));
    }

    @Test
    public void productWithNullCategoryShouldThrowException() {
        Product product = new Product("1", "Laptop", null, 4, java.time.LocalDate.now(), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("must not be null"));
    }

    @Test
    public void productWithNegativeRatingShouldThrowException() {
        Product product = new Product("1", "Laptop", Category.ELECTRONICS, -1, java.time.LocalDate.now(), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Rating should be between 1-5"));
    }

    @Test
    public void productWithRatingAboveFiveShouldThrowException() {
        Product product = new Product("1", "Laptop", Category.ELECTRONICS, 6, java.time.LocalDate.now(), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Rating should be between 1-5"));
    }

    @Test
    public void productWithFutureCreatedDateShouldThrowException() {
        Product product = new Product("1", "Laptop", Category.ELECTRONICS, 4, java.time.LocalDate.now().plusDays(1), java.time.LocalDate.now());
        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("must be a date in the past or in the present"));
    }
}

