package it.thomasjohansen.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REFRESH_TOKEN")
public class RefreshToken extends Token {
}
