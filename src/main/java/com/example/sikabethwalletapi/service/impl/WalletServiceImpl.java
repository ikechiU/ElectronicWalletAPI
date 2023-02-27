package com.example.sikabethwalletapi.service.impl;

import com.example.sikabethwalletapi.enums.TransactionSource;
import com.example.sikabethwalletapi.enums.TransactionType;
import com.example.sikabethwalletapi.enums.VerificationStatus;
import com.example.sikabethwalletapi.exception.WalletException;
import com.example.sikabethwalletapi.model.Payment;
import com.example.sikabethwalletapi.model.Transaction;
import com.example.sikabethwalletapi.model.User;
import com.example.sikabethwalletapi.model.Wallet;
import com.example.sikabethwalletapi.pojo.paystack.request.SetUpTransactionRequest;
import com.example.sikabethwalletapi.pojo.paystack.response.CustomerValidationResponse;
import com.example.sikabethwalletapi.pojo.paystack.response.SetUpTransactionResponse;
import com.example.sikabethwalletapi.pojo.paystack.response.VerifyPaymentResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.InitiateTransferFromSikabethToWalletRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.PinResetRequest;
import com.example.sikabethwalletapi.pojo.wallet.request.SikabethWalletResponse;
import com.example.sikabethwalletapi.pojo.wallet.request.WalletValidationRequest;
import com.example.sikabethwalletapi.pojo.wallet.response.WalletResponse;
import com.example.sikabethwalletapi.repository.PaymentRepository;
import com.example.sikabethwalletapi.repository.TransactionRepository;
import com.example.sikabethwalletapi.repository.WalletRepository;
import com.example.sikabethwalletapi.service.PaymentService;
import com.example.sikabethwalletapi.service.WalletService;
import com.example.sikabethwalletapi.util.AmazonSES;
import com.example.sikabethwalletapi.util.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * @author Ikechi Ucheagwu
 * @created 24/02/2023 - 18:39
 * @project SikabethWalletAPI
 */

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final AuthDetails authDetails;
    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder encoder;
    private final PaymentService paymentService;
    private final WalletChecker walletChecker;
    private final AmazonSES amazonSES;


    @Override
    public WalletResponse getWallet(Principal principal) {
        return WalletResponse.mapFromWallet(getUserWallet(principal));
    }

    @Override
    public List<WalletResponse> getWallets(Principal principal, int page, int limit) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);
        Page<Wallet> pagedWallets = walletRepository.findAll(pageable);
        List<Wallet> wallets = pagedWallets.getContent();
        return WalletResponse.mapFromWallet(wallets);
    }

    @Override
    public WalletResponse transferMoneyWalletToWallet(Principal principal, String recipientWalletId, String amount, String reason, String pin) {
        Wallet wallet = checksBeforeTransaction(principal);
        Wallet recipientWallet = findWallet(recipientWalletId);

        if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) <= 0) {
            throw new WalletException("Amount is less or equal than 0");
        }

        BigDecimal newUserBalance = wallet.getBalance().subtract(new BigDecimal(amount));
        BigDecimal newRecipientBalance = recipientWallet.getBalance().add(new BigDecimal(amount));

        if (newUserBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new WalletException("Insufficient balance");
        }

        //CHECK PIN
        if (!encoder.matches(pin, wallet.getPin())) {
            throw new WalletException("Pin did not match");
        }

        wallet.setBalance(newUserBalance);
        Wallet updatedUserWallet = walletRepository.save(wallet);

        recipientWallet.setBalance(newRecipientBalance);
        walletRepository.save(recipientWallet);

        Transaction userTransaction = createTransaction(wallet.getUserUuid(), reason, wallet.getWalletId(), recipientWalletId,
                new BigDecimal(amount), newUserBalance, TransactionType.DEBIT, TransactionSource.WALLET);
        transactionRepository.save(userTransaction);

        Transaction recipientTransaction = createTransaction(recipientWallet.getUserUuid(), reason, wallet.getWalletId(),
                recipientWalletId, new BigDecimal(amount), newRecipientBalance, TransactionType.CREDIT, TransactionSource.WALLET);
        transactionRepository.save(recipientTransaction);

        //SEND TWO PEOPLE SMS
        String userMessage = "<div>You transferred from your " + wallet.getWalletId() +
                " to " + recipientWallet.getWalletId() + ". Your account balance is " + newUserBalance + ".</div>";
        amazonSES.sendTransaction(userMessage, wallet.getEmail());

        String recipientMessage = "<div>" + wallet.getEmail() + " transferred from " + wallet.getWalletId() +
                " to your account and your new balance is " + newRecipientBalance + ".</div>";
        amazonSES.sendTransaction(recipientMessage, recipientWallet.getEmail());

        WalletResponse walletResponse = WalletResponse.mapFromWallet(updatedUserWallet);
        walletResponse.setTransactionReference(userTransaction.getTransactionReference());
        return walletResponse;
    }

    @Override
    public WalletResponse payService(Principal principal, String serviceName, BigDecimal amount, String pin) {
        Wallet wallet = checksBeforeTransaction(principal);

        return null;
    }

    @Override
    public String validateWallet(Principal principal, WalletValidationRequest request) {
        Wallet wallet = getUserWallet(principal);

        //N/B: THE VALIDATION OUGHT TO BE WITH CURRENT USER'S INFORMATION OR RATHER COLLECT ONLY BVN, NIN
        //AND BANK DETAILS THEN DO A VALIDATION COMPARE IF NAME, PHONE AND OTHER DATA THEY PRESENTED MATCHES
        //I.E WHAT HAS BEEN STORED IN DATABASE. ANOTHER EXTRA LAYER WOULD BE TO COLLECT FINGERPRINTS
        //AND PICTURE OF USE AND COMPARE WITH BVN AND NIN VALIDATION
        CustomerValidationResponse response = paymentService.validateCustomer(request);
        if (response.isStatus() && response.getMessage().equals("Customer Identification in progress")) {
            wallet.setVerificationStatus(VerificationStatus.CONFIRMED);
            wallet.setVerified(true);
            walletRepository.save(wallet);
        }
        return response.getMessage();
    }

    @Override
    public String resetPin(Principal principal, PinResetRequest request) {
        Wallet wallet = getUserWallet(principal);
        boolean oldPinMatch = encoder.matches(request.getOldPin(), wallet.getPin());
        if (!oldPinMatch) throw new WalletException("Old pin does not match existing pin in records");

        boolean newPinMatch = request.getNewPin().equals(request.getConfirmNewPin());
        if (!newPinMatch) throw new WalletException("New pin and confirm new pin does not match");

        wallet.setPin(encoder.encode(request.getNewPin()));
        walletRepository.save(wallet);

        return "Pin change successful";
    }

    @Override
    public SikabethWalletResponse initializeTransaction(Principal principal, InitiateTransferFromSikabethToWalletRequest request) {
        User user = checksBeforeSikabethToWalletTransfer(principal);

        SetUpTransactionRequest setUpTransactionRequest = InitiateTransferFromSikabethToWalletRequest
                .mapToSetUpTransactionRequest(request.getAmount(), user.getEmail());

        SetUpTransactionResponse response = paymentService.initializeTransaction(principal, setUpTransactionRequest);
        if (!response.isStatus()) {
            throw new WalletException("Transaction initiation failed, try again");
        }

        String transfer_code = String.valueOf(System.currentTimeMillis());

        SikabethWalletResponse result = SikabethWalletResponse.mapFromSetUpTransactionResponse(
                response.isStatus(), response.getMessage(), user.getEmail(), "Sikabeth",
                new BigDecimal(request.getAmount()), user.getWalletId(), transfer_code, response.getData().getReference());

        Payment payment = createPayment(result);
        paymentRepository.save(payment);

        return result;
    }

    @Override
    public WalletResponse verifyTransaction(Principal principal, String reference, String transfer_code) {
        User user = checksBeforeSikabethToWalletTransfer(principal);
        Payment payment = paymentRepository.findByTransferCode(transfer_code)
                .orElseThrow(() -> new WalletException("Payment does not exist"));

        VerifyPaymentResponse response = paymentService.verifyTransaction(reference);
        if (!response.isStatus()) {
            throw new WalletException("Verification failed try again");
        }

        payment.setConfirmed(true);
        paymentRepository.save(payment);

        Wallet wallet = findWallet(user);
        BigDecimal fromSikabeth = new BigDecimal(response.getData().getAmount());
        BigDecimal balance = findWallet(user).getBalance().add(fromSikabeth);

        Transaction transaction = createTransaction(user.getUuid(), "Sikabeth transfer to your wallet.", "Sikabeth",
                wallet.getWalletId(), fromSikabeth, balance, TransactionType.CREDIT, TransactionSource.BANK);
        transactionRepository.save(transaction);

        wallet.setBalance(balance);
        Wallet updatedWallet = walletRepository.save(wallet);

        //SEND USER EMAIL NOTIFICATION
        String message = "<div>Sikabeth transferred " + fromSikabeth +
                " to your account and your new balance is " + updatedWallet + ".</div>";
        amazonSES.sendTransaction(message, user.getEmail());

        WalletResponse walletResponse = WalletResponse.mapFromWallet(updatedWallet);
        walletResponse.setTransactionReference(transaction.getTransactionReference());
        return walletResponse;
    }

    private Wallet getUserWallet(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        return findWallet(user);
    }

    private Wallet checksBeforeTransaction(Principal principal) {
        Wallet wallet = getUserWallet(principal);
        walletChecker.checksBeforeTransaction(wallet, encoder);
        return wallet;
    }

    private User checksBeforeSikabethToWalletTransfer(Principal principal) {
        User user = authDetails.validateActiveUser(principal);
        Wallet wallet = findWallet(user);
        walletChecker.checksBeforeTransaction(wallet, encoder);
        return user;
    }

    private Wallet findWallet(User user) {
        return walletRepository.findByWalletId(user.getWalletId())
                .orElseThrow(() -> new WalletException("Wallet does not exist."));
    }

    private Wallet findWallet(String walletId) {
        return walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new WalletException("The recipient Wallet does not exist."));
    }

    private Payment createPayment(SikabethWalletResponse response) {
        return Payment.builder()
                .uuid(UUID.randomUUID().toString())
                .walletId(response.getWalletId())
                .to(response.getTo())
                .from(response.getFrom())
                .amount(response.getAmount())
                .confirmed(false)
                .reference(response.getReference())
                .transferCode(response.getTransfer_code())
                .build();
    }

    private Transaction createTransaction(
            String userUuid,
            String reason,
            String from,
            String to,
            BigDecimal amount,
            BigDecimal balance,
            TransactionType transactionType,
            TransactionSource transactionSource
    ) {
        return Transaction.builder()
                .userUuid(userUuid)
                .uuid(UUID.randomUUID().toString())
                .reason(reason)
                .from(from)
                .to(to)
                .amount(amount)
                .balance(balance)
                .transactionReference(String.valueOf(System.currentTimeMillis()))
                .transactionType(transactionType)
                .transactionSource(transactionSource)
                .build();
    }


}
