package com.hoxify.ws.hoax;

import com.hoxify.ws.hoax.vm.HoaxSubmitVM;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    public GenericResponse saveHoax(@Valid @RequestBody HoaxSubmitVM hoax, @CurrentUser User user) {
        hoaxService.save(hoax, user);
        return new GenericResponse("Hoax is saved");

    }

    @GetMapping("/hoaxes")
    public Page<HoaxVM> getHoaxes(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getHoaxes(pageable).map(HoaxVM::new);
    }

    @GetMapping({"/hoaxes/{id:\\d+}", "/users/{username}/hoaxes/{id:\\d+}"})
    public ResponseEntity<?> getHoaxesRelative(@PathVariable Long id,
                                               @PathVariable(required = false) String username,
                                               @PageableDefault(sort = "id",
                                                       direction = Sort.Direction.DESC) Pageable pageable,
                                               @RequestParam(name = "count",
                                                       required = false, defaultValue = "false") boolean count,
                                               @RequestParam(name = "direction",
                                                       defaultValue = "before") String direction) {
        if (count) {
            Long newHoaxCount = hoaxService.getNewHoaxesCount(id, username);
            Map<String, Long> response = new HashMap<>();
            response.put("count", newHoaxCount);
            return ResponseEntity.ok(response);
        }
        if (direction.equals("after")) {
            List<HoaxVM> newHoaxes = hoaxService.getNewHoaxes(id, username, pageable.getSort())
                    .stream().map(HoaxVM::new).collect(Collectors.toList());
            return ResponseEntity.ok(newHoaxes);
        }
        return ResponseEntity.ok(hoaxService.getOldHoaxes(id, username, pageable).map(HoaxVM::new));
    }

    @GetMapping("/users/{username}/hoaxes")
    public Page<HoaxVM> getUserHoaxes(@PathVariable String username, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return hoaxService.getUserHoaxes(pageable, username).map(HoaxVM::new);
    }

    @DeleteMapping("/hoaxes/{id:\\d+}")
    @PreAuthorize("@hoaxSecurityService.isAllowedToDelete(#id, principal)")
    public GenericResponse deleteHoax(@PathVariable Long id) {
        hoaxService.delete(id);
        return new GenericResponse("Hoax removed");
    }
}
