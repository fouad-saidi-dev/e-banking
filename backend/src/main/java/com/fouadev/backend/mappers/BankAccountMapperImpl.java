package com.fouadev.backend.mappers;
/*
 Created by : Fouad SAIDI on 24/05/2024
 @author : Fouad SAIDI
 @date : 24/05/2024
 @project : e-banking
*/

import com.fouadev.backend.dtos.*;
import com.fouadev.backend.entities.*;
import com.fouadev.backend.security.requests.AppUserRequest;
import com.fouadev.backend.security.responses.AppUserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO accountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(accountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(accountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO accountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,accountDTO);
        accountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        accountDTO.setType(savingAccount.getClass().getSimpleName());
        return accountDTO;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
    public AppUser fromAppUserDTO(AppUserDTO appUserDTO){
        AppUser user = new AppUser();
        BeanUtils.copyProperties(appUserDTO,user);
        return user;
    }
    public AppUserDTO fromAppUser(AppUser appUser){
        AppUserDTO userDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,userDTO);
        return userDTO;
    }
    public AppRole fromAppRoleDTO(AppRoleDTO appRoleDTO){
        AppRole role = new AppRole();
        BeanUtils.copyProperties(appRoleDTO,role);
        return role;
    }
    public AppRoleDTO fromAppRole(AppRole appRole){
        AppRoleDTO appRoleDTO = new AppRoleDTO();
        BeanUtils.copyProperties(appRole,appRoleDTO);
        return appRoleDTO;
    }
    public AppUserDTO fromAppUserRequest(AppUserRequest userRequest){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setPassword(userRequest.getPassword());
        appUserDTO.setUsername(userRequest.getUsername());
        appUserDTO.setEmail(userRequest.getEmail());
        return appUserDTO;
    }
    public AppUserResponse fromAppUserDTOResponse(AppUserDTO appUserDTO){
        AppUserResponse userResponse = new AppUserResponse();
        userResponse.setUsername(appUserDTO.getUsername());
        userResponse.setEmail(appUserDTO.getEmail());
        return userResponse;
    }
}