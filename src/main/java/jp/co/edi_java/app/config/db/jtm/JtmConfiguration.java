package jp.co.edi_java.app.config.db.jtm;

import javax.sql.DataSource;

import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.EntityListenerProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class JtmConfiguration {

	@Bean
    @Jtm
    @ConfigurationProperties("spring.datasource.jtm")
    public DataSourceProperties jtmDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(destroyMethod = "close")
    @Jtm
    @ConfigurationProperties("spring.datasource.jtm.pool")
    public DataSource jtmDataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(jtmDataSourceProperties().getDriverClassName());
        ds.setJdbcUrl(jtmDataSourceProperties().getUrl());
        ds.setUsername(jtmDataSourceProperties().getUsername());
        ds.setPassword(jtmDataSourceProperties().getPassword());
        return ds;
    }

    @Bean
    @Jtm
    @ConfigurationProperties("spring.datasource.jtm.prop")
    DomaProperties jtmDomaProperties() {
        return new DomaProperties();
    }

    @Bean
    @Jtm
    public Config jtmConfig(final EntityListenerProvider entityListenerProvider) {
        final DomaConfigBuilder builder = jtmDomaProperties()
                .initializeDomaConfigBuilder()
                .dataSource(jtmDataSource())
                .entityListenerProvider(entityListenerProvider);
        return builder.build(jtmDomaProperties());
    }
}