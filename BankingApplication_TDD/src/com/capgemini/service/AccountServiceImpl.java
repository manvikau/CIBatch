package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	int balance;
	AccountRepository accountRepository;
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}
	
	
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException{
		Account account = accountRepository.searchAccount(accountNumber) ;
		if(account != null){
			
		int finalAmount = account.getAmount()+amount;
		return finalAmount;
		}
		else{
			throw new InvalidAccountNumberException();
		}
			
		}
	
	
	@Override
	public int withdrawAmount(int accountNo,int amount)throws InvalidAccountNumberException,InsufficientBalanceException {
		Account account = accountRepository.searchAccount(accountNo) ;
		if(account != null){
			if(account.getAmount()>=amount){
		int finalAmount = account.getAmount()-amount;
		return finalAmount;
		}else{
			throw new InsufficientBalanceException();
			}}
		else{
			throw new InvalidAccountNumberException();
		}
			
		}
	}

