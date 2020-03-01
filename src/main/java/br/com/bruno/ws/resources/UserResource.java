package br.com.bruno.ws.resources;

import br.com.bruno.ws.domain.Role;
import br.com.bruno.ws.domain.User;
import br.com.bruno.ws.dto.UserDTO;
import br.com.bruno.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> listDTO = users.stream().map(x-> new UserDTO(x)).collect(Collectors.toList());

        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById( id );
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        User u = userService.fromDTO( dto );
        return ResponseEntity.ok().body( new UserDTO( userService.create( u ) ) );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO u ) {
        User user = userService.fromDTO( u );
        user.setId( id );
        return ResponseEntity.ok().body( new UserDTO( userService.update( user ) ) );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id ) {
        userService.delete( id );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id]/roles")
    public ResponseEntity< List<Role> > findRoles( @PathVariable String id ) {
        User user = userService.findById( id );
        return ResponseEntity.ok().body( user.getRoles() );
    }

}
