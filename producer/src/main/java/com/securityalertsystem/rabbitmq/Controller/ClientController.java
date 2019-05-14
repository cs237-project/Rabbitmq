package com.securityalertsystem.rabbitmq.Controller;

import com.securityalertsystem.rabbitmq.entity.Client;
import com.securityalertsystem.rabbitmq.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping(value = "/getClients" )
    public List<Client> getClientList(){
        return clientRepository.findAll();
    }

    @PostMapping(value = "/addClients")
    public void addCli(@RequestParam("number") int num){
        for(int i=0;i<num;i++){
            Client cli=new Client();

            cli.setLocationx(50+Math.random()*30);
            cli.setLocationy(50+Math.random()*30);

            cli.setAddressx(51+Math.random()*30);
            cli.setAddressy(51+Math.random()*30);

            clientRepository.save(cli);
        }
    }

}
