package com.copterbuddy.prototype.backend.exeption;

public class ProductException extends BaseException {

    public ProductException(String code) {
        super("product." + code);
    }

    public static ProductException notFound() {
        return new ProductException("not found");
    }
}
