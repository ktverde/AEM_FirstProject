package com.adobe.aem.guides.wknd.core.models;

import java.math.BigDecimal;
import java.util.Objects;

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

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toHtml(){
        return String.format("<div>Id da nota fiscal: %d</div><br><div>Id do produto: %d</div><div>Id do cliente: %d</div><div>Total da compra realizada: %f</div><div>Quantidade comprada: %d</div>",
                this.id, this.productId, this.userId, this.total, this.qt);
    }

}
