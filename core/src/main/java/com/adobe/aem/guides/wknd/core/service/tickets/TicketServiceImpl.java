package com.adobe.aem.guides.wknd.core.service.tickets;

import com.adobe.aem.guides.wknd.core.dao.product.ProductDao;
import com.adobe.aem.guides.wknd.core.dao.tickets.TicketDao;
import com.adobe.aem.guides.wknd.core.dao.user.UserDao;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.Ticket;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.adobe.aem.guides.wknd.core.service.product.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component(immediate = true, service = TicketService.class)
public class TicketServiceImpl implements TicketService
{
    @Reference
    private TicketDao ticketDao;
    @Reference
    private ProductDao productDao;
    @Reference
    private UserDao userDao;
    @Reference
    private DatabaseService databaseService;

    public int makeTicket(SlingHttpServletRequest request) throws IOException {
        int count = 0;
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Ticket>>() {}.getType();
            List<Ticket> ticketList = new Gson().fromJson(reader, listType);

            for (Ticket t : ticketList) {
                t.setId(null);
                Product product = productDao.getProductById(t.getProductId());
                User user = userDao.getUserById(t.getUserId());

                if(product != null && user != null) {

                    t.setUnitPrice(product.getPrice());
                    t.setTotal(product.getPrice().multiply(BigDecimal.valueOf(t.getQt())));
                    ticketDao.add(t);
                    count++;
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return count;
    }

    public String list(SlingHttpServletRequest request) {
        String id = request.getParameter("id");
        String userId = request.getParameter("uId");
        boolean check = false;

        Set<Ticket> ticketList = ticketDao.getAll();
        Set<Ticket> ticketTemp = new HashSet<>();
        try {
            if(!(id == null || id.isEmpty() || id.isBlank() || notLong(id))) {
                Long longId = Long.parseLong(id);
                Ticket ticket = ticketDao.getTicketById(longId);
                if (ticket != null) ticketTemp.add(ticket);
                else check = true;
            }
            if(!(userId == null || userId.isEmpty() || userId.isBlank() || notLong(userId))) {
                Long longUserId = Long.parseLong(userId);
                Set<Ticket> tickets = ticketDao.getTicketByUserId(longUserId);
                ticketTemp.addAll(tickets);
            }

            if(check) return "Nenhuma nota fiscal encontrada";
            else if(ticketTemp.isEmpty()) ticketTemp = ticketList;

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return new Gson().toJson(ticketTemp);
    }
    private boolean notLong(String pId) {
        try{
            Long.parseLong(pId);
            return false;
        }catch (Exception e){
            return true;
        }
    }
}
