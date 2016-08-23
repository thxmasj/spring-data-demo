package it.thomasjohansen.demo;

import it.thomasjohansen.demo.model.Authorization;
import it.thomasjohansen.demo.model.AuthorizationIdentity;
import it.thomasjohansen.demo.model.Client;
import it.thomasjohansen.demo.repository.AuthorizationRepository;
import it.thomasjohansen.demo.repository.ClientRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ModelTest {

    @Configuration
    @EnableJpaRepositories
    @EnableTransactionManagement
    public static class Config {

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.HSQL).build();
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setShowSql(true);
            return vendorAdapter;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new JpaTransactionManager(entityManagerFactory());
        }

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setJpaVendorAdapter(jpaVendorAdapter());
            factory.setPackagesToScan("it.thomasjohansen.demo.model");
            factory.setDataSource(dataSource());
            factory.setPersistenceUnitName("test");
            factory.setJpaProperties(new Properties() {{
                setProperty("javax.persistence.schema-generation.database.action", "create");
                setProperty("javax.persistence.schema-generation.scripts.action", "create");
                setProperty("javax.persistence.schema-generation.scripts.create-target", "target/schema.sql");
            }});
            factory.afterPropertiesSet();
            return factory.getObject();
        }

    }

    @Autowired
    private AuthorizationRepository authorizationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @After
    public void cleanup() {
        clientRepository.deleteAll();
        authorizationRepository.deleteAll();
    }

    @Test
    public void whenStoringAuthorizationThenAuthorizationCanBeFoundInRepository() {
        final String authorizationId = "testAuthorizationId";
        Authorization authorization = new Authorization(authorizationId, new AuthorizationIdentity(authorizationId));
        transactionTemplate().execute(transactionStatus -> {
            entityManager.persist(authorization);
            return authorizationRepository.save(authorization);
        });
        assertNotNull(authorizationRepository.findOne(authorizationId));
    }

    @Test
    public void whenStoringClientThenClientCanBeFoundInRepository() {
        final String clientId = "testClientId";
        Client client = new Client(clientId);
        transactionTemplate().execute(transactionStatus -> {
            entityManager.persist(client);
            return clientRepository.save(client);
        });
        assertNotNull(clientRepository.findOne(clientId));
    }

    private TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager);
    }

}
