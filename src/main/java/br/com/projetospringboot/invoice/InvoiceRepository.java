package br.com.projetospringboot.invoice;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
    Optional<Invoice> findByNumber(String number);

    List<Invoice> findAllByCustomerName(String customerName);
}
