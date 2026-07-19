package com.example.demo.response;

import com.example.demo.enumm.TicketStatus;

import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Ticket {
	private Integer ticketId;
	private String from;
	private String to;
	private String trainnum;
	private TicketStatus status;
	private Integer price;
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTrainnum() {
		return trainnum;
	}
	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", from=" + from + ", to=" + to + ", trainnum=" + trainnum + ", status="
				+ status + ", price=" + price + "]";
	}
	
	

}
