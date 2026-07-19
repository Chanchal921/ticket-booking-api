package com.example.demo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.example.demo.enumm.TicketStatus;
import com.example.demo.request.Passenger;
import com.example.demo.response.Ticket;

import ch.qos.logback.core.status.Status;

@RestController
public class TicketController {
	private HashMap<Integer,Ticket> map = new HashMap<>();
	
	@PostMapping(value="/ticket",
			consumes = {"application/xml","application/json"},
			produces= {"application/json","application/xml"}
	)
	public ResponseEntity<?> bookTicket(@RequestBody Passenger passenger){
		if (passenger == null) {
		    return new ResponseEntity<>("Request body is empty",
		            HttpStatus.BAD_REQUEST);
		}
		
		if(passenger.getFrom() == null || passenger.getFrom().isBlank()) {
		    return new ResponseEntity<>("Source cannot be empty",
		            HttpStatus.BAD_REQUEST);
		}

		if(passenger.getTo() == null || passenger.getTo().isBlank()) {
		    return new ResponseEntity<>("Destination cannot be empty",
		            HttpStatus.BAD_REQUEST);
		}

		if(passenger.getFrom().equalsIgnoreCase(passenger.getTo())) {
		    return new ResponseEntity<>("Source and destination cannot be same",
		            HttpStatus.BAD_REQUEST);
		}
		int ticketid = map.size()+1;
		Ticket ticket = new Ticket();
		Random random = new Random();

		TicketStatus[] statuses = TicketStatus.values();

		TicketStatus randomStatus = statuses[random.nextInt(statuses.length)];

		ticket.setStatus(randomStatus);
		ticket.setTicketId(ticketid);
		ticket.setFrom(passenger.getFrom());
		ticket.setTo(passenger.getTo());
		ticket.setPrice(2000);

		ticket.setTrainnum("no123");
		map.put(ticketid, ticket);
		
		return new ResponseEntity<>(ticket,HttpStatus.CREATED);
		
	}
	@GetMapping(value="/tickets",
			
			produces= {"application/json","application/xml"}
	)
	public ResponseEntity<Collection<Ticket>> getAllTickets(){
		if(map.isEmpty()) {
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(map.values(),HttpStatus.OK);
	}
	
	@GetMapping("/tickets/{tickid}")
	public ResponseEntity<?> getTicket(@PathVariable Integer tickid){
		if(map.containsKey(tickid)) {
			return new ResponseEntity<>(map.get(tickid),HttpStatus.OK);
		}
		return new ResponseEntity<>("Ticket not aviabale sorry!",HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/tickets/{tickid}/download")
	public ResponseEntity<?> downloadTicket(@PathVariable Integer tickid) {

	    Ticket ticket = map.get(tickid);

	    if (ticket == null) {
	        return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
	    }

	    try {

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	        Document document = new Document();

	        PdfWriter.getInstance(document, outputStream);

	        document.open();

	        document.add(new Paragraph("******** TRAIN TICKET ********"));
	        document.add(new Paragraph(" "));
	        document.add(new Paragraph("Ticket ID : " + ticket.getTicketId()));
	        document.add(new Paragraph("From      : " + ticket.getFrom()));
	        document.add(new Paragraph("To        : " + ticket.getTo()));
	        document.add(new Paragraph("Train No  : " + ticket.getTrainnum()));
	        document.add(new Paragraph("Price     : ₹" + ticket.getPrice()));
	        document.add(new Paragraph("Status    : " + ticket.getStatus()));
	        document.add(new Paragraph(" "));
	        document.add(new Paragraph("Happy Journey!"));

	        document.close();

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=ticket_" + tickid + ".pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(outputStream.toByteArray());

	    } catch (Exception e) {

	        return new ResponseEntity<>("Error while generating PDF",
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	

}
