package study.springsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import study.springsecurity.entity.enums.Currency;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
