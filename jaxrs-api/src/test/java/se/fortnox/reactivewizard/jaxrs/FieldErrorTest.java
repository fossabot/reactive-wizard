package se.fortnox.reactivewizard.jaxrs;

import org.fest.assertions.MapAssert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class FieldErrorTest {

    @Test
    public void shouldPrefixErrorWithValidationWhenMissing() {
        FieldError fieldError = new FieldError("username", "too.short");
        assertThat(fieldError.getError()).isEqualTo("validation.too.short");
        assertThat(fieldError.getField()).isEqualTo("username");
    }

    @Test
    public void shouldNotPrefixErrorWithValidationWhenAlreadyPresent() {
        FieldError fieldError = new FieldError("username", "validation.too.short");
        assertThat(fieldError.getError()).isEqualTo("validation.too.short");
        assertThat(fieldError.getField()).isEqualTo("username");
    }

    @Test
    public void shouldInitializeNotNullError() {
        FieldError fieldError = FieldError.notNull("username");
        assertThat(fieldError.getError()).isEqualTo("validation.notnull");
        assertThat(fieldError.getField()).isEqualTo("username");
    }

    @Test
    public void shouldIncludeErrorParams() {
        Map<String, Object> errorParams = new HashMap<>();
        errorParams.put("name", "a");

        FieldError fieldError = new FieldError("username", "validation.too.short", errorParams);
        assertThat(fieldError.getError()).isEqualTo("validation.too.short");
        assertThat(fieldError.getField()).isEqualTo("username");
        assertThat(fieldError.getErrorParams()).includes(MapAssert.entry("name", "a"));
    }

    @Test
    public void shouldNotInitializeValues() {
        FieldError fieldError = new FieldError();
        assertThat(fieldError.getError()).isNull();
        assertThat(fieldError.getField()).isNull();
        assertThat(fieldError.getErrorParams()).isNull();
    }
}
