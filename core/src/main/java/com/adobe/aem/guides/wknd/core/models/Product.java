package com.adobe.aem.guides.wknd.core.models;

import java.math.BigDecimal;

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
}
