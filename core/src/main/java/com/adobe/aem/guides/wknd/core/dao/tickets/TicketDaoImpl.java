package com.adobe.aem.guides.wknd.core.dao.tickets;

import com.adobe.aem.guides.wknd.core.models.Ticket;
import com.adobe.aem.guides.wknd.core.service.db.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component(immediate = true, service = TicketDao.class)
public class TicketDaoImpl implements TicketDao
{
    private static final long serialVersionUID = 3L;

    @Reference
    private DatabaseService databaseService;

    public Set<Ticket> getAll() {
        Set<Ticket> listTickets = new HashSet<>();
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM tickets";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.execute();
                try (ResultSet rs = pst.getResultSet()) {
                    while (rs.next()) {
                        Ticket ticket = new Ticket(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getBigDecimal(4), rs.getInt(5), rs.getBigDecimal(6));
                        listTickets.add(ticket);
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listTickets;
    }

    public void add(Ticket ticket) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "INSERT INTO tickets (product_id, user_id, total, qt, unit_price) VALUES (?, ?, ?, ?, ?)";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, ticket.getProductId());
                pst.setLong(2, ticket.getUserId());
                pst.setBigDecimal(3, ticket.getTotal());
                pst.setInt(4, ticket.getQt());
                pst.setBigDecimal(5, ticket.getUnitPrice());

                pst.execute();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Set<Ticket> getTicketByUserId(Long id) {
        Set<Ticket> ticketList = new HashSet<>();
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM tickets WHERE user_id = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, id);
                pst.execute();

                try(ResultSet rs = pst.getResultSet()){
                    while(rs.next())
                        ticketList.add(new Ticket(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getBigDecimal(4), rs.getInt(5),rs.getBigDecimal(6)));

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
        return ticketList;
    }
    public Ticket getTicketById(Long id) {
        Ticket ticket = null;
        try (Connection con = databaseService.getConnection()) {
            String sql = "SELECT * FROM tickets WHERE id = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, id);
                pst.execute();

                try(ResultSet rs = pst.getResultSet()){
                    if(rs.next())
                        ticket = new Ticket(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getBigDecimal(4), rs.getInt(5),rs.getBigDecimal(6));

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
        return ticket;
    }

    public void delete(Long id) {
        try (Connection con = databaseService.getConnection()) {
            String sql = "DELETE FROM tickets WHERE id = ?";

            try(PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, id);

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

}
