
package com.swords.model.repository;

import com.swords.model.User;

public interface CustomUserRepository {
    public User queryByNameAndPass(String username, String password);
}
