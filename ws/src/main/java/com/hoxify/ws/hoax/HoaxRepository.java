package com.hoxify.ws.hoax;

import com.hoxify.ws.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax, Long> {
    Page<Hoax> findByUser(User user, Pageable pageable);
    Page<Hoax> findByIdLessThan(Long id, Pageable pageable);

    Page<Hoax> findByIdLessThanAndUser(Long id, User user, Pageable pageable);

    Long countByIdGreaterThan(Long id);
}
