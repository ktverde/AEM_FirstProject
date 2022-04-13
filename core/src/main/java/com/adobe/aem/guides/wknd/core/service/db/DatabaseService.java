package com.adobe.aem.guides.wknd.core.service.db;

import java.sql.Connection;

public interface DatabaseService {
    Connection getConnection();
}
