package com.adobe.aem.guides.wknd.core.dao.product;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.User;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    void add(Product product);
    boolean delete(Long pId);
    boolean update(Long pId, Product product);
    Product getProductById(Long pId);
}
