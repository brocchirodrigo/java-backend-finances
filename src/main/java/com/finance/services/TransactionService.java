package com.finance.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.domain.transaction.Transaction;
import com.finance.domain.user.User;
import com.finance.dtos.TransactionDTO;
import com.finance.repositories.TransactionRepository;

@Service
public class TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionRepository repository;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private NotificationService notification;

  public Transaction createTransaction(TransactionDTO transaction) throws Exception {
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());

    this.userService.validateTransaction(sender, transaction.value());

    boolean authorized = this.authorizedTransaction(sender, transaction.value());

    if (!authorized) {
      throw new Exception("Transaction not authorized.");
    }

    Transaction newTransaction = new Transaction();
    newTransaction.setAmount(transaction.value());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));

    this.repository.save(newTransaction);
    this.userService.saveUser(sender);
    this.userService.saveUser(receiver);

    this.notification.sendNotification(sender, "Transação realizada com sucesso.");
    this.notification.sendNotification(receiver, "Transação realizada com sucesso.");

    return newTransaction;
  }

  public boolean authorizedTransaction(User sender, BigDecimal value) {
    ResponseEntity<Map> authorizationResponse = restTemplate
        .getForEntity("https://run.mocky.io/v3/93526ccd-216f-4cc6-b78f-1ec65b056c6f", Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String) authorizationResponse.getBody().get("Message");
      return "Autorizado".equalsIgnoreCase(message);
    } else
      return false;
  }

}
