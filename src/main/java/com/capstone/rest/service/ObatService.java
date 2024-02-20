package com.capstone.rest.service;

import com.capstone.rest.entity.Obat;
import com.capstone.rest.model.obat.CreateObatRequest;
import com.capstone.rest.model.obat.UpdateObatRequest;
import com.capstone.rest.model.obat.response.ObatResponse;
import com.capstone.rest.repository.ObatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class ObatService {

    @Autowired
    private ObatRepository repository;

    @Autowired
    private ValidationService validator;

    @Transactional
    public ObatResponse create(CreateObatRequest request) {
        validator.validate(request);
        Obat obat1 = repository.findFirstByNameObat(request.getNameObat()).orElse(null);
        if(obat1 != null) {
            throw new ResponseStatusException(HttpStatus.FOUND, "Nama Obat Sudah Ada");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getExpired(), formatter);

        Obat obat = new Obat();
        obat.setNameObat(request.getNameObat());
        obat.setExpiredAt(date);
        obat.setStock(request.getStock());
        obat.setMintStock(request.getMinStock());
        obat.setUsedTotal(0);
        repository.save(obat);

        return ObatResponse.builder()
                .nameObat(obat.getNameObat())
                .expired(obat.getExpiredAt())
                .stock(obat.getStock())
                .minStock(obat.getMintStock())
                .usedTotal(obat.getUsedTotal())
                .build();
    }
    @Transactional
    public List<ObatResponse> list() {
        List<Obat> list = repository.findAll().stream().toList();
        return list.stream().map(lists -> toObatResponse(lists)).toList();
    }

    @Transactional
    public ObatResponse update(UpdateObatRequest request) {
        Obat obat = repository.findFirstByIdObat(request.getIdObat()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Obat tidak ada"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getExpired(), formatter);

        if(Objects.nonNull(request.getNameObat())) {
            obat.setNameObat(request.getNameObat());
        }

        if(Objects.nonNull(request.getExpired())) {
            obat.setExpiredAt(date);
        }

        if (Objects.nonNull(request.getStock())) {
            obat.setStock(request.getStock());
        }

        if (Objects.nonNull(request.getMinStock())) {
            obat.setMintStock(request.getMinStock());
        }

        repository.save(obat);

        return ObatResponse.builder()
                .idObat(obat.getIdObat())
                .nameObat(obat.getNameObat())
                .usedTotal(obat.getUsedTotal())
                .minStock(obat.getMintStock())
                .stock(obat.getStock())
                .expired(obat.getExpiredAt())
                .build();
    }

    private ObatResponse toObatResponse(Obat obat) {
        return ObatResponse.builder()
                .idObat(obat.getIdObat())
                .nameObat(obat.getNameObat())
                .expired(obat.getExpiredAt())
                .stock(obat.getStock())
                .minStock(obat.getMintStock())
                .usedTotal(obat.getUsedTotal())
                .build();
    }



}
