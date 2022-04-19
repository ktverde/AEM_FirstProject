package com.adobe.aem.guides.wknd.core.service.product;

import com.adobe.aem.guides.wknd.core.dao.product.ProductDao;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.adobe.aem.guides.wknd.core.service.user.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

@Component(immediate = true, service = ProductService.class)
public class ProductServiceImpl implements ProductService
{
    @Reference
    private ProductDao productDao;
    @Reference
    private DatabaseService databaseService;

    public int register(SlingHttpServletRequest request) throws IOException {
        int count = 0;
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> productList = new Gson().fromJson(reader, listType);

            for (Product p : productList) {
                if (productDao.getProductById(p.getpId()) == null) {
                    productDao.add(p);
                    count++;
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public String list(SlingHttpServletRequest request) {
        String pId = request.getParameter("pId");
        String searchFor = request.getParameter("searchFor");
        String order = request.getParameter("order");

        Set<Product> productList = productDao.getAll(false);
        Set<Product> productTemp = new HashSet<>();
        try {

            if(!(pId == null || pId.isEmpty() || pId.isBlank() || notLong(pId))) {
                Long id = Long.parseLong(pId);
                Product product = productDao.getProductById(id);
                if (product != null) productTemp.add(product);
            }
            else if(!(searchFor == null || searchFor.isEmpty() || searchFor.isBlank())) {
                for (Product p : productList) {
                    if (p.getName().toLowerCase().contains(searchFor.toLowerCase())
                            || p.getDesc().toLowerCase().contains(searchFor.toLowerCase())
                            || p.getType().toLowerCase().contains(searchFor.toLowerCase()))
                        productTemp.add(p);
                }
            }
            else if(!(order == null || order.isEmpty() || order.isBlank())){
                productTemp = productDao.getAll(true);
            }
            else {
                productTemp = productList;
            }

        } catch(Exception e) {
            e.getStackTrace();
        }
        return new Gson().toJson(productTemp);
    }

    public int delete(SlingHttpServletRequest request) {
        int count = 0;
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> productList = new Gson().fromJson(reader, listType);

            for (Product p : productList) {
                if (productDao.getProductById(p.getpId()) != null) {
                    productDao.delete(p.getpId());
                    count++;
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public boolean update(String pId, String name, String desc, String type, BigDecimal price) {

        if (pId == null || pId.isEmpty() || pId.isBlank() || notLong(pId)) return false;
        Long id = Long.parseLong(pId);

        Product p = productDao.getProductById(id);

        if (p != null) {

            if(!p.getPrice().equals(price) && price != null) {
                p.setPrice(price);
            }
            if(!p.getType().equals(type) && type != null) {
                if (!type.isEmpty() || !type.isBlank())
                    p.setType(type);
            }
            if(!p.getName().equals(name) && name != null) {
                if (!name.isEmpty() || !name.isBlank())
                    p.setName(name);
            }
            if(!p.getDesc().equals(desc) && desc != null) {
                if (!desc.isEmpty() || !desc.isBlank())
                    p.setDesc(desc);
            }

            return productDao.update(id, p);
        }

        return false;
    }

    private boolean notLong(String pId) {
        try{
            Long.parseLong(pId);
            return false;
        }catch (Exception e){
            return true;
        }
    }
}
