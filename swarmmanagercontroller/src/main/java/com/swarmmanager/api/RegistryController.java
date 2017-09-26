package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.mongo.Registry;
import com.swarmmanager.mongo.RegistryRepository;
import com.swarmmanager.mongo.RegistryUser;
import com.swarmmanager.mongo.RegistryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/api/registry")
public class RegistryController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private RegistryUserRepository registryUserRepository;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Registry> getRegistries() {
        return registryRepository.findAll();
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{registryName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void removeRegistry(@PathVariable String registryName) {
        registryRepository.delete(registryName);

    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createRegistry(@RequestBody Registry registry) {
        registryRepository.insert(registry);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> getRegistryUsers() {
        return registryUserRepository.findAll().stream().map(RegistryUser::getRegistryUsername).collect(toList());
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "user/{registryUsername}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void removeRegistryUsers(@PathVariable String registryUsername) {
        registryUserRepository.delete(registryUsername);

    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createRegistryUser(@RequestBody RegistryUser registryUser) {
        if (registryUser.getRegistryUsername() != null && registryUser.getRegistryPassword() != null) {
            registryUser.setRegistryPassword(passwordEncoder.encode(registryUser.getRegistryPassword()));
            registryUserRepository.insert(registryUser);
        }
    }

}
