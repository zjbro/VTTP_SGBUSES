package vttp2022.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.project.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepo;

    public boolean authenticate(String username, String password) {
        return 1 == usersRepo.countUsersByNameAndPassword(username, password);
    }

}
