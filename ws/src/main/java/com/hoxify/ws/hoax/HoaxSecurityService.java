package com.hoxify.ws.hoax;

import com.hoxify.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HoaxSecurityService {
    @Autowired
    HoaxRepository hoaxRepository;

    public boolean isAllowedToDelete(Long id, User loggedInUser) {
        Optional<Hoax> optionalHoax = hoaxRepository.findById(id);
        if (!optionalHoax.isPresent()) {
            return false;
        } else {
            Hoax hoax = optionalHoax.get();
            return hoax.getUser().getId() == loggedInUser.getId();
        }
    }
}
