package ru.manasyan.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.manasyan.model.Dish;
import ru.manasyan.repository.DataJpaDishRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DishRestController {

    @Autowired
    private DataJpaDishRepository repository;

//    public Dish get(int id) {
//        //int userId = SecurityUtil.authUserId();
//        return repository.get(id, 1);
//    }

    public List<Dish> getAll() {
        return repository.getAll(LocalDate.now());
    }

    public List<Dish> getAllByDate(LocalDate date) {
        return repository.getAll(date);
    }



}
