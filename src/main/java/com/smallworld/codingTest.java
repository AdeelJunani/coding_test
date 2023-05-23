package com.smallworld;

import com.dto.TransactionRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class codingTest {

    public static void main(String[] args) throws IOException {

        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();

        ObjectMapper objectMapper = new ObjectMapper();
        File transactionFile = new File("transactions.json");
        TransactionRequestDTO[]  transactionRequestDTO = objectMapper.readValue(transactionFile, TransactionRequestDTO[].class);

        List<TransactionRequestDTO> transactionRequestDTOList = Arrays.asList(transactionRequestDTO);

        double getTotalTransactionAmount = transactionDataFetcher.getTotalTransactionAmount(transactionRequestDTOList);
        System.out.println("Sum of the amounts of all transactions: " + getTotalTransactionAmount);

        double getTotalTransactionAmountSentBy = transactionDataFetcher.getTotalTransactionAmountSentBy("Billy Kimber",transactionRequestDTOList);
        System.out.println("Sum of the amounts of all transactions sent by the specified client: " + getTotalTransactionAmountSentBy);

        double getMaxTransactionAmount = transactionDataFetcher.getMaxTransactionAmount(transactionRequestDTOList);
        System.out.println("Highest transaction amount: "+ getMaxTransactionAmount);

        long countUniqueClients = transactionDataFetcher.countUniqueClients(transactionRequestDTOList);
        System.out.println("Number of unique clients that sent or received a transaction: "+ countUniqueClients);

        boolean hasOpenComplianceIssues = transactionDataFetcher.hasOpenComplianceIssues("Billy Kimber",transactionRequestDTOList);
        System.out.println("(Sender or Beneficiary) has at least one transaction with a compliance: "+ hasOpenComplianceIssues);

        Map<String, Integer> getTransactionsByBeneficiaryName = transactionDataFetcher.getTransactionsByBeneficiaryName(transactionRequestDTOList);
        System.out.println("Transactions indexed by beneficiary name: "+getTransactionsByBeneficiaryName);

        Set<Integer> getUnsolvedIssueIds = transactionDataFetcher.getUnsolvedIssueIds(transactionRequestDTOList);
        System.out.println("Identifiers of all open compliance issues: "+getUnsolvedIssueIds);

        List<String> getAllSolvedIssueMessages = transactionDataFetcher.getAllSolvedIssueMessages(transactionRequestDTOList);
        System.out.println("List of all solved issue messages: "+getAllSolvedIssueMessages);

        List<TransactionRequestDTO> getTop3TransactionsByAmount = transactionDataFetcher.getTop3TransactionsByAmount(transactionRequestDTOList);
        System.out.println("3 transactions with highest amount sorted by amount descending: "+getTop3TransactionsByAmount);

        Optional<Map.Entry<String, Double>> getTopSender = transactionDataFetcher.getTopSender(transactionRequestDTOList);
        System.out.println("Sender with the most total sent amount: "+getTopSender);

    }
}
