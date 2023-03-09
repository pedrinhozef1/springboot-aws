package br.com.projetospringboot.invoice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Iterable<Invoice> findAll(){
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findByNumber(String number){
        return invoiceRepository.findByNumber(number);
    }

    public Iterable<Invoice> findAllByCustomerName(String customerName){
        return invoiceRepository.findAllByCustomerName(customerName);
    }

}
