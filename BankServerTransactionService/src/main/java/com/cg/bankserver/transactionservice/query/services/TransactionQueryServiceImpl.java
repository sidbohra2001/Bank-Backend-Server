package com.cg.bankserver.transactionservice.query.services;

import java.util.List;
import java.util.Optional;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cg.bankserver.transactionservice.dto.AccountDTO;
import com.cg.bankserver.transactionservice.exceptions.BankingServicesException;
import com.cg.bankserver.transactionservice.exceptions.TransactionDetailsNotFoundException;
import com.cg.bankserver.transactionservice.query.GetTransactionByIdQuery;
import com.cg.bankserver.transactionservice.query.GetTransactionsByAccountNumberQuery;
import com.cg.bankserver.transactionservice.query.entities.Transaction;

@Component
public class TransactionQueryServiceImpl implements TransactionQueryService {

	@Autowired
	private QueryGateway queryGateway;
	@Autowired
	private WebClient.Builder builder;

	@Override
	public Transaction getTransactionById(int id) throws TransactionDetailsNotFoundException {
		return Optional
				.ofNullable(queryGateway
						.query(new GetTransactionByIdQuery(id), ResponseTypes.instanceOf(Transaction.class)).join())
				.orElseThrow(() -> new TransactionDetailsNotFoundException(
						"Transactoin Details Not found for Id :-  " + id));
	}

	@Override
	public List<Transaction> getTransactionByAccountNumber(int accNo) throws BankingServicesException {
//		if (checkAccountNumber(accNo) == false)
//			throw new BankingServicesException("Invalid Account Number : " + accNo);
		return queryGateway.query(new GetTransactionsByAccountNumberQuery(accNo),
				ResponseTypes.multipleInstancesOf(Transaction.class)).join();
	}

	private boolean checkAccountNumber(int accNo) throws BankingServicesException {
		try {
			var client = builder.baseUrl("http://BANK-SERVER-ACCOUNT-SERVICE").build();
			if (client.get().uri("/account/getAccountDetails?accountNumber=" + accNo).accept(MediaType.APPLICATION_JSON)
					.retrieve().bodyToMono(AccountDTO.class).block() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new BankingServicesException("Account with account number : "+accNo+" does not exist.", e.getCause());
		}

	}
}
