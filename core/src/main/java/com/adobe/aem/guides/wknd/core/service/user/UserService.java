package com.adobe.aem.guides.wknd.core.service.user;

import com.adobe.aem.guides.wknd.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface UserService
{
    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    public String list(String name);

    public boolean delete(String name);

    public User login(String username, String password);
}
