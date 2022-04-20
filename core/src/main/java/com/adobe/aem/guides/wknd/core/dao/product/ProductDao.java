package com.adobe.aem.guides.wknd.core.dao.product;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.User;

import java.util.List;
import java.util.Set;

public interface ProductDao {
    Set<Product> getAll(boolean order);
    void add(Product product);
    void delete(Long pId);
    boolean update(Long pId, Product product);
    Product getProductById(Long pId);
}
