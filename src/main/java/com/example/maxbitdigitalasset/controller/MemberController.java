package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.controller.template.CrudController;
import com.example.maxbitdigitalasset.model.request.V1PatchMemberRequest;
import com.example.maxbitdigitalasset.model.request.V1PostMemberRequest;
import com.example.maxbitdigitalasset.model.response.V1GetMemberResponse;
import com.example.maxbitdigitalasset.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/member")
@Validated
public class MemberController implements CrudController<V1GetMemberResponse, V1PostMemberRequest, V1PatchMemberRequest> {

    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<V1GetMemberResponse>> getAllUser() {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                memberService.getAllMembers(),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<V1GetMemberResponse> getUserById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                memberService.getMemberById(id),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> postUser(
            @RequestBody @Valid V1PostMemberRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        memberService.insertMember(request);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PatchMapping
    public ResponseEntity<Void> patchUserById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id,
            @RequestBody @Valid V1PatchMemberRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        memberService.updateMember(id, request);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        memberService.deleteMember(id);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }
}
