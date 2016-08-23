package it.thomasjohansen.demo.model;

import javax.persistence.*;

@Entity
public class Authorization {

    @Id
    private String id;
    private String clientId;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private AuthorizationIdentity identity;
    @OneToOne(cascade = CascadeType.ALL)
    private AccessToken accessToken;
    @OneToOne(cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    private Authorization() {
        // Default constructor required by JPA
    }

    public Authorization(String id, AuthorizationIdentity identity) {
        this.id = id;
        this.identity = identity;
    }

}
