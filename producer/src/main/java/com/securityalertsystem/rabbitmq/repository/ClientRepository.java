package com.securityalertsystem.rabbitmq.repository;


import com.securityalertsystem.rabbitmq.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {
}
