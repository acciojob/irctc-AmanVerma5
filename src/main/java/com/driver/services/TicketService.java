package com.driver.services;


import com.driver.EntryDto.BookTicketEntryDto;
import com.driver.model.Passenger;
import com.driver.model.Ticket;
import com.driver.model.Train;
import com.driver.repository.PassengerRepository;
import com.driver.repository.TicketRepository;
import com.driver.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    PassengerRepository passengerRepository;


    public Integer bookTicket(BookTicketEntryDto bookTicketEntryDto)throws Exception{

        //Check for validity
        //Use bookedTickets List from the TrainRepository to get bookings done against that train
        // Incase the there are insufficient tickets
        // throw new Exception("Less tickets are available");
        //otherwise book the ticket, calculate the price and other details
        //Save the information in corresponding DB Tables
        //Fare System : Check problem statement
        //Incase the train doesn't pass through the requested stations
        //throw new Exception("Invalid stations");
        //Save the bookedTickets in the train Object
        //Also in the passenger Entity change the attribute bookedTickets by using the attribute bookingPersonId.
        //And the end return the ticketId that has come from db
        Train train=trainRepository.findById(bookTicketEntryDto.getTrainId()).get();
        List<Ticket> bookedTickets=train.getBookedTickets();
        int seatsOccupied=0;
        for(Ticket ticket:bookedTickets){
            seatsOccupied+=ticket.getPassengersList().size();
        }
        int totalSeats= train.getNoOfSeats();
        int seatsLeft=totalSeats-seatsOccupied;
        if(seatsLeft<bookTicketEntryDto.getNoOfSeats()) throw new Exception("Less tickets are available");
        if(!train.getRoute().contains(bookTicketEntryDto.getFromStation().name()) || !train.getRoute().contains(bookTicketEntryDto.getToStation().name())){
            throw new Exception("Invalid stations");
        }

        train.setNoOfSeats(seatsLeft);
        Ticket ticket=new Ticket();
        ticket.setTrain(train);
        ticket.setFromStation(bookTicketEntryDto.getFromStation());
        ticket.setToStation(bookTicketEntryDto.getToStation());
        int total_fare=(ticket.getToStation().getStationNo()-ticket.getFromStation().getStationNo())*300;
        ticket.setTotalFare(total_fare);
        List<Integer> passengerIds=bookTicketEntryDto.getPassengerIds();
        List<Passenger> passengerList=new ArrayList<>();
        for(int id:passengerIds){
            Passenger passenger=passengerRepository.findById(id).get();
            passengerList.add(passenger);
        }
        ticket.setPassengersList(passengerList);
        Passenger p=passengerRepository.findById(bookTicketEntryDto.getBookingPersonId()).get();
        p.getBookedTickets().add(ticket);

        ticket=ticketRepository.save(ticket);

        trainRepository.save(train);
       return ticket.getTicketId();

    }
}
