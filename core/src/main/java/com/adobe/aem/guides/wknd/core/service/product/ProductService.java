package com.adobe.aem.guides.wknd.core.service.product;

import com.adobe.aem.guides.wknd.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService
{
    int register(SlingHttpServletRequest request) throws IOException;

    String list(SlingHttpServletRequest request);

    int delete(SlingHttpServletRequest request);

    boolean update(SlingHttpServletRequest request);
}
