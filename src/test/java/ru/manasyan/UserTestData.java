package ru.manasyan;

import ru.manasyan.model.Role;
import ru.manasyan.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.manasyan.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int ADMIN_ID = START_SEQ;
    public static final int USER1_ID = START_SEQ + 1;
    public static final int USER2_ID = START_SEQ + 2;
    public static final int USER3_ID = START_SEQ + 3;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User USER1 = new User(USER1_ID, "User1", "user1@gmail.com", "password", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@gmail.com", "password", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@gmail.com", "password", Role.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

}
