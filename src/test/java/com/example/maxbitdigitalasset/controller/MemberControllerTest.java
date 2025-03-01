package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.model.request.V1PatchMemberRequest;
import com.example.maxbitdigitalasset.model.request.V1PostMemberRequest;
import com.example.maxbitdigitalasset.model.response.V1GetMemberResponse;
import com.example.maxbitdigitalasset.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;
    @Captor
    ArgumentCaptor<V1PostMemberRequest> captorParamsV1PostMemberRequest;
    @Captor
    ArgumentCaptor<V1PatchMemberRequest> captorParamsV1PatchMemberRequest;

    private final String id = "id";
    private final String email = "email";
    private final String name = "name";
    private final V1GetMemberResponse mockV1GetMemberResponse = new V1GetMemberResponse()
            .setId(id)
            .setEmail(email)
            .setName(name);

    @BeforeEach
    void setup() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ReflectionTestUtils.setField(memberController, "httpHeaders", httpHeaders);
    }

    @Test
    void test_getAll_success() {
        var expected = List.of(mockV1GetMemberResponse);

        doReturn(expected)
                .when(memberService)
                .getAllMembers();

        var actual = memberController.getAll();
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_getById_success() {
        var expected = mockV1GetMemberResponse;

        doReturn(expected)
                .when(memberService)
                .getMemberById(id);

        var actual = memberController.getById(id);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_post_success() {
        var request = new V1PostMemberRequest()
                .setId(id)
                .setName(name)
                .setEmail(email);

        doNothing()
                .when(memberService)
                .insertMember(captorParamsV1PostMemberRequest.capture());

        assertDoesNotThrow(() -> memberController.post(request));
        assertEquals(request, captorParamsV1PostMemberRequest.getValue());
        verify(memberService, times(1))
                .insertMember(request);
    }

    @Test
    void test_patchById_success() {
        var request = new V1PatchMemberRequest()
                .setName(name)
                .setEmail(email);

        doNothing()
                .when(memberService)
                .updateMember(eq(id), captorParamsV1PatchMemberRequest.capture());

        assertDoesNotThrow(() -> memberController.patchById(id, request));
        assertEquals(request, captorParamsV1PatchMemberRequest.getValue());
        verify(memberService, times(1))
                .updateMember(id, request);
    }

    @Test
    void test_deleteById_success() {
        doNothing()
                .when(memberService)
                .deleteMember(id);

        assertDoesNotThrow(() -> memberController.deleteById(id));
        verify(memberService, times(1))
                .deleteMember(id);
    }
}