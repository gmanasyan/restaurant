package ru.manasyan.web.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.manasyan.model.Dish;
import ru.manasyan.repository.JpaDishRepository;

@Controller
public class DishRestController {

    private final JpaDishRepository repository;

    @Autowired
    public DishRestController(JpaDishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int id) {

        //int userId = SecurityUtil.authUserId();

        return repository.get(id, 1);
    }

}
