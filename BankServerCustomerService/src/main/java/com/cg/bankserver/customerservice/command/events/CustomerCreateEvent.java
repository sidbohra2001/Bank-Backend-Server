package com.cg.bankserver.customerservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateEvent {
    private int id;
    private String uid;
    private String firstName;
    private String lastName;
    private String username;
}
