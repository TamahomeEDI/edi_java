package jp.co.edi_java.app.config.db;

import javax.sql.DataSource;

import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.EntityListenerProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration

public class DefaultConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

	@Bean(destroyMethod = "close")
    @Primary
    @ConfigurationProperties("spring.datasource.pool")
    public DataSource dataSource() {
    	final HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(dataSourceProperties().getDriverClassName());
        ds.setJdbcUrl(dataSourceProperties().getUrl());
        ds.setUsername(dataSourceProperties().getUsername());
        ds.setPassword(dataSourceProperties().getPassword());
        return ds;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.prop")
    public DomaProperties domaProperties() {
        return new DomaProperties();
    }

    @Bean
    @Primary
    public Config config(final EntityListenerProvider entityListenerProvider) {
        final DomaConfigBuilder builder = domaProperties()
                .initializeDomaConfigBuilder()
                .dataSource(dataSource())
                .entityListenerProvider(entityListenerProvider);
        return builder.build(domaProperties());
    }
}
