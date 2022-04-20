package com.adobe.aem.guides.wknd.core.service.reports;

import com.adobe.aem.guides.wknd.core.dao.product.ProductDao;
import com.adobe.aem.guides.wknd.core.dao.tickets.TicketDao;
import com.adobe.aem.guides.wknd.core.dao.user.UserDao;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.Ticket;
import com.adobe.aem.guides.wknd.core.models.User;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import com.adobe.aem.guides.wknd.core.service.tickets.TicketService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component(immediate = true, service = ReportService.class)
public class ReportServiceImpl implements ReportService
{
    @Reference
    private TicketDao ticketDao;
    @Reference
    private ProductDao productDao;
    @Reference
    private UserDao userDao;
    @Reference
    private DatabaseService databaseService;

    public StringBuilder makeReport(SlingHttpServletRequest request) throws IOException {
        String uId = request.getParameter("uId");
        String username = request.getParameter("username");

        StringBuilder report = new StringBuilder();
        User user = null;
        User user1 = null;
        User user2 = null;
        try{
            if (!(username == null || username.isEmpty() || username.isBlank())) user1 = userDao.getUserByUsername(username);

            if (!(uId == null || uId.isEmpty() || uId.isBlank() || notLong(uId))) user2 = userDao.getUserById(Long.parseLong(uId));

            if(user1 == null && user2 == null) return report.append("<html><body><div>Informe algum parâmetro válido para busca. Confirme se o id e/ou usuário buscados estão corretos.</div></body></html>");
            else if(user1 == null) user = user2;
            else if(user2 == null) user = user1;
            else if(user1 == user2) user = user1;

            if(user == null){
                writeHtml(report, user1);

                writeHtml(report, user2);
            }
            else{
                writeHtml(report, user);
            }

            report.append("</body></html>");

        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return report;
    }

    private void writeHtml(StringBuilder report, User user) {
        Set<Ticket> ticketSet = ticketDao.getTicketByUserId(user.getuId());
        report.append("<html><body><h1>Relatório para o cliente ").append(user.getName()).append("</h1>");
        if(ticketSet.isEmpty()) report.append("<div>Esse cliente não realizou nenhuma compra conosco. Aproveite para dar uma olhada em nossos produtos, e retorne para ver seu relatório!</div>");
        for (Ticket t:ticketSet) {
            report.append(t.toHtml());
            report.append("<div>").append(productDao.getProductById(t.getProductId()).toHtml()).append("</div>");
            report.append("<hr>");
        }
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
