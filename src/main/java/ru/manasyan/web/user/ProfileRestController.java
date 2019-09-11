package ru.manasyan.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.manasyan.AuthorizedUser;
import ru.manasyan.model.User;
import ru.manasyan.repository.DataJpaUserRepository;
import ru.manasyan.service.UserService;
import ru.manasyan.web.ExceptionInfoHandler;

import javax.validation.Valid;
import java.net.URI;

import static ru.manasyan.util.ValidationUtil.assureIdConsistent;
import static ru.manasyan.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {

    static final String REST_URL = "/rest/profile";

    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("Register new user {} ", user.toString());
        checkNew(user);
        User created = userService.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("View user {} info ", authUser.getId());
        return userService.get(authUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Update user {} with info {}", authUser.getId(), user.toString());
        assureIdConsistent(user, authUser.getId());
        userService.update(user);
    }

}
