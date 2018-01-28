package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.repository.model.RegistryUser;
import com.swarmmanager.repository.RegistryUserRepository;
import com.swarmmanager.util.EncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.swarmmanager.util.UserUtil.getCurrentUsername;

@RestController
@RequestMapping("/api/registriesUsers")
public class RegistryController {

    @Autowired
    private RegistryUserRepository registryUserRepository;

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<RegistryUser> getRegistriesUsers() {
        return registryUserRepository.findByUserOwner(getCurrentUsername()).stream().map(registryUser -> {
            registryUser.setRegistryPassword(null);
            return registryUser;
        }).collect(Collectors.toList());
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void removeRegistryUser(@PathVariable String name) {
        registryUserRepository.deleteByNameAndUserOwner(name, getCurrentUsername());
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createRegistryUSer(@RequestBody RegistryUser registryUser) {
        registryUser.setUserOwner(getCurrentUsername());
        if (registryUser.getRegistryPassword() != null) {
            registryUser.setRegistryPassword(EncoderDecoder.base64URLEncode(registryUser.getRegistryPassword()));
            registryUserRepository.insert(registryUser);
        }
    }

}
