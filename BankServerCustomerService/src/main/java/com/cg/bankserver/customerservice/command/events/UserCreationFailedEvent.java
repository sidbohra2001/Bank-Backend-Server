package com.cg.bankserver.customerservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationFailedEvent {
    private String uid;
    private int id;
    private String username;
    private String password;
}
