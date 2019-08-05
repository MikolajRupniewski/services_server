package pl.rupniewski.service_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.rupniewski.service_server.repository.*;

public abstract class BaseController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Autowired
    EnabledUsersRepository enabledUsersRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ServiceRepository serviceRepository;
}
