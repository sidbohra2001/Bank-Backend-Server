package com.cg.bankserver.customerservice.services;

import com.cg.bankserver.customerservice.dto.CustomerDTO;
import com.cg.bankserver.customerservice.dto.UserDTO;
import com.cg.bankserver.customerservice.exceptions.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import com.cg.bankserver.customerservice.dao.CustomerDAO;
import com.cg.bankserver.customerservice.entities.Customer;
import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class CustomerServiceImpl {
	@Autowired
	private CustomerDAO customerDao;

	public Customer addCustomerDetail(CustomerDTO customerData) {
		Customer customer = new Customer(0, customerData.getFirstName(), customerData.getLastName(), customerData.getUsername());
		UserDTO user = UserDTO.builder()
				.username(customerData.getUsername())
				.password(customerData.getPassword())
				.build();
		saveUserEntity(user);

		return customerDao.save(customer);
	}

	public Customer getCustomerDetail(int customerId) throws CustomerDetailsNotFoundException {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerDetailsNotFoundException("Customer with id :- " + customerId + " does not exists"));
	}

	private UserDTO saveUserEntity(UserDTO user) {

		return WebClient.builder().build().post()
				.uri("http://localhost:9012/auth/add")
				.bodyValue(user)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> response.bodyToMono(String.class).map(ResponseException::new))
				.bodyToMono(UserDTO.class)
				.block();
	}
}
