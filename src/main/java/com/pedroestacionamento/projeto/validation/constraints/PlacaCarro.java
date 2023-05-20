package com.pedroestacionamento.projeto.validation.constraints;

import com.pedroestacionamento.projeto.validation.PlacaCarroValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlacaCarroValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacaCarro {

    String message() default "Placa do carro inválida!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
