import entity.Customer;
import entity.SupportTicketSystem;
import enums.IssueType;

import java.text.ParseException;

public class SupportTicketSystemDemo {
    public static void main(String[] args) throws ParseException {

        SupportTicketSystem supportTicketSystem = new SupportTicketSystem(3);
        supportTicketSystem.createAgent("agent1");
        supportTicketSystem.createAgent("agent2");
        supportTicketSystem.createAgent("agent3");
        supportTicketSystem.createAgent("agent4");

        System.out.println();

        supportTicketSystem.createCustomer("customer1");
        supportTicketSystem.createCustomer("customer2");
        supportTicketSystem.createCustomer("customer3");
        supportTicketSystem.createCustomer("customer4");
        supportTicketSystem.createCustomer("customer5");
        supportTicketSystem.createCustomer("customer6");



        System.out.println();

        Customer customer1=supportTicketSystem.getCustomer("customer1");
        customer1.createTicket(supportTicketSystem,"ticket description1",IssueType.T1);

        Customer customer2=supportTicketSystem.getCustomer("customer2");
        customer2.createTicket(supportTicketSystem,"ticket description2",IssueType.T2);

        Customer customer3=supportTicketSystem.getCustomer("customer3");
        customer3.createTicket(supportTicketSystem,"ticket description3",IssueType.T3);

        Customer customer4=supportTicketSystem.getCustomer("customer4");
        customer4.createTicket(supportTicketSystem,"ticket description4",IssueType.T3);

//        Thread.sleep(3000);

        Customer customer5=supportTicketSystem.getCustomer("customer5");
        customer5.createTicket(supportTicketSystem,"ticket description5",IssueType.T3);

//        Thread.sleep(3000);

        Customer customer6=supportTicketSystem.getCustomer("customer6");
        customer6.createTicket(supportTicketSystem,"ticket description6",IssueType.T3);

        System.out.println();
        supportTicketSystem.printBusyAgents(); // question1

        System.out.println();
        supportTicketSystem.printPendingTickets(); //question2

        System.out.println();
        supportTicketSystem.completedTask(2); //question3
        supportTicketSystem.rateTask(3,3);
        supportTicketSystem.rateTask(4,2);
        supportTicketSystem.rateTask(1,1);

        supportTicketSystem.printPendingTickets();

        System.out.println();

        supportTicketSystem.cancelTicket(customer3); //question4
        supportTicketSystem.printPendingTickets();

        System.out.println();
        supportTicketSystem.displayTop(2,"Sat Dec 14"); //question5

        System.out.println();
        supportTicketSystem.displayCountOfTicketsOfEachType("Sat Dec 14"); //question6

    }
}
