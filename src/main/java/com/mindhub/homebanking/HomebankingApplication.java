package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {


            Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
            Client client2 = new Client("Andres", "Oyarzun", "andres@gmail.com", passwordEncoder.encode("1234"));
            clientRepository.save(client1);
            clientRepository.save(client2);

            Account account1 = new Account("VIN001", LocalDateTime.now(), 500000, client1);
            Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 375000, client1);
            Account account3 = new Account("VIN003", LocalDateTime.now(), 1500000, client2);
            Account account4 = new Account("VIN004", LocalDateTime.now(), 80000, client2);
            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
            accountRepository.save(account4);

            Transaction transaction1 = new Transaction(TransactionType.CREDIT, 1000, "Compra de Pan panaderia XXX", LocalDateTime.now(), account1);
            Transaction transaction2 = new Transaction(TransactionType.DEBIT, -55000, "Compra de ropa tienda XXX", LocalDateTime.now(), account1);
            Transaction transaction3 = new Transaction(TransactionType.CREDIT, 7500, "Compra de dulces panaderia XXX", LocalDateTime.now(), account2);
            Transaction transaction4 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account2);
            Transaction transaction5 = new Transaction(TransactionType.CREDIT, 1000, "Pago  Energia Electrica xxx", LocalDateTime.now(), account2);
            Transaction transaction6 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account1);
            Transaction transaction7 = new Transaction(TransactionType.DEBIT, -1000, "Pago Agua xxxx", LocalDateTime.now(), account2);
            Transaction transaction8 = new Transaction(TransactionType.CREDIT, 1000, "Compra Supermercado XXX", LocalDateTime.now(), account2);
            Transaction transaction9 = new Transaction(TransactionType.DEBIT, -1000, "Pago gas xxx", LocalDateTime.now(), account2);
            Transaction transaction10 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account1);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            transactionRepository.save(transaction3);
            transactionRepository.save(transaction4);
            transactionRepository.save(transaction5);
            transactionRepository.save(transaction6);
            transactionRepository.save(transaction7);
            transactionRepository.save(transaction8);
            transactionRepository.save(transaction9);
            transactionRepository.save(transaction10);

            Loan loan1 = new Loan("Hipotecario", 500000, Arrays.asList(2, 24, 36, 48, 60));
            Loan loan2 = new Loan("Personal", 100.000, Arrays.asList(6, 12, 24));
            Loan loan3 = new Loan("Automotriz", 300.000, Arrays.asList(6, 12, 24, 36));
            loanRepository.save(loan1);
            loanRepository.save(loan2);
            loanRepository.save(loan3);

            ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
            ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);
            ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan2);
            ClientLoan clientLoan4 = new ClientLoan(200000, 36, client2, loan3);

            clientLoanRepository.save(clientLoan1);
            clientLoanRepository.save(clientLoan2);
            clientLoanRepository.save(clientLoan3);
            clientLoanRepository.save(clientLoan4);

            Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "5138-2923-8855-0763", 890, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
            Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "4082-6771-1217-9310", 819, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
            Card card3 = new Card(client2.getFirstName() + " " + client2.getLastName(), CardType.CREDIT, CardColor.SILVER, "3558-1230-4376-2970", 768, LocalDateTime.now(), LocalDateTime.now().plusYears(7), client2);
            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);
        };
    }
}
