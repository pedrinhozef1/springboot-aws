package br.com.projetospringboot.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 32, nullable = false)
    private String number;

    @Column(name = "customer_name", length = 32, nullable = false)
    private String customerName;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "product_id")
    private long productId;

    private int quantity;
}
