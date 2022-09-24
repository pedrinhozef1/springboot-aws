package br.com.projetospringboot.notification;

import br.com.projetospringboot.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBody {
    private EventType eventType;
    private String message;
}
