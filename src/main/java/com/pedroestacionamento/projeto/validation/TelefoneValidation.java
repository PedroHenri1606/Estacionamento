package com.pedroestacionamento.projeto.validation;

import com.pedroestacionamento.projeto.validation.constraints.Telefone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidation implements ConstraintValidator<Telefone,String> {

    @Override
    public void initialize(Telefone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (value.equals("00000000000") || value.equals("11111111111") || value.equals("22222222222") || value.equals("33333333333") ||
                value.equals("44444444444") || value.equals("55555555555") || value.equals("66666666666") || value.equals("77777777777") ||
                value.equals("88888888888") || value.equals("99999999999") || (value.length() != 11))
            return(false);

        value = value.replaceAll("[0-9]{2}[ ][0-9]{5}[0-9]{4}"," ");

        if(value.length() !=11){
            return false;
        } else {
            return true;
        }
    }
}
