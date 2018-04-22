package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.auth.util.Role;
import co.uk.swarmbit.exception.ValidationException;
import co.uk.swarmbit.repository.UserDataRepository;
import co.uk.swarmbit.repository.UserRepository;
import co.uk.swarmbit.repository.model.User;
import co.uk.swarmbit.repository.model.UserData;
import co.uk.swarmbit.util.EncoderDecoder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserDataRepository userDataRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserDataRepository userDataRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(path = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserShort> getUsers() {
        List<UserShort> userShortList = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            UserShort userShort = new UserShort();
            userShort.setUsername(user.getUsername());
            UserData userData = userDataRepository.findByUsername(user.getUsername());
            userShort.setRole(Role.getRole(user.getRoles()));
            userShort.setDisplayName(userData.getDisplayName());
            userShortList.add(userShort);
        });
        userShortList.sort(Comparator.comparing(UserShort::getUsername));
        return userShortList;
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(path = "{username}/changeRole", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changeRole(@PathVariable String username, @Valid @RequestBody RoleChange roleChange) {
        User user = userRepository.findByUsername(username);
        List<String> roles = Role.getRoles(roleChange.getRole());
        if (roles.isEmpty()) {
            throw new ValidationException("invalid-role", "Invalid role");
        }
        user.setRoles(roles.toArray(new String[]{}));
        userRepository.save(user);
    }

    @RequestMapping(path = "create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CreateUserResponse createUser(@Valid @RequestBody CreateUser createUser) {
        if (userRepository.findByUsername(createUser.getUsername()) != null) {
            throw new ValidationException("exist", "User already exists");
        }

        String resetKey = RandomStringUtils.randomAscii(20);
        User user = new User();
        user.setUsername(createUser.getUsername());
        user.setPassword(passwordEncoder.encode(EncoderDecoder.base64Decode(createUser.password)));
        user.setResetKey(passwordEncoder.encode(resetKey));
        user.setRoles(new String[]{ Role.NONE.toString() });
        userRepository.save(user);

        UserData userData = new UserData();
        userData.setUsername(createUser.getUsername());
        userData.setDisplayName(createUser.getUsername());
        userDataRepository.save(userData);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.resetPasswordKey = EncoderDecoder.base64Encode(resetKey);
        return createUserResponse;
    }

    @RequestMapping(path = "resetPassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        User user = userRepository.findByUsername(resetPassword.getUsername());
        if (userRepository.findByUsername(resetPassword.getUsername()) == null) {
            throw new ValidationException("not-exist", "User does not exist");
        }
        String resetKey = EncoderDecoder.base64Decode(resetPassword.getResetKey());
        if (!passwordEncoder.matches(resetKey, user.getResetKey())) {
            throw new ValidationException("not-match", "Reset key does not match");
        }

        String password = EncoderDecoder.base64Decode(resetPassword.getPassword());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(path = "{username}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void removeUser(@PathVariable String username) {
        userRepository.delete(username);
        userDataRepository.delete(username);
    }

    public static class CreateUser {

        @NotNull
        private String username;

        @NotNull
        @Size(min = 6)
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class ResetPassword {

        @NotNull
        private String username;

        @NotNull
        private String resetKey;

        @NotNull
        @Size(min = 6)
        private String password;

        public String getResetKey() {
            return resetKey;
        }

        public void setResetKey(String resetKey) {
            this.resetKey = resetKey;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class CreateUserResponse {

        private String resetPasswordKey;

        public String getResetPasswordKey() {
            return resetPasswordKey;
        }

        public void setResetPasswordKey(String resetPasswordKey) {
            this.resetPasswordKey = resetPasswordKey;
        }
    }


    public static class UserShort {

        @NotNull
        private String username;

        @NotNull
        private String displayName;

        @NotNull
        private String role;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class RoleChange {

        @NotNull
        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

}
