package br.com.projetospringboot.invoice;

import br.com.projetospringboot.model.UrlResponse;
import com.amazonaws.HttpMethod;
import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@AllArgsConstructor
public class InvoiceController {
//    @Value("${aws.s3.bucket.invoice.name}")
    @Value("aaa")
    private final String bucketS3Name = "TESTE";

    private AmazonS3 amazonS3;

    private final InvoiceService invoiceService;

    @PostMapping("/url")
    public ResponseEntity<UrlResponse> createInvoiceUrl() {
        String uuid = UUID.randomUUID().toString();
        Instant expirationTime = Instant.now().plus(Duration.ofMinutes(5));

        GeneratePresignedUrlRequest preAssignedUrl = new GeneratePresignedUrlRequest(
                bucketS3Name, uuid)
                .withMethod(HttpMethod.PUT)
                .withExpiration(Date.from(expirationTime));

        UrlResponse urlResponse = UrlResponse.builder()
                .url(amazonS3.generatePresignedUrl(preAssignedUrl).toString())
                .expirationTime(expirationTime.getEpochSecond())
                .build();

        return ResponseEntity.ok().body(urlResponse);

    }

    @GetMapping
    public ResponseEntity<Iterable<Invoice>> getAll(){
        return ResponseEntity.ok().body(invoiceService.findAll());
    }

    @GetMapping("/{number}")
    public ResponseEntity<Optional<Invoice>> getByNumber(@PathVariable String number){
        return ResponseEntity.ok().body(invoiceService.findByNumber(number));
    }

    @GetMapping("/customer")
    public ResponseEntity<Iterable<Invoice>> getByCustomerName(@RequestParam String customerName){
        return ResponseEntity.ok().body(invoiceService.findAllByCustomerName(customerName));
    }
}
