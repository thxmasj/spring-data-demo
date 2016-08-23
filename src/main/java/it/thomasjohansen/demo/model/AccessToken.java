package it.thomasjohansen.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ACCESS_TOKEN")
public class AccessToken extends Token {

}
