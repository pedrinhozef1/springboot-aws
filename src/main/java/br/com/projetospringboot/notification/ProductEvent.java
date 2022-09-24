package br.com.projetospringboot.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEvent {
    private long id;
    private String code;
    private String username;
}
