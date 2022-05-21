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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getHoaxesRelative(@PathVariable Long id,
                                               @PageableDefault(sort = "id",
                                                       direction = Sort.Direction.DESC) Pageable pageable,
                                               @RequestParam(name = "count",
                                                       required = false, defaultValue = "false") boolean count,
                                               @RequestParam(name = "direction",
                                                       defaultValue = "before") String direction) {
        if (count) {
            Long newHoaxCount = hoaxService.getNewHoaxesCount(id);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newHoaxCount);
            return ResponseEntity.ok(response);
        }
        if (direction.equals("after")) {
            List<HoaxVM> newHoaxes = hoaxService.getNewHoaxes(id, pageable.getSort())
                    .stream().map(HoaxVM::new).collect(Collectors.toList());
            return ResponseEntity.ok(newHoaxes);
        }
        return ResponseEntity.ok(hoaxService.getOldHoaxes(id, pageable).map(HoaxVM::new));
    }

    @GetMapping("/users/{username}/hoaxes")
    public Page<HoaxVM> getUserHoaxes(@PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getUserHoaxes(pageable, username).map(HoaxVM::new);
    }

    @GetMapping("/users/{username}/hoaxes/{id:\\d+}")
    public ResponseEntity<?> getUserHoaxesRelative(@PathVariable Long id,
                                                   @PathVariable String username,
                                                   @PageableDefault(sort = "id",
                                                           direction = Sort.Direction.DESC) Pageable pageable,
                                                   @RequestParam(name = "count",
                                                           required = false, defaultValue = "false") boolean count,
                                                   @RequestParam(name = "direction",
                                                           defaultValue = "before") String direction) {
        if (count) {
            Long newUserHoaxCount = hoaxService.getNewUserHoaxCount(id, username);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newUserHoaxCount);
            return ResponseEntity.ok(response);
        }
        if (direction.equals("after")) {
            List<HoaxVM> newUserHoaxes = hoaxService.getNewUserHoaxes(username, id, pageable.getSort())
                    .stream().map(HoaxVM::new).collect(Collectors.toList());
            return ResponseEntity.ok(newUserHoaxes);
        }
        return ResponseEntity.ok(hoaxService.getOldUserHoaxes(id, username, pageable).map(HoaxVM::new));
    }

}
