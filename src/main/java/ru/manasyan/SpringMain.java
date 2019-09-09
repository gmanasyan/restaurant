package ru.manasyan;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.manasyan.web.user.DishRestController;
import ru.manasyan.web.admin.AdminRestController;


import java.time.LocalDate;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            DishRestController dishRestController = appCtx.getBean(DishRestController.class);

            //System.out.println(mealRestController.getAll().toString());
            //System.out.println(mealRestController.save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)));

            //System.out.println("Dish selected: " + dishRestController.get(100005));

            //System.out.println("Today Dishs: " + dishRestController.getAll());
            //System.out.println("16 avg Dishs: " + dishRestController.getAllByDate(LocalDate.of(2019,8,15)));
            //System.out.println("Vote for rest: " + dishRestController.vote(100004));
            //System.out.println("Can vote user?: " + dishRestController.canVote());
        }

    }
}
