package kr.co.mz.fnm.megablock.restapi.controller;

import kr.co.mz.fnm.megablock.restapi.entity.User;
import kr.co.mz.fnm.megablock.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public User put(@RequestParam(value = "name") String name) {
        return userRepository.save(new User(name));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Iterable<User> list() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id).get();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public User update(@PathVariable(value = "id") Long id, @RequestParam(value = "name") String name) {
        User user = userRepository.findById(id).get();
        user.setName(name);
        return userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam(value = "id") Long id) {
        userRepository.deleteById(id);
    }
}