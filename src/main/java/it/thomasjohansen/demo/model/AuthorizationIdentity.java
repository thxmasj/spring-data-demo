package it.thomasjohansen.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthorizationIdentity {

    @Id
    private String id;
    private String ssn;

    private AuthorizationIdentity() {
        // Default constructor required by JPA
    }

    public AuthorizationIdentity(String id) {
        this.id = id;
    }
}
