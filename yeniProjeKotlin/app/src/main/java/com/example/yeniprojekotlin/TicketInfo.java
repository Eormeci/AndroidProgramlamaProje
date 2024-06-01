package com.example.yeniprojekotlin;

public class TicketInfo {
    private final String ticketInfo;
    private int quantity;

    public TicketInfo(String ticketInfo, int quantity) {
        this.ticketInfo = ticketInfo;
        this.quantity = quantity;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
