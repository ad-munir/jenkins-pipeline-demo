package com.clients.demo.controllers;


import com.clients.demo.dto.Client;
import com.clients.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService ;
    @GetMapping("client")
    public List<Client> getAll(){
        return clientService.getAllclients() ;
    }
}
