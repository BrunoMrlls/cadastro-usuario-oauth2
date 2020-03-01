package br.com.bruno.ws.service;

import br.com.bruno.ws.domain.Role;
import br.com.bruno.ws.domain.User;
import br.com.bruno.ws.repository.UserRepository;
import br.com.bruno.ws.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail( s );

        if ( !optionalUser.isPresent() ) {
            throw new UsernameNotFoundException( String.format("UserNotExist") );
        } else if ( ! optionalUser.get().isEnabled() ) {
            throw new ObjectNotFoundException( String.format("UserNotEnabled") );
        }
        return new UserRepositoryUserDetails( optionalUser.get() );
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final Collection<Role> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role r: roles) {
            authorities.add( new SimpleGrantedAuthority( r.getName() ));
        }
        return authorities;
    }

    public final Collection<? extends GrantedAuthority> getAuthorities( final Collection<Role> roles ) {
        return getGrantedAuthorities( roles );
    }

    private final static class UserRepositoryUserDetails extends User implements UserDetails {

        public UserRepositoryUserDetails( User u ) {
            super( u );
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getRoles();
        }

        @Override
        public String getPassword() {
            return getPassword();
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return isEnabled();
        }

    }

}
