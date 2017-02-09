package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	
	/*
	 * create account
	 * 1.when the amount is less than 500 system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */

	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
    public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
    {
		accountService.createAccount(101, 400);
    }
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account(101,5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}
	
	/*
	 * deposit
	 * 1.when the account number is invalid system should throw an exception
	 * 2.when the valid info is passed account should be created successfully
	 */
			@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
			public void whenDepositinvalidAccountNumberthrowException() throws InvalidAccountNumberException {
				//Account account = accountservice.createAccount(123, 600);
				
				int accountNumber = 999;
				new Account(123, 600);
				
				when(accountRepository.searchAccount(999)).thenReturn(null);
				
				accountService.depositAmount(accountNumber, 123);
			}
			@Test
			public void whenDepositlGoodReturnFinalAmount() throws InvalidAccountNumberException {
				//Account account = accountservice.createAccount(123, 600);
				
				int accountNumber = 123;
				Account account = new Account(123, 600);
				
				when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
				
				assertEquals(accountService.depositAmount(accountNumber, 500),1100);
			}
	/*
	 * withdraw
	 * 1.when the account number is invalid system should throw an exception
	 * 2.when the balance is not sufficient system should throw an exception
	 * 3..when the valid info is passed account should be created successfully
	 */
			@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
			public void whenWithdrawinvalidAccountNumberthrowException() throws InsufficientBalanceException,InvalidAccountNumberException {
				//Account account = accountservice.createAccount(123, 600);
				
				int accountNumber = 999;
				new Account(123, 600);
				
				when(accountRepository.searchAccount(999)).thenReturn(null);
				
				accountService.withdrawAmount(accountNumber, 123);
				
			}
			
			@Test(expected = com.capgemini.exceptions.InsufficientBalanceException.class)
			public void whenWithdrawInsufficientbalancethrowException() throws InsufficientBalanceException,InvalidAccountNumberException {
				//Account account = accountservice.createAccount(123, 600);
				
				int accountNumber = 123;
				Account account = new Account(123, 200);
				
				when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
				
				accountService.withdrawAmount(accountNumber, 500);
				
			}
			@Test
			public void whenWithdrawallGoodReturnFinalAmount() throws InsufficientBalanceException,InvalidAccountNumberException {
				//Account account = accountservice.createAccount(123, 600);
				
				int accountNumber = 123;
				Account account = new Account(123, 600);
				
				when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
				
				assertEquals(accountService.withdrawAmount(accountNumber, 500),100);
			}
			
}
