package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.manasyan.model.User;

@Repository
public class DataJpaUserRepository {

    @Autowired
    private CrudUserRepository crudUserRepository;

    public User save(User user) {
        return crudUserRepository.save(user);
    }

    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

}
