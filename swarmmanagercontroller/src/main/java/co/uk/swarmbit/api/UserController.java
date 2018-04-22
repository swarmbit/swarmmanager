package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.exception.ValidationException;
import co.uk.swarmbit.repository.UserRepository;
import co.uk.swarmbit.repository.model.UserData;
import co.uk.swarmbit.repository.model.User;
import co.uk.swarmbit.repository.UserDataRepository;
import co.uk.swarmbit.util.EncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static co.uk.swarmbit.util.UserUtil.getCurrentUsername;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDataRepository userDataRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDataRepository userDataRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize(RoleAuthorities.IS_NONE)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserData getUserData() {
        User user = userRepository.findByUsername(getCurrentUsername());
        UserData userData = userDataRepository.findByUsername(getCurrentUsername());
        if (userData != null) {
            userData.setRoles(user.getRoles());
        }
        return userData;
    }

    @PreAuthorize(RoleAuthorities.IS_NONE)
    @RequestMapping(path = "changeDisplayName", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changeDisplayName(@Valid @RequestBody DisplayNameChange displayNameChange) {
        UserData userData = userDataRepository.findByUsername(getCurrentUsername());
        userData.setDisplayName(displayNameChange.getDisplayName());
        userDataRepository.save(userData);
    }

    @PreAuthorize(RoleAuthorities.IS_NONE)
    @RequestMapping(path = "changePassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@Valid @RequestBody PasswordChange passwordChange) {
        User user = userRepository.findByUsername(getCurrentUsername());
        String currentPassword = EncoderDecoder.base64URLDecode(passwordChange.getCurrentPassword());
        String newPassword = EncoderDecoder.base64URLDecode(passwordChange.getNewPassword());
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
           throw new ValidationException("not-match", "Passwords not match");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public static class DisplayNameChange {

        @NotNull
        @Size(min = 4, max = 16)
        private String displayName;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class PasswordChange {

        @NotNull
        private String currentPassword;

        @NotNull
        @Size(min = 6)
        private String newPassword;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }



}
