package com.zerobase.convpay.config;

import com.zerobase.convpay.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class ApplicationConfig {

    @Bean
    public ConveniencePayService conveniencePayService(){
        return new ConveniencePayService(
                new HashSet<>(
                        Arrays.asList(MoneyAdapter(), cardAdapter())
                ),
                disountByConvenience()
        );
    }
    @Bean
    public CardAdapter cardAdapter(){
        return new CardAdapter();
    }

    @Bean
    private static MoneyAdapter MoneyAdapter() {
        return new MoneyAdapter();
    }

    @Bean
    private static DiscountByConvenience disountByConvenience() {
        return new DiscountByConvenience();
    }
}
