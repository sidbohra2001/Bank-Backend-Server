package com.cg.bankserver.customerservice.command.saga;

import com.cg.bankserver.customerservice.command.CustomerCreateRollBackCommand;
import com.cg.bankserver.customerservice.command.events.UserCreationFailedEvent;
import com.cg.bankserver.customerservice.command.repositories.CustomerCommandRepository;
import com.cg.bankserver.customerservice.query.repositories.CustomerQueryRepository;
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
public class CustomerServiceSaga {
    @Autowired transient private CommandGateway commandGateway;
    @Autowired transient private QueryGateway queryGateway;
    @Autowired transient private CustomerQueryRepository accountQueryRepository;
    @Autowired transient private CustomerCommandRepository accountCommandRepository;
    private static int counter =0;

    @StartSaga
    @SagaEventHandler(associationProperty = "uid")
    public void handleAccountUpdateEvent(UserCreationFailedEvent event) {
        try {
            log.info(" Start Saga TransactionEntryFailedCommand  :=   uid  "+event.getUid()  +"  counter   :-  "+ counter++ );
            CustomerCreateRollBackCommand accountUpdateRollBackedCommand =  CustomerCreateRollBackCommand.builder()
                    .uid(UUID.randomUUID().toString())
                    .id(event.getId())
                    .username(event.getUsername())
                    .password(event.getPassword())
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
