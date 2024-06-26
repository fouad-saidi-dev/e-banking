package com.fouadev.backend.web;

import com.fouadev.backend.dtos.*;
import com.fouadev.backend.entities.BankAccount;
import com.fouadev.backend.exceptions.BalanceNotSufficientException;
import com.fouadev.backend.exceptions.BankAccountNotFoundException;
import com.fouadev.backend.exceptions.CustomerNotFoundException;
import com.fouadev.backend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping(path = "/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping
    public List<BankAccountDTO> bankAccountList() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                                     @RequestParam(name = "page",defaultValue = "0") int page,
                                                     @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
       return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/debit")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO,Principal principal) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(), debitDTO.getDescription(), principal.getName());
        return debitDTO;
    }
    @PostMapping("/credit")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO, Principal principal) throws BankAccountNotFoundException {
        bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription(), principal.getName());
        return creditDTO;
    }
    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public TransferDTO transfer(@RequestBody TransferDTO transferDTO,Principal principal) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.transfer(transferDTO.getAccountIdSource(),transferDTO.getAccountIdDestination(),transferDTO.getAmount(), principal.getName());
        return transferDTO;
    }
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<BankAccountDTO> bankAccountListCustomer(@PathVariable Long customerId) {
        return bankAccountService.getAccountsCustomer(customerId);
    }
    @GetMapping("/operations")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<AccountOperationDTO> accountOperations(){
        return bankAccountService.accountOperationsList();
    }

    @PostMapping("/addSavingAccount")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public SavingBankAccountDTO addSavingAccount(@RequestBody SavingBankAccountDTO accountDTO,Principal principal) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(accountDTO,principal.getName());
    }
    @PostMapping("/addCurrentAccount")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CurrentBankAccountDTO addCurrentAccount(@RequestBody CurrentBankAccountDTO accountDTO,Principal principal) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(accountDTO,principal.getName());
    }
    @GetMapping("/pagination")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public AccountPageDTO accounts(@RequestParam(name = "keyword",defaultValue = "") String keyword,
                                   @RequestParam(name = "page",defaultValue = "0") int page,
                                   @RequestParam(name = "size",defaultValue = "5") int size){
        return bankAccountService.getAccounts("%"+keyword+"%",page,size);
    }
}