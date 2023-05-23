package com.smallworld;

import com.dto.TransactionRequestDTO;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount(List<TransactionRequestDTO> transactionRequestDTOList) {

        double sum = 0d;
        for (TransactionRequestDTO transactionRequestDTO : transactionRequestDTOList){
            sum += transactionRequestDTO.getAmount();
        }
        return sum;
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName,List<TransactionRequestDTO> transactionRequestDTOList) {

        double sum = 0d;

        transactionRequestDTOList = transactionRequestDTOList
                .stream()
                .filter( t -> t.getSenderFullName().equals(senderFullName))
                .collect(Collectors.toList());

        if(transactionRequestDTOList.size()>0){

            for (TransactionRequestDTO transactionRequestDTO : transactionRequestDTOList){
                sum += transactionRequestDTO.getAmount();
            }

        }
        return sum;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount(List<TransactionRequestDTO> transactionRequestDTOList) {
        double maxAmount = 0;
        TransactionRequestDTO transactionRequestDTO = transactionRequestDTOList
                .stream()
                .max(Comparator.comparing(TransactionRequestDTO::getAmount))
                .orElseThrow(NoSuchElementException::new);
        maxAmount = transactionRequestDTO.getAmount();
        return maxAmount;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients(List<TransactionRequestDTO> transactionRequestDTOList) {

        Set<Integer> mtn = new HashSet<>();
        long uniqueClients = 0;
        for(TransactionRequestDTO transactionRequestDTO : transactionRequestDTOList){
            mtn.add(transactionRequestDTO.getMtn());
        }
        uniqueClients = mtn.size();
        return uniqueClients;
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName,List<TransactionRequestDTO> transactionRequestDTOList) {

        transactionRequestDTOList = transactionRequestDTOList
                .stream()
                .filter(t -> (t.getSenderFullName().equals(clientFullName) || t.getBeneficiaryFullName().equals(clientFullName))
                                && !t.getIssueSolved())
                .collect(Collectors.toList());

        if(transactionRequestDTOList.size()>0){
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Integer> getTransactionsByBeneficiaryName(List<TransactionRequestDTO> transactionRequestDTOList) {

        Map<String, Integer> getTransactionIndex = new  HashMap<>();

        for(TransactionRequestDTO transactionRequestDTO: transactionRequestDTOList){
            getTransactionIndex.put(transactionRequestDTO.getBeneficiaryFullName(),transactionRequestDTOList.indexOf(transactionRequestDTO));
        }

        return getTransactionIndex;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds(List<TransactionRequestDTO> transactionRequestDTOList) {

        Set<Integer> id = new HashSet<>();
        transactionRequestDTOList = transactionRequestDTOList
                .stream()
                .filter(t -> !t.getIssueSolved())
                .collect(Collectors.toList());
        if(transactionRequestDTOList.size()>0){
            for(TransactionRequestDTO transactionRequestDTO: transactionRequestDTOList){
                id.add(transactionRequestDTO.getMtn());
            }
        }

        return id;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages(List<TransactionRequestDTO> transactionRequestDTOList) {

        List<String> stringList = new ArrayList<>();
        transactionRequestDTOList = transactionRequestDTOList
                .stream()
                .filter(t -> t.getIssueSolved())
                .collect(Collectors.toList());
        if(transactionRequestDTOList.size()>0){
            for(TransactionRequestDTO transactionRequestDTO: transactionRequestDTOList){
                stringList.add(transactionRequestDTO.getIssueMessage());
            }
        }
        return stringList;
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<TransactionRequestDTO> getTop3TransactionsByAmount(List<TransactionRequestDTO> transactionRequestDTOList) {

        transactionRequestDTOList
                .sort(Comparator
                        .comparingDouble(TransactionRequestDTO::getAmount)
                        .reversed()
                );
        transactionRequestDTOList = transactionRequestDTOList
                .stream()
                .limit(3)
                .collect(Collectors.toList());

        return transactionRequestDTOList;
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Map.Entry<String, Double>> getTopSender(List<TransactionRequestDTO> transactionRequestDTOList) {

        Map<String, Double> hm = new HashMap<>();
        for (TransactionRequestDTO transactionRequestDTO : transactionRequestDTOList) {

            String name = transactionRequestDTO.getSenderFullName();
            // If the map already has the pet use the current value, otherwise 0.
            double amount = hm.containsKey(name) ? hm.get(name) : 0;
            amount += transactionRequestDTO.getAmount();

            hm.put(name, amount);
        }
        Optional<Map.Entry<String, Double>> topSender = Optional.of(hm
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .findFirst()
                .get());

        return topSender;
    }

}
