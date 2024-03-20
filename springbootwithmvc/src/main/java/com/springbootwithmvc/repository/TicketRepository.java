package com.springbootwithmvc.repository;


import com.springbootwithmvc.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}