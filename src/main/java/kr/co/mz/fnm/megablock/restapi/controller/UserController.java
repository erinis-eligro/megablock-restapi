package kr.co.mz.fnm.megablock.restapi.controller;

import kr.co.mz.fnm.megablock.restapi.entity.User;
import kr.co.mz.fnm.megablock.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    public static User loginUser;

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

    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody String body) {
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(body);

        HashMap<String ,Object> returnJsonMap = new HashMap<>();
        List<User> users = userRepository.findAll();

        if (users == null) {
            returnJsonMap.put("code","empry");
            return returnJsonMap;
        }

        User selectedUser = null;
        for(User u : users) {
            if (u.getName().equals(jsonMap.get("name").toString())) {
                selectedUser = u;
                loginUser = u;
            }
        }

        if (selectedUser == null) {
            returnJsonMap.put("code","empry");
            return returnJsonMap;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(body.getBytes());
            String encryptedString = new String(messageDigest.digest());
            returnJsonMap.put("user",selectedUser);
            returnJsonMap.put("validateCode",encryptedString);
        } catch (Exception e) {

        }

        return returnJsonMap;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}