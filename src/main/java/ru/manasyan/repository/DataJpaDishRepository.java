package ru.manasyan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.manasyan.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaDishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    public List<Dish> getAll(LocalDate date) {
        return crudDishRepository.getAll(date);
    }

//    public Dish get() {
//        return crudDishRepository.getOne();
//    }


}
