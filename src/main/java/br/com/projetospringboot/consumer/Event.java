package br.com.projetospringboot.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // ignorar campos a mais que nao estao mapeados aqui
public class Event {
    @JsonProperty("TopicArn")
    private String topicArn; // quem publicou?

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Messageid")
    private String messageId; // id do evento

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Message")
    private String message;
}
