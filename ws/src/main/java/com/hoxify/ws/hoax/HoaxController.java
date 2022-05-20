package com.hoxify.ws.hoax;

import com.hoxify.ws.hoax.vm.HoaxVM;
import com.hoxify.ws.shared.CurrentUser;
import com.hoxify.ws.shared.GenericResponse;
import com.hoxify.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    public GenericResponse saveHoax(@Valid @RequestBody Hoax hoax, @CurrentUser User user) {
        hoaxService.save(hoax, user);
        return new GenericResponse("Hoax is saved");

    }

    @GetMapping("/hoaxes")
    public Page<HoaxVM> getHoaxes(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getHoaxes(pageable).map(HoaxVM::new);
    }

    @GetMapping("/hoaxes/{id:\\d+}")
    Page<HoaxVM> getHoaxesRelative(@PathVariable Long id, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getOldHoaxes(id, pageable).map(HoaxVM::new);
    }

    @GetMapping("/users/{username}/hoaxes")
    public Page<HoaxVM> getUserHoaxes(@PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getUserHoaxes(pageable, username).map(HoaxVM::new);
    }

}
