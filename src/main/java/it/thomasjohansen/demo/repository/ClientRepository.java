package it.thomasjohansen.demo.repository;

import it.thomasjohansen.demo.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
}
