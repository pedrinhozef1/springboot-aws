package br.com.projetospringboot.consumer;

import br.com.projetospringboot.invoice.Invoice;
import br.com.projetospringboot.invoice.InvoiceRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @JmsListener(destination = "${aws.sqs.queue.invoice.events.name}")
    public void receiveS3Event(TextMessage textMessage) throws JMSException, IOException {
        Event event = objectMapper.readValue(textMessage.getText(), Event.class);

        S3EventNotification s3Event = objectMapper.readValue(event.getMessage(), S3EventNotification.class);

        this.processInvoiceNotification(s3Event);
    }

    private void processInvoiceNotification(S3EventNotification s3Event) throws IOException {
        for (S3EventNotification.S3EventNotificationRecord s3EventRecord : s3Event.getRecords()){
            S3EventNotification.S3Entity s3Entity = s3EventRecord.getS3();

            String bucketName = s3Entity.getBucket().getName();
            String fileName = s3Entity.getObject().getKey();

            String invoiceFile = this.downloadObject(bucketName, fileName);

            Invoice invoice = objectMapper.readValue(invoiceFile, Invoice.class);
            log.info("Invoice received: {}", invoice.getNumber());

            invoiceRepository.save(invoice);

            amazonS3.deleteObject(bucketName, fileName);
        }
    }

    private String downloadObject(String bucketName, String fileName) throws IOException {
        S3Object s3File = amazonS3.getObject(bucketName, fileName);

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3File.getObjectContent()));

        String content = null;
        while ((content = bufferedReader.readLine()) != null) {
            stringBuilder.append(content);
        }

        return stringBuilder.toString();
    }
}
