/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

//import com.adobe.cq.wcm.core.components.models.List;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.adobe.aem.guides.wknd.core.service.user.UserService;

import com.drew.lang.annotations.NotNull;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import static org.apache.sling.api.servlets.ServletResolverConstants.*;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingAllMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */

@Component(immediate = true, service = Servlet.class, property = {
        SLING_SERVLET_METHODS + "=" + "POST",
        SLING_SERVLET_METHODS + "=" + "GET",
        SLING_SERVLET_METHODS + "=" + "DELETE",
        SLING_SERVLET_PATHS + "=" + "/bin/keepalive/userService",
        SLING_SERVLET_EXTENSIONS + "=" + "txt", SLING_SERVLET_EXTENSIONS + "=" + "json"})

@ServiceDescription("User Service All")
public class UserServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private UserService userService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String json = userService.list(name);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try{
            int count = userService.register(request);
            if(count > 0)
                response.getWriter().write(count + " usuário(s) cadastrado(s) com sucesso");
            else
                response.getWriter().write("Usuários já se encontram cadastrados");
        }catch(Exception e){
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        int count = userService.delete(request);
        if(count > 0)
            response.getWriter().write(count + " usuário(s) removido(s) com sucesso");
        else
            response.getWriter().write("Usuario(s) nao existe(m) em nosso sistema");

    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        User objUser = gson.fromJson(reader, User.class);
        if(objUser != null){
            if(userService.update(user, objUser.getUsername(), objUser.getPassword(), objUser.getName()))
                response.getWriter().write("Usuario alterado com sucesso com os devidos dados informados. ");
            else
                response.getWriter().write("Usuario nao existe em nosso sistema");
        }
        else
            response.getWriter().write("Informe o Json do usuário para ser atualizado");

    }
}
