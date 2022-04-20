package com.adobe.aem.guides.wknd.core.service.user;

import com.adobe.aem.guides.wknd.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface UserService
{
    int register(SlingHttpServletRequest request) throws IOException;

    String list(String name);

    int delete(SlingHttpServletRequest request);

    User login(String username, String password);

    boolean update(SlingHttpServletRequest request);
}
