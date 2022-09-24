package br.com.projetospringboot.config.aws.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class SnsConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SnsConfig.class);

    private final String productEventsTopic;
    private final AmazonSNS snsClient;

    public SnsConfig(){
        LOG.info("Start create local topic.....");

        this.snsClient = AmazonSNSClient.builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        CreateTopicRequest topic = new CreateTopicRequest("product-events");
        this.productEventsTopic = this.snsClient.createTopic(topic).getTopicArn();

        LOG.info("SNS local topic created with ARN: {}", this.productEventsTopic);
    }

    @Bean
    public AmazonSNS getSnsClient(){
        return this.snsClient;
    }

    @Bean(name = "productEventsTopic")
    public Topic snsProductEventsTopic(){
        return new Topic().withTopicArn(productEventsTopic);
    }

}
