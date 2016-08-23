package it.thomasjohansen.demo.model;

import javax.persistence.*;

@DiscriminatorColumn(name = "TOKEN_TYPE", discriminatorType =  DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Token {

    @Id
    protected String id;
    private Long expiry;

}
