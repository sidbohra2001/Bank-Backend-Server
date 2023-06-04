package com.cg.bankserver.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;

    private String password;

    private boolean isAdmin;

    private String isOauthAccount;

    private String deletedFlag;

    private boolean isAccountExpired;

    private boolean isAccountLocked;

    private boolean isReadOnlyUser;

//    @Override
//    public String toString() {
//        if (isAdmin) {
//            return "User [id=" + id + ", uname=" + username + ", Admin User]";
//        } else {
//            return "User [id=" + id + ", uname=" + username + "]";
//        }
//    }
}
