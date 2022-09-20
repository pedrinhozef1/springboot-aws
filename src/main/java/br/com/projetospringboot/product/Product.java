package br.com.projetospringboot.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name of product can be not null!")
    private String name;

    @Column(name = "model")
    @NotNull(message = "Model of product can be not null!")
    private String model;

    @Column(name = "code")
    @NotNull(message = "Code of product can be not null!")
    private String code;

    @Column(name = "price")
    @NotNull(message = "Price of product can be not null!")
    private Float price;
}
