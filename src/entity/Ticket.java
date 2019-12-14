package entity;

import enums.IssueType;
import enums.Status;

import java.util.Date;

public class Ticket {
    int ticketID;
    IssueType issueType;
    String description;
    Agent agentWorking;
    Integer rating;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agent getAgentWorking() {
        return agentWorking;
    }

    public void setAgentWorking(Agent agentWorking) {
        this.agentWorking = agentWorking;
    }

    public Status getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Status ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    Status ticketStatus;
    Date createdDate;

    public Ticket(String ticket_description, IssueType issueType) {
        this.description=ticket_description;
        this.issueType=issueType;
    }
}
