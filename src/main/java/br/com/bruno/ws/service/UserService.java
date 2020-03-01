package br.com.bruno.ws.service;

import br.com.bruno.ws.domain.User;
import br.com.bruno.ws.dto.UserDTO;
import br.com.bruno.ws.repository.UserRepository;
import br.com.bruno.ws.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()-> new ObjectNotFoundException("Objeto não encotrado"));
    }

    public User create ( User u ) {
        return userRepository.save( u );
    }

    public  User fromDTO(UserDTO userDTO) {
        return new User( userDTO );
    }

    public User update( User u ) {

       Optional<User> updateUser = userRepository.findById( u.getId() );
       return updateUser.map( x-> userRepository.save( new User( x.getId(), u.getFirstName(), u.getLastName()
                                                                , u.getEmail(), u.getPassword(), u.isEnabled() ) ) )
                .orElseThrow( () -> new ObjectNotFoundException("Usuário não encontrado!") );

    }

    public void delete( String id ) {
        userRepository.deleteById( id );
    }

}
