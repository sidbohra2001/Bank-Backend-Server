package com.cg.bankserver.customerservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreateRollBackCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int id;
    private String username;
    private String password;
}
