package com.capstone.rest.service;

import com.capstone.rest.entity.RekamMedis;
import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.rekamMedis.CreateRekamMedisRequest;
import com.capstone.rest.model.rekamMedis.response.CreateRekamMedisResponse;
import com.capstone.rest.model.user.response.GetUserResponse;
import com.capstone.rest.repository.RmRepository;
import com.capstone.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RekamMedisService {

    @Autowired
    private ValidationService validator;

    @Autowired
    private RmRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CreateRekamMedisResponse create(CreateRekamMedisRequest request, String noRm) {
        validator.validate(request);

        User user = userRepository.findFirstByNoRm(noRm).orElse(null);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Tidak ada");
        }

        RekamMedis rm = new RekamMedis();
        rm.setAnamnesa(request.getAnamnesa());
        rm.setDiagnosis(request.getDiagnosis());
        rm.setTherapy(request.getTherapy());
        rm.setCreatedAt(LocalDate.now());
        rm.setTotalObat(request.getJumlahObat());
        rm.setUser(user);
        repository.save(rm);

        return CreateRekamMedisResponse.builder()
                .anamnesa(rm.getAnamnesa())
                .diagnosis(rm.getDiagnosis())
                .therapy(rm.getTherapy())
                .noRm(noRm)
                .jumlahObat(request.getJumlahObat())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CreateRekamMedisResponse> listById(String noRm) {
        User user = userRepository.findFirstByNoRm(noRm).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien Tidak ada"));
        List<RekamMedis> allByUser = repository.findAllByUser(user).stream().toList();
        return allByUser.stream().map(list -> CreateRekamMedisResponse.builder()
                .anamnesa(list.getAnamnesa())
                .diagnosis(list.getDiagnosis())
                .therapy(list.getTherapy())
                .therapy2(list.getTherapy2())
                .therapy3(list.getTherapy3())
                .therapy4(list.getTherapy4())
                .jumlahObat(list.getTotalObat())
                .jumlahObat2(list.getTotalObat2())
                .jumlahObat3(list.getTotalObat3())
                .jumlahObat4(list.getTotalObat4())
                .noRm(noRm)
                .Checkin(list.getCreatedAt())
                .build()).toList();
    }


}
