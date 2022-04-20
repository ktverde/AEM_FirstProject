package com.adobe.aem.guides.wknd.core.dao.tickets;

import com.adobe.aem.guides.wknd.core.models.Ticket;

import java.util.List;
import java.util.Set;

public interface TicketDao {
    Set<Ticket> getAll();
    void add(Ticket ticket);
    void delete(Long id);
    Set<Ticket> getTicketByUserId(Long id);
    Ticket getTicketById(Long id);
}
