package br.com.projetospringboot.notification;

import br.com.projetospringboot.enums.EventType;
import br.com.projetospringboot.product.Product;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ProductPublisher.class);

    private AmazonSNS snsClient;
    private Topic produtctsEventTopic;
    private ObjectMapper objectMapper;

    public ProductPublisher(AmazonSNS snsClient,
                            @Qualifier("productEventsTopic")Topic productsEventTopic,
                            ObjectMapper objectMapper){

        this.snsClient = snsClient;
        this.produtctsEventTopic = productsEventTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username){
        ProductEvent productEvent = new ProductEvent();
        productEvent.setId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(username);

        EventBody eventBody = new EventBody();
        eventBody.setEventType(eventType);

        try{
            eventBody.setMessage(objectMapper.writeValueAsString(productEvent));

            snsClient.publish(
                    produtctsEventTopic.getTopicArn(),
                    objectMapper.writeValueAsString(eventBody)
            );

        } catch (JsonProcessingException ex){
            LOG.error("Failed to create product event message");
        }
    }
}
