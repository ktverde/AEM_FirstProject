package com.adobe.aem.guides.wknd.core.service.db;

import com.day.commons.datasource.poolservice.DataSourcePool;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

@Component(immediate = true, service = DatabaseService.class)
public class DatabaseServiceImpl implements DatabaseService {

    private final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);
    @Reference
    private DataSourcePool dataSourcePool;

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) dataSourcePool.getDataSource("aem_db");
            connection = dataSource.getConnection();
            logger.debug("Conexão feita");
        }
        catch (Exception e) {
            logger.debug("Não foi possível realizar conexão." + e.getMessage());
        }
        return connection;
    }

}
