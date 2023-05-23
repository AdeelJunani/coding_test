package com.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionRequestDTO implements Serializable {

    private  Integer mtn;
    private double amount;
    private String senderFullName;
    private  Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private Boolean issueSolved;
    private String issueMessage;
}
