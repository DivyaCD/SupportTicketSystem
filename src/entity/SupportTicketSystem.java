package entity;


import enums.Status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SupportTicketSystem {
    int random=1;
    int agentTotal;
    int timeToSolve;
    List<Agent> availableAgents=new ArrayList<>();
    Map<Agent,Ticket> busyAgents=new HashMap<>();
    Comparator<Ticket> dateComparator=(t1,t2)->{ return t1.createdDate.compareTo(t2.createdDate);};
    Comparator<Ticket> ratingComparator=(t1,t2)->{ return t1.rating.compareTo(t2.rating);};
    Comparator<Agent> ratingAgentComparator=(t1,t2)->{ return t2.rating.compareTo(t1.rating);};
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    PriorityQueue<Ticket> pendingTickets=new PriorityQueue<>(dateComparator);
    List<Customer> customers=new ArrayList<>();
    List<Ticket> tickets=new ArrayList<>();

    Map<String,PriorityQueue<Ticket>> topRated=new HashMap<>();
    Map<String,List<Ticket>> issueType_Date=new HashMap<>();


    public SupportTicketSystem(int agentTotal, int timeToSolve) {
        this.agentTotal = agentTotal;
        this.timeToSolve = timeToSolve;
    }

    public SupportTicketSystem(int agentTotal) {
        this.agentTotal = agentTotal;
    }

    public SupportTicketSystem() {
    }

    public int generateTicketID() {
        int ticketID=random;
        random++;
        return ticketID;
    }

    public void createAgent(String agentID){
        System.out.println("Creating agent with id:"+agentID);

        if(availableAgents.size()==agentTotal){
            System.out.println("Cant create agent: "+agentID+" .Exceeded the max count of agents:"+agentTotal);
        }else{
            if(availableAgents.stream().anyMatch(a->a.agentID.equals(agentID))){
                System.out.println("Agent already exists with this name. Cannot create given agent");
                return;
            }
            availableAgents.add(new Agent(agentID));
            System.out.println("Created new agent:"+agentID);
        }
    }

    public void createCustomer(String customer) {
        System.out.println("Creating customer with id:"+customer);
        if(customers.stream().anyMatch(p->p.customerID.equals(customer))) {
            System.out.println("Customer already exists with this name. Cannot create given customer");
            return;
        }
        customers.add(new Customer(customer));
        System.out.println("Customer is created with id:"+customer);
    }

    public Customer getCustomer(String customer1) {
        return customers.stream().filter(c->c.customerID.equals(customer1)).findAny().orElse(null);
    }

    public void pushTicketToSystem(Ticket ticket) throws ParseException {
        if(availableAgents.isEmpty()){
            System.out.println("All agents are busy. Ticket is pushed to pending queue:"+ticket.ticketID);
            ticket.ticketStatus=Status.OPEN;
            pendingTickets.add(ticket);
        }else{
            System.out.println("Assign ticket to agent");
            assignTicket(ticket,availableAgents.get(0));
            ticket.ticketStatus=Status.IN_PROGRESS;
        }

        Date d=formatter.parse(formatter.format(ticket.createdDate));

        List<Ticket> t_issueType=issueType_Date.get(d.toString()+"--"+ticket.issueType);
        if(t_issueType==null){
            issueType_Date.put(d.toString()+"--"+ticket.issueType,new ArrayList(Arrays.asList(ticket)));
        }else{
            t_issueType.add(ticket);
        }
    }

    private void assignTicket(Ticket ticket, Agent agent) {
        for(Agent a:availableAgents){
            if(a.agentID==agent.agentID){
                availableAgents.remove(a);
                busyAgents.put(a,ticket);
                ticket.agentWorking=a;
                System.out.println("Ticket is assigned to agent:"+a.agentID);
                break;
            }
        }
    }

    private void assignTicket(Agent agent) {
        if(!pendingTickets.isEmpty())
        for(Agent a:availableAgents){
            if(a.agentID==agent.agentID){
                availableAgents.remove(a);
                Ticket t1=pendingTickets.poll();
                t1.agentWorking=a;
                busyAgents.put(a,t1);
                System.out.println("Ticket is assigned to agent:"+a.agentID+" Ticket ID:"+t1.ticketID);
                break;
            }
        }
    }

    public void printBusyAgents() {
        for(Map.Entry<Agent,Ticket> agent:busyAgents.entrySet()){
            System.out.println(agent.getKey().agentID +" is working on ticket "+agent.getValue().ticketID);
        }
    }

    public void printPendingTickets() {
        System.out.println("Pending tickets");
        pendingTickets.forEach(a->System.out.println(a.ticketID+" "+a.createdDate));
    }

    public void completedTask(int s) {
        for(Map.Entry<Agent,Ticket> a:busyAgents.entrySet()){
            if(a.getValue().ticketID==s){
                System.out.println("Ticket is completed:"+s);
                System.out.println("Agent is free:"+a.getKey().agentID);
                a.getValue().ticketStatus=Status.CLOSED;
                availableAgents.add(a.getKey());
                busyAgents.remove(a.getKey());
                assignTicket(a.getKey());
                break;
            }
        }
    }

    public void cancelTicket(Customer customer3) {
        Ticket t=customer3.currentTicket;

        for(Map.Entry<Agent,Ticket> a:busyAgents.entrySet()){
            if(a.getValue().ticketID==t.ticketID){
                System.out.println("Ticket is canceled:"+t.ticketID);
                System.out.println("Agent is free:"+a.getKey().agentID);
                a.getValue().ticketStatus=Status.CANCEL;
                availableAgents.add(a.getKey());
                busyAgents.remove(a.getKey());
                assignTicket(a.getKey());
                break;
            }
        }
    }

    public void rateTask(int rating,int ticketID) {
        Ticket t=tickets.stream().filter(a->a.ticketID==ticketID).findAny().get();
        if(t==null){
            System.out.println("No ticket with ticketID"+ticketID);
        }
        System.out.println("Rated ticket:"+ticketID+" with "+rating);
        t.rating=rating;
        
        if(topRated.get(t.createdDate.toString())==null){
            PriorityQueue<Ticket> p=new PriorityQueue<>(ratingComparator);
            p.add(t);
            topRated.put(t.createdDate.toString(),p);
        }else{
            PriorityQueue<Ticket> p=topRated.get(t.createdDate.toString());
            p.add(t);
        }
    }

    public void displayTop(int i,String date) {
        System.out.println("Top performers");
        Set<Agent> topPerformers=new TreeSet<>(ratingAgentComparator);

        for(Map.Entry<String,PriorityQueue<Ticket>> e:topRated.entrySet()){
            if(e.getKey().contains(date)){
                for(Ticket t:e.getValue()) {
                    if(topPerformers.size()<i) {
                        t.agentWorking.rating = t.agentWorking.rating + t.rating;
                        topPerformers.add(t.agentWorking);
                    }
                }
                break;
            }
        }
        for(Agent a:topPerformers){
            System.out.println(a.agentID);
            a.rating=0;
        }
    }

    public void displayCountOfTicketsOfEachType(String s) {
        for(Map.Entry<String,List<Ticket>> i:issueType_Date.entrySet()){
            if(i.getKey().contains(s)){
                System.out.println("Ticket count for: "+i.getKey()+" is "+i.getValue().size());
            }
        }
    }
}
