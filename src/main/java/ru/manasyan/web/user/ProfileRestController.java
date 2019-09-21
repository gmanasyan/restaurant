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
import ru.manasyan.model.Role;
import ru.manasyan.model.User;
import ru.manasyan.service.UserService;
import ru.manasyan.to.UserToIn;
import ru.manasyan.web.ExceptionInfoHandler;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;

import static ru.manasyan.model.Role.ROLE_USER;
import static ru.manasyan.util.Util.updateFromTo;
import static ru.manasyan.util.ValidationUtil.assureIdConsistent;
import static ru.manasyan.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {

    static final String REST_URL = "/rest/profile";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("Register new user {} ", user.toString());
        checkNew(user);
        user.setRoles(EnumSet.of(Role.ROLE_USER));
        user.setRegistered(LocalDateTime.now());

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
    public void update(@Valid @RequestBody UserToIn userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("Update user {} with info {}", authUser.getId(), userTo.toString());
        assureIdConsistent(userTo, authUser.getId());
        userService.update(updateFromTo(userTo, userService.get(authUser.getId())));
    }

}
