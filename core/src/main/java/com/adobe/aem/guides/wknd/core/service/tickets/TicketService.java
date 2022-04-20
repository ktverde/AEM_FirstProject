package com.adobe.aem.guides.wknd.core.service.tickets;

import org.apache.sling.api.SlingHttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;

public interface TicketService
{
    int makeTicket(SlingHttpServletRequest request) throws IOException;

    String list(SlingHttpServletRequest request);

}
