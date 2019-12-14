package entity;

import enums.IssueType;

import java.text.ParseException;
import java.util.Date;

public class Customer extends Account {
    String customerID;
    Ticket currentTicket;

    public Customer(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Ticket getCurrentTicket() {
        return currentTicket;
    }

    public void setCurrentTicket(Ticket currentTicket) {
        this.currentTicket = currentTicket;
    }

    public void createTicket(SupportTicketSystem supportTicketSystem,String ticket_description, IssueType issueType) throws ParseException {
        int ticketID = supportTicketSystem.generateTicketID();

        System.out.println("Creating a ticket: " + ticketID);
        Ticket ticket = new Ticket(ticket_description, issueType);
        ticket.setCreatedDate(new Date());
        ticket.ticketID=ticketID;
        currentTicket = ticket;
        supportTicketSystem.tickets.add(ticket);
        System.out.println("Created a ticket: " + ticketID);

        supportTicketSystem.pushTicketToSystem(ticket);
    }
}
