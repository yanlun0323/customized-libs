package com.customized.libs.core.libs.optional;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        Person person = new Person();

        String brand = null;
        if (null != person.getCar()) {
            if (null != person.getCar().getWheel()) {
                brand = person.getCar().getWheel().getBrand();
            }
        }
        System.out.println(StringUtils.defaultString(brand, "N/A"));


        // Optional Case
        brand = Optional.of(person)
                .map(Person::getCar)
                .map(Car::getWheel)
                .map(Wheel::getBrand)
                .orElse("default");
        System.out.println(brand);
    }
}
