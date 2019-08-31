package ru.manasyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.manasyan.AuthorizedUser;
import ru.manasyan.model.User;
import ru.manasyan.repository.CrudUserRepository;
import ru.manasyan.repository.DataJpaUserRepository;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    @Autowired
    private DataJpaUserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User get(int id) {
        return repository.get(id);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        //return new AuthorizedUser(user)
        return new AuthorizedUser(user);
    }
}
