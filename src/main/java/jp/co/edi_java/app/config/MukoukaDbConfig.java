package jp.co.edi_java.app.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class MukoukaDbConfig {

	private String URL = "jdbc:oracle:thin:@10.201.0.5:1521:mukouka";
	private String USERNAME = "test";
	private String PASSWORD = "testa";
	private String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";


	public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url(URL)
                .username(USERNAME)
                .password(PASSWORD)
                .driverClassName(DRIVER_CLASS_NAME)
                .build();
    }
}