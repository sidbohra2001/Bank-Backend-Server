package com.cg.bankserver.customerservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private int id;


    private String username;


    private String password;

    private boolean isAdmin = false;

    private String isOauthAccount = "N";

    private String deletedFlag;

    private boolean isAccountExpired = false;

    private boolean isAccountLocked = false;

    private boolean isReadOnlyUser = false;

}
