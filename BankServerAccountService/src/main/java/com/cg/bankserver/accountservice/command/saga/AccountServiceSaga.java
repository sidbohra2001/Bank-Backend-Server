package com.cg.bankserver.accountservice.command.saga;

import com.cg.bankserver.accountservice.command.AccountUpdateRollBackedCommand;
import com.cg.bankserver.accountservice.command.event.TransactionEntryFailedEvent;
import com.cg.bankserver.accountservice.command.repository.AccountCommandRepository;
import com.cg.bankserver.accountservice.query.repository.AccountQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class AccountServiceSaga {
    @Autowired transient private CommandGateway commandGateway;
    @Autowired transient private QueryGateway queryGateway;
    @Autowired transient private AccountQueryRepository accountQueryRepository;
    @Autowired transient private AccountCommandRepository accountCommandRepository;
    private static int counter =0;

    @StartSaga
    @SagaEventHandler(associationProperty = "uid")
    public void handleAccountUpdateEvent(TransactionEntryFailedEvent event) {
        try {
            log.info(" Start Saga TransactionEntryFailedCommand  :=   uid  "+event.getUid()  +"  counter   :-  "+ counter++ );
            AccountUpdateRollBackedCommand accountUpdateRollBackedCommand =  AccountUpdateRollBackedCommand.builder()
                    .uid(UUID.randomUUID().toString())
                    .date(event.getDate())
                    .type(event.getType())
                    .amount(event.getAmount())
                    .accountNumber(event.getAccountNumber())
                    .build();
            commandGateway.sendAndWait(accountUpdateRollBackedCommand);

        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    @EndSaga
    public void on() {
        SagaLifecycle.end();
    }



	/*
	private void addTransaction(TransactionDTO transactionDTO)  {

		try {
			String uriEndPoint = "http://BankApp-TransactionService/transaction/addTransactionDetails";
			String jsonData =  new ObjectMapper().writeValueAsString(transactionDTO);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<String>(jsonData,headers);
			restTemplate.postForObject(uriEndPoint, httpEntity, TransactionDTO.class);
		} catch (JsonProcessingException e) {
			log.error(e.toString());
		}

	}*/
}
