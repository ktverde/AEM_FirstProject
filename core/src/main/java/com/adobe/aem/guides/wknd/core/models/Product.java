package com.adobe.aem.guides.wknd.core.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long pId;
    private BigDecimal price;
    private String type;
    private String name;
    private String description;

    public Product() {
    }

    public Product(Long pId, BigDecimal price, String type, String name, String description) {
        this.pId = pId;
        this.price = price;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Long getpId() { return pId; }
    public void setpId(Long pId) { this.pId = pId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesc() { return description; }
    public void setDesc(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return pId.equals(product.pId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId);
    }

    public String toHtml() {
        return String.format("<div>Nome do produto: %s</div><div>Descrição: %s</div><div>Preço: %f</div>",
                this.name, this.description, this.price);
    }
}
