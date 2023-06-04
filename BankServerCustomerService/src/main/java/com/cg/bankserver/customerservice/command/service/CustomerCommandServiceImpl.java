package com.cg.bankserver.customerservice.command.service;

import com.cg.bankserver.customerservice.command.CustomerCreateCommand;
import com.cg.bankserver.customerservice.command.CustomerDeleteCommand;
import com.cg.bankserver.customerservice.command.CustomerUpdateCommand;
import com.cg.bankserver.customerservice.command.UserCreationFailedCommand;
import com.cg.bankserver.customerservice.command.entities.Customer;
import com.cg.bankserver.customerservice.command.repositories.CustomerCommandRepository;
import com.cg.bankserver.customerservice.dto.CustomerDTO;
import com.cg.bankserver.customerservice.dto.UserDTO;
import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.exceptions.ResponseException;
import com.cg.bankserver.customerservice.exceptions.UserCreationException;
import com.cg.bankserver.customerservice.query.repositories.CustomerQueryRepository;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
@Slf4j
public class CustomerCommandServiceImpl implements CustomerCommandService{

    @Autowired private CommandGateway commandGateway;
    
    @Autowired private CustomerQueryRepository qRepo;

    @Autowired private CustomerCommandRepository repository;

    @Autowired private WebClient.Builder webClientBuilder;

    @Override
    public Customer addCustomerDetails(CustomerDTO customerData) throws UserCreationException {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerData, customer);
        Customer responseCustomer = repository.save(customer);
        UserDTO user = UserDTO.builder()
                .id(responseCustomer.getId())
                .username(customerData.getUsername())
                .password(customerData.getPassword())
                .build();
        saveUserEntity(user);
        CustomerCreateCommand customerCommand = CustomerCreateCommand.builder()
                .id(responseCustomer.getId())
                .uid(UUID.randomUUID().toString())
                .firstName(responseCustomer.getFirstName())
                .lastName(responseCustomer.getLastName())
                .username(responseCustomer.getUsername())
                .build();
        log.info("Customer create command service : - " + customerCommand);
        commandGateway.sendAndWait(customerCommand);
        log.info("Saved Customer : " + responseCustomer);
        return responseCustomer;
    }

    @Override
    public Customer updateCustomerDetails(CustomerDTO customerData) throws CustomerDetailsNotFoundException {
        UserDTO user = UserDTO.builder()
                .username(customerData.getUsername())
                .password(customerData.getPassword())
                .build();
        Customer customer = new Customer();
        qRepo.findById(customerData.getId()).orElseThrow(()->new CustomerDetailsNotFoundException("Customer not found with customer id : "+customerData.getId()));
        BeanUtils.copyProperties(customerData, customer);
        log.info("Updating customer: " + customer.getId());
        customer = repository.save(customer);
        CustomerUpdateCommand customerCommand = CustomerUpdateCommand.builder()
                .id(customer.getId())
                .uid(UUID.randomUUID().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .build();
        commandGateway.sendAndWait(customerCommand);
        return customer;
    }

    @Override
    public void deleteCustomerDetails(int id) throws CustomerDetailsNotFoundException {
        qRepo.findById(id).orElseThrow(()->new CustomerDetailsNotFoundException("Customer not found with customer id : "+id));
        repository.deleteById(id);
        CustomerDeleteCommand customerCommand = CustomerDeleteCommand.builder()
                .id(id)
                .uid(UUID.randomUUID().toString())
                .build();
        commandGateway.sendAndWait(customerCommand);
    }

    private UserDTO saveUserEntity(UserDTO user) throws UserCreationException {

        try {
            return webClientBuilder.build().post()
                    .uri("http://AUTH-SERVER/auth/add")
                    .bodyValue(user)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class).map(ResponseException::new))
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (Exception e) {
            var userCreationFailed = UserCreationFailedCommand.builder()
                    .uid(UUID.randomUUID().toString())
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
            commandGateway.sendAndWait(userCreationFailed);
            throw new UserCreationException(e.getMessage());
        }
    }
}