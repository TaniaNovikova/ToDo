package de.mydev.app.service;



import de.mydev.app.model.*;

import java.util.List;


public interface IUserService
{
    User saveUser(User user);

    User changeRole(Role newRole, String username);

    User findByUsername(String username);

    List<User> findAllUsers();
}
