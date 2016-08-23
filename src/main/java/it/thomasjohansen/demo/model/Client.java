package it.thomasjohansen.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {

    @Id
    private String id;

    private Client() {
    }

    public Client(String id) {
        this.id = id;
    }
}
