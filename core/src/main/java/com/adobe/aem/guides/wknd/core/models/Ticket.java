package com.adobe.aem.guides.wknd.core.models;

import java.math.BigDecimal;

public class Ticket {

    private Long id, productId, userId;
    private BigDecimal total, unitPrice;
    private int qt;

    public Ticket() {
    }

    public Ticket(Long id, Long productId, Long userId, BigDecimal total, int qt, BigDecimal unitPrice) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.total = total;
        this.qt = qt;
        this.unitPrice = unitPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId;  }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public int getQt() { return qt; }
    public void setQt(int qt) { this.qt = qt; }
}
