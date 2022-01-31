package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/pi")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients/currents/cards")
    public List<CardDTO> getCards (Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,@RequestParam CardColor cardColor, Authentication authentication){
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if (client.getCards().stream().filter(card -> card.getType() == cardType).count() >= 3){
            return new ResponseEntity<>("Ha superado el limite de tarjetas"+ cardType+ ".-", HttpStatus.FORBIDDEN);
        }

        String cardNumber = (int)((Math.random() * (9999 -1000)) +1000)+"-"+ (int)((Math.random() * (9999 -1000)) +1000)+"-"+(int)((Math.random() * (9999 -1000)) +1000)+"-"+(int)((Math.random() * (9999 -1000)) +1000);
        int cvv =  (int)((Math.random() * (999 -100)) +100);
        cardRepository.save(new Card(client.getFirstName()+" "+client.getLastName(),cardType,cardColor, cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5),client));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }




}
