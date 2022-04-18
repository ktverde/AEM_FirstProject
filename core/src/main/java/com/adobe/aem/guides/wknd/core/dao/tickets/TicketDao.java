package com.adobe.aem.guides.wknd.core.dao.tickets;

import com.adobe.aem.guides.wknd.core.models.Ticket;

import java.util.List;

public interface TicketDao {
    List<Ticket> getAll();
    void add(Ticket ticket);
    void delete(Long id);
    Ticket getTicketById(Long id);
}
