package br.com.bruno.ws.config;

import br.com.bruno.ws.domain.Role;
import br.com.bruno.ws.domain.User;
import br.com.bruno.ws.repository.RoleRepository;
import br.com.bruno.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        roleRepository.deleteAll();
        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        Role roleUser = createRoleIfNotFound("ROLE_USER");

        userRepository.deleteAll();
        User mario = new User("Mario", "Abreu", "ma@gmail.com");
        User julia = new User("Julia", "Silva", "js@gmail.com");

        mario.setRoles( Arrays.asList( roleAdmin ) );
        mario.setPassword( passwordEncoder.encode("1234") );
        mario.setEnabled(true);

        julia.setRoles( Arrays.asList( roleUser ) );
        julia.setPassword( passwordEncoder.encode("1234") );
        julia.setEnabled(true);

        createUserIfNotFound( mario );
        createUserIfNotFound( julia );


    }

    private User createUserIfNotFound(final User u) {
        Optional<User> obj = userRepository.findByEmail(u.getEmail());

        if ( obj.isPresent() ) {
            return obj.get();
        } else {
            return userRepository.save( u );
        }
    }

    private Role createRoleIfNotFound( String name ) {
        Optional<Role> r = roleRepository.findByName( name );
        if ( r.isPresent() ) {
            return r.get();
        }
        return roleRepository.save( new Role( name ) );
    }

}
