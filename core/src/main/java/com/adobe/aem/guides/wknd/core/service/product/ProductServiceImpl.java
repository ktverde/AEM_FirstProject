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
import java.util.stream.Collectors;

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

        Set<Product> productSet = productDao.getAll(false);
        Set<Product> productTemp = new HashSet<>();
        boolean check = false;
        try {

            if(!(pId == null || pId.isEmpty() || pId.isBlank() || notLong(pId))) {
                Long id = Long.parseLong(pId);
                Product product = productDao.getProductById(id);
                if (product != null) productTemp.add(product);
                else check = true;
            }
            if(!(searchFor == null || searchFor.isEmpty() || searchFor.isBlank())) {
                for (Product p : productSet) {
                    if (p.getName().toLowerCase().contains(searchFor.toLowerCase())
                            || p.getDesc().toLowerCase().contains(searchFor.toLowerCase())
                            || p.getType().toLowerCase().contains(searchFor.toLowerCase()))
                        productTemp.add(p);
                }
            }
            if(!(order == null || order.isEmpty() || order.isBlank())){
                if(order.equals("s")) {
                    if (productTemp.isEmpty()) productTemp = productDao.getAll(true);
                    else {
                        productTemp = productTemp.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toCollection(LinkedHashSet::new));
                    }
                }
            }

        } catch(Exception e) {
            e.getStackTrace();
        }

        if(check)
            return "Nenhum produto encontrado";

        //Se depois de tudo, ainda estiver vazia, preenche com uma lista completa
        else if(productTemp.isEmpty()) productTemp = productSet;

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

    public boolean update(SlingHttpServletRequest request) {
        try{

            String pId = request.getParameter("pId");

            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Product objProduct = gson.fromJson(reader, Product.class);

            if (pId == null || pId.isEmpty() || pId.isBlank() || notLong(pId)) return false;
            Long id = Long.parseLong(pId);

            Product p = productDao.getProductById(id);

            String newName = null;
            String newDesc = null;
            String newType = null;
            BigDecimal newPrice = null;

            if(objProduct != null){
                newName = objProduct.getName();
                newDesc = objProduct.getDesc();
                newType = objProduct.getType();
                newPrice = objProduct.getPrice();
            }

            if (p != null) {

                if(!p.getPrice().equals(newPrice) && newPrice != null) {
                    p.setPrice(newPrice);
                }
                if(!p.getType().equals(newType) && newType != null) {
                    if (!newType.isEmpty() || !newType.isBlank())
                        p.setType(newType);
                }
                if(!p.getName().equals(newName) && newName != null) {
                    if (!newName.isEmpty() || !newName.isBlank())
                        p.setName(newName);
                }
                if(!p.getDesc().equals(newDesc) && newDesc != null) {
                    if (!newDesc.isEmpty() || !newDesc.isBlank())
                        p.setDesc(newDesc);
                }

                return productDao.update(id, p);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
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
