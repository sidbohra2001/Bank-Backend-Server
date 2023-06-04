package com.cg.bankserver.customerservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CustomerCreateCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int id;
    private String firstName;
    private String lastName;
    private String username;
}
