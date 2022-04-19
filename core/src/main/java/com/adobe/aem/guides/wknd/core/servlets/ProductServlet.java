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

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.product.ProductService;
import com.adobe.aem.guides.wknd.core.service.user.UserService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

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
        SLING_SERVLET_PATHS + "=" + "/bin/keepalive/productService",
        SLING_SERVLET_EXTENSIONS + "=" + "txt", SLING_SERVLET_EXTENSIONS + "=" + "json"})

@ServiceDescription("Product Service All")
public class ProductServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 5L;

    @Reference
    private ProductService productService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String json = productService.list(request);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try{
            int count = productService.register(request);
            if(count > 0)
                response.getWriter().write(count + " produto(s) cadastrado(s) com sucesso");
            else
                response.getWriter().write("Id de produto(s) já se encontra(m) cadastrado(s)");
        }catch(Exception e){
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            int count = productService.delete(request);
            if (count > 0)
                response.getWriter().write(count + " produto(s) removido(s) com sucesso");
            else
                response.getWriter().write("Produto(s) não existe(m) em nosso sistema");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String product = request.getParameter("pId");

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Product objProduct = gson.fromJson(reader, Product.class);

        if(objProduct != null){
            if(productService.update(product, objProduct.getName(), objProduct.getDesc(), objProduct.getType(), objProduct.getPrice()))
                response.getWriter().write("Produto alterado com sucesso com os devidos dados informados. ");
            else
                response.getWriter().write("Produto nao existe em nosso sistema");
        }
        else
            response.getWriter().write("Informe o Json do produto para ser atualizado");

    }
}
