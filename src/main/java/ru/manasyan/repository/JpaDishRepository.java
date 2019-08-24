package ru.manasyan.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class JpaDishRepository {

    @PersistenceContext
    private EntityManager em;

    public Dish get(int id, int userId) {
        Dish dish = em.find(Dish.class, id);
       // return ((dish != null) &&  (dish.getUser().getId() == userId)) ?  meal : null;
        return dish;
    }


}
