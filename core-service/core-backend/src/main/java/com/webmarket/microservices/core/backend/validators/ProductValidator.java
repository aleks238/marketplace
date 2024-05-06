package com.webmarket.microservices.core.backend.validators;

import com.webmarket.microservices.core.api.ProductDto;
import com.webmarket.microservices.core.backend.exceptionHandling.Exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto){
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice() < 1){
            errors.add("Цена продукта не может быть меньше 1");
        }
        if (productDto.getTitle().isBlank()){
            errors.add("Продукт не может быть сохранен без названия");
        }
        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }

    }
}
