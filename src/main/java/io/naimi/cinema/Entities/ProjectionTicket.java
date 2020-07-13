package io.naimi.cinema.Entities;

@org.springframework.data.rest.core.config.Projection(name = "p2",types = Ticket.class)
public interface ProjectionTicket {
    public long getId();
    public Integer getCodePay();
    public double getPrix();
    public boolean getReserved();
    public Place getPlace();
}
