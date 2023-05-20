package com.pedroestacionamento.projeto.validation.constraints;

import com.pedroestacionamento.projeto.validation.CpfValidation;
import com.pedroestacionamento.projeto.validation.TelefoneValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {

    String message() default "CPF informado não é válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
