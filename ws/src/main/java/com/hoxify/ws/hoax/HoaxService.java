package com.hoxify.ws.hoax;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public Page<Hoax> getOldHoaxes(Long id, String username, Pageable pageable) {
        Specification<Hoax> specification = idLessThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification, pageable);
    }

    public Long getNewHoaxesCount(Long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.count(specification);
    }

    public List<Hoax> getNewHoaxes(Long id, String username, Sort sort) {
        Specification<Hoax> specification = idGreaterThan(id);
        if (username != null) {
            User user = userService.getByUsername(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification, sort);
    }

    Specification<Hoax> idLessThan(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    Specification<Hoax> userIs(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    Specification<Hoax> idGreaterThan(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), id);
    }
}
