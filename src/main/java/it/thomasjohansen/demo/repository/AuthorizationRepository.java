package it.thomasjohansen.demo.repository;

import it.thomasjohansen.demo.model.Authorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizationRepository extends CrudRepository<Authorization, String> {

    Authorization findByRefreshTokenId(String id);

    Authorization findByAccessTokenId(String id);

    List<Authorization> findByAccessTokenExpiryGreaterThan(Long value);

    List<Authorization> findByIdentitySsn(String ssn);

}
