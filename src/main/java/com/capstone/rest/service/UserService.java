package com.capstone.rest.service;

import com.capstone.rest.entity.RekamMedis;
import com.capstone.rest.entity.User;
import com.capstone.rest.model.PagingResponse;
import com.capstone.rest.model.user.RegisterUserAdminRequest;
import com.capstone.rest.model.user.RegisterUserRequest;
import com.capstone.rest.model.user.SearchUserRequest;
import com.capstone.rest.model.user.response.CountResponse;
import com.capstone.rest.model.user.response.GetUserResponse;
import com.capstone.rest.model.user.response.RegisterUserResponse;
import com.capstone.rest.repository.RmRepository;
import com.capstone.rest.repository.UserRepository;
import com.capstone.rest.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ValidationService validator;

    @Autowired
    private RmRepository rmRepository;

    @Transactional
    public void registerAdm(RegisterUserAdminRequest request) {
        validator.validate(request);

        if(repository.existsById(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole(1);
        repository.save(user);

    }

    @Transactional
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        validator.validate(request);


        if(repository.existsById(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already registered");
        }

//        Generate No Rekam medis
        Long totalUser = repository.count();
        totalUser++;
        String noRm = request.getCategory() + totalUser;

//        Generate Password
        String pw = genPassword();

//        Generate Created At
        LocalDate createdAt = LocalDate.now();

//        Conversi String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(request.getBirthDate(), formatter);

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setGender(request.getGender());
        newUser.setBirthplace(request.getTempat_lahir());
        newUser.setBirthDate(date);
        newUser.setCategory(request.getCategory());
        newUser.setInstansi(request.getInstansi());

        newUser.setPassword(BCrypt.hashpw(pw, BCrypt.gensalt()));
        newUser.setNoRm(noRm);
        newUser.setRole(2);
        repository.save(newUser);

        RekamMedis rm = new RekamMedis();
        rm.setAnamnesa(request.getAnamnesa());
        rm.setDiagnosis(request.getDiagnosis());
        rm.setTherapy(request.getTherapy());
        rm.setUser(newUser);
        rm.setCreatedAt(createdAt);
        rm.setTotalObat(request.getTotalObat());
        rmRepository.save(rm);

        return RegisterUserResponse.builder()
                .email(newUser.getEmail())
                .noRm(newUser.getNoRm())
                .password(pw)
                .createdAt(rm.getCreatedAt())
                .build();

    }

    public GetUserResponse getUserCurrent(User user) {
        return GetUserResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .instansi(user.getInstansi())
                .name(user.getName())
                .noRm(user.getNoRm())
                .tanggal_lahir(user.getBirthDate())
                .tempat_lahir(user.getBirthplace())
                .token(user.getToken())
                .build();
    }

    @Transactional
    public List<GetUserResponse> getAll() {
        List<User> list = repository.findAllByRole(2).stream().toList();
        return list.stream().map(lists -> toUserResponse(lists)).toList();
    }

    @Transactional(readOnly = true)
    public Page<GetUserResponse> search(SearchUserRequest request) {
        Specification<User> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("role"), 2));

            if (Objects.nonNull(request.getName())) {
                predicates.add(builder.like(root.get("name"), "%" + request.getName() + "%"));
            }

            if (Objects.nonNull(request.getNoRm())) {
                predicates.add(builder.like(root.get("noRm"), "%" + request.getNoRm() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();

        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<User> users = repository.findAll(specification, pageable);
        List<GetUserResponse> responses = users.getContent()
                .stream()
                .map(user -> toUserResponse(user))
                .collect(Collectors.toList());

        return new PageImpl<>(responses,  pageable, users.getTotalElements());
    }

    @Transactional
    public CountResponse countTotal() {
        Integer employee  = repository.countAllByCategory("K");
        Integer student  = repository.countAllByCategory("M");
        Integer total = employee + student;

        return CountResponse.builder()
                .totalUserM(student)
                .totalUserK(employee)
                .totalAll(total)
                .build();
    }


    private GetUserResponse toUserResponse(User list) {
        return GetUserResponse.builder()
                .email(list.getEmail())
                .role(list.getRole())
                .gender(list.getGender())
                .tempat_lahir(list.getBirthplace())
                .tanggal_lahir(list.getBirthDate())
                .instansi(list.getInstansi())
                .noRm(list.getNoRm())
                .name(list.getName())
                .build();
    }

    private String genPassword() {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        Random random = new Random();

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(alphabet.length);
            password.append(alphabet[index]);
        }

        return password.toString();
    }

}
