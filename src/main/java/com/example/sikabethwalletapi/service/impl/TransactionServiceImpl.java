package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.enums.TransactionType;
import com.example.sikabethwalletapi.exception.WalletException;
import com.example.sikabethwalletapi.model.Transaction;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.pojo.wallet.response.WalletTransactionResponse;
import com.example.sikabethwalletapi.repository.TransactionRepository;
import com.example.sikabethwalletapi.service.TransactionService;
import com.example.sikabethwalletapi.util.AuthDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 27/02/2023 - 02:08
 * @project SikabethWalletAPI
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AuthDetails authDetails;

    @Override
    public WalletTransactionResponse fetchTransaction(Principal principal, String id) {
        User user = authDetails.validateActiveUser(principal);
        Transaction transaction = transactionRepository.findByUserUuidAndUuidOrTransactionReference(user.getUuid(), id, id).
                orElseThrow(() -> new WalletException(""));
        return WalletTransactionResponse.mapFromTransaction(transaction);
    }

    @Override
    public List<WalletTransactionResponse> fetchTransactions(Principal principal, int page, int limit) {
        User user = authDetails.validateActiveUser(principal);

        Sort sort = Sort.by(Sort.Direction.DESC, "amount");
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Transaction> pagedTransactions = transactionRepository.findAllByUserUuid(user.getUuid(), pageable);

        List<Transaction> transactions = pagedTransactions.getContent();
        return WalletTransactionResponse.mapFromTransaction(transactions);
    }

    @Override
    public List<WalletTransactionResponse> fetchTransactionsByAdmin(Principal principal, int page, int limit) {
        User user = authDetails.validateActiveUser(principal);
        log.info(user.getUuid());


        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);
        Page<Transaction> pagedTransactions = transactionRepository.findAll(pageable);

        List<Transaction> transactions = pagedTransactions.getContent();
        return WalletTransactionResponse.mapFromTransaction(transactions);
    }


    @Override
    public List<WalletTransactionResponse> fetchTransactions(Principal principal, int page, int limit, String transactionType) {
        User user = authDetails.validateActiveUser(principal);

        TransactionType type = TransactionType.DEBIT;
        if (TransactionType.CREDIT.name().equalsIgnoreCase(transactionType))
            type = TransactionType.CREDIT;

        if (page > 0) page = page - 1;

        Sort sort = Sort.by(Sort.Direction.DESC, "amount");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Transaction> pagedTransactions = transactionRepository.findAllByUserUuidAndTransactionType(
                user.getUuid(), type, pageable);

        List<Transaction> transactions = pagedTransactions.getContent();
        return WalletTransactionResponse.mapFromTransaction(transactions);
    }
}
