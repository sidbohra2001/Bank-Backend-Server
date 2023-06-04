package com.cg.bankserver.customerservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UserCreationFailedCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int id;
    private String username;
    private String password;
}
