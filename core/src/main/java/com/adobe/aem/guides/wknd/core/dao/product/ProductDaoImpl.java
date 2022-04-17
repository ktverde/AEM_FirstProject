package com.adobe.aem.guides.wknd.core.dao.product;

import com.adobe.aem.guides.wknd.core.dao.user.UserDao;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = ProductDao.class)
public class ProductDaoImpl implements ProductDao
{
    private static final long serialVersionUID = 3L;

    @Reference
    private DatabaseService databaseService;

    public List<Product> getAll() {
        List<Product> listProducts = new ArrayList<>();
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM products";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.execute();
                try (ResultSet rs = pst.getResultSet()) {
                    while (rs.next()) {
                        Product product = new Product(rs.getLong(1),rs.getBigDecimal(2),rs.getString(3), rs.getString(4), rs.getString(5));
                        listProducts.add(product);
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e.getMessage() + "1");
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return listProducts;
    }

    public void add(Product product) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "INSERT INTO products (pId, price, type, name, description) VALUES (?, ?, ?, ?, ?)";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, product.getpId());
                pst.setBigDecimal(2, product.getPrice());
                pst.setString(3, product.getType());
                pst.setString(4, product.getName());
                pst.setString(5, product.getDesc());
                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
    }
    public Product getProductById(Long pId) {
        Product product = null;
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM products WHERE pId = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, pId);
                pst.execute();

                try(ResultSet rs = pst.getResultSet()){
                    if(rs.next())
                        product = new Product(rs.getLong(1),rs.getBigDecimal(2),rs.getString(3), rs.getString(4), rs.getString(5));

                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return product;
    }

    public boolean delete(Long pId) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "DELETE FROM products WHERE pId = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, pId);

                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return true;
    }

    public boolean update(Long pId, Product product){
        try (Connection con = databaseService.getConnection()) {
            String sql = "UPDATE products SET pId = ?, price = ?, type =?, name = ?, description = ? WHERE pId = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, product.getpId());
                pst.setBigDecimal(2, product.getPrice());
                pst.setString(3, product.getType());
                pst.setString(4, product.getName());
                pst.setString(5, product.getDesc());
                pst.setLong(6, pId);

                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "2");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "3");
        }
        return true;
    }
}