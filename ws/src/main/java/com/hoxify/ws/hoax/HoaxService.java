package com.hoxify.ws.hoax;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;
    UserService userService;

    public HoaxService(HoaxRepository hoaxRepository, UserService userService) {
        this.hoaxRepository = hoaxRepository;
        this.userService = userService;
    }

    public void save(Hoax hoax, User user) {
        hoax.setTimestamp(new Date());
        hoax.setUser(user);
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> getHoaxes(Pageable pageable) {
        return hoaxRepository.findAll(pageable);
    }

    public Page<Hoax> getUserHoaxes(Pageable pageable, String username) {
        User user = userService.getByUsername(username);
        return hoaxRepository.findByUser(user, pageable);
    }

    public Page<Hoax> getOldHoaxes(Long id, Pageable pageable) {
        return hoaxRepository.findByIdLessThan(id, pageable);
    }

    public Page<Hoax> getOldUserHoaxes(Long id, String username, Pageable pageable) {
        User user = userService.getByUsername(username);
        return hoaxRepository.findByIdLessThanAndUser(id, user, pageable);
    }

    public Long getNewHoaxesCount(Long id) {
        return hoaxRepository.countByIdGreaterThan(id);
    }

    public Long getNewUserHoaxCount(Long id, String username) {
        User user = userService.getByUsername(username);
        return hoaxRepository.countByUserAndIdGreaterThan(user, id);
    }

    public List<Hoax> getNewHoaxes(Long id, Sort sort) {
        return hoaxRepository.findByIdGreaterThan(id, sort);
    }

    public List<Hoax> getNewUserHoaxes(String username, Long id, Sort sort) {
        User user = userService.getByUsername(username);
        return hoaxRepository.findByUserAndIdGreaterThan(user, id, sort);
    }
}
