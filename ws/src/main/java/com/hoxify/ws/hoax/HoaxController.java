package com.hoxify.ws.hoax;

import com.hoxify.ws.hoax.vm.HoaxVM;
import com.hoxify.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    public GenericResponse saveHoax(@Valid @RequestBody Hoax hoax) {
        hoaxService.save(hoax);
        return new GenericResponse("Hoax is saved");

    }

    @GetMapping("/hoaxes")
    public Page<HoaxVM> getHoaxes(Pageable pageable){
        return hoaxService.getHoaxes(pageable).map(HoaxVM::new);
    }

}
