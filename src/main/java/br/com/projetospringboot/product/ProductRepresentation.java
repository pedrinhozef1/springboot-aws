package br.com.projetospringboot.product;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface ProductRepresentation {
    @Getter
    @Setter
    @Data
    class CreateOrUpdate{
        @NotNull(message = "Name of product can be not null!")
        @NotEmpty(message = "Name of product can be not empty")
        private String name;

        @NotNull(message = "Model of product can be not null!")
        @NotEmpty(message = "Model of product can be not empty")
        private String model;

        @NotNull(message = "Code of product can be not null!")
        @NotEmpty(message = "Code of product can be not empty")
        private String code;

        @NotNull(message = "Price of product can be not null!")
        private Float price;
    }

    @Builder
    @Getter
    @Setter
    @Data
    class Details{
        private Long id;
        private String name;
        private String model;
        private String code;
        private Float price;

        public static Details from (Product product){
            Details detailsProduct = Details.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .model(product.getModel())
                    .code(product.getCode())
                    .price(product.getPrice())
                    .build();

            return detailsProduct;
        }
    }
}
