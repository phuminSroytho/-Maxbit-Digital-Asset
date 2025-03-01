package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.MemberEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchMemberRequest;
import com.example.maxbitdigitalasset.model.request.V1PostMemberRequest;
import com.example.maxbitdigitalasset.model.response.V1GetMemberResponse;
import com.example.maxbitdigitalasset.repository.MemberRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    private final String id = "1";
    private final String email = "email";
    private final String name = "name";
    private final MemberEntity mockMemberEntity = new MemberEntity(Integer.parseInt(id), name, email, new HashSet<>());

    @Nested
    class Test_getMember {
        private final V1GetMemberResponse mockV1GetMemberResponse = new V1GetMemberResponse()
                .setId(id)
                .setEmail(email)
                .setName(name);

        @Test
        void test_getAllMembers_success() {
            var expected = List.of(mockV1GetMemberResponse);

            doReturn(List.of(mockMemberEntity))
                    .when(memberRepository)
                    .findAll();

            var actual = memberService.getAllMembers();
            assertEquals(expected, actual);
        }

        @Test
        void test_getMemberById_success() {
            doReturn(Optional.of(mockMemberEntity))
                    .when(memberRepository)
                    .findById(id);

            var actual = memberService.getMemberById(id);
            assertEquals(mockV1GetMemberResponse, actual);
        }

        @Test
        void test_getAuthorById_notFound() {
            var expected = new V1GetMemberResponse();

            doReturn(Optional.empty())
                    .when(memberRepository)
                    .findById(id);

            var actual = memberService.getMemberById(id);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Test_insertMember {
        private final V1PostMemberRequest mockV1PostMemberRequest = new V1PostMemberRequest()
                .setId(id)
                .setName(name)
                .setEmail(email);


        @Test
        void test_insertMember_success() {
            var expected = mockMemberEntity;
            doReturn(expected)
                    .when(memberRepository)
                    .save(expected);

            assertDoesNotThrow(() -> memberService.insertMember(mockV1PostMemberRequest));
            verify(memberRepository, times(1))
                    .save(expected);
        }
    }

    @Nested
    class Test_updateAuthor {
        private final V1PatchMemberRequest mockV1PatchMemberRequest = new V1PatchMemberRequest()
                .setName(name)
                .setEmail(email);

        @Test
        void test_updateMember_success() {
            var expected = mockMemberEntity;
            doReturn(Optional.of(expected))
                    .when(memberRepository)
                    .findById(id);

            doReturn(expected)
                    .when(memberRepository)
                    .save(expected);

            assertDoesNotThrow(() -> memberService.updateMember(id, mockV1PatchMemberRequest));
        }

        @Test
        void test_updateMember_failed() {
            doReturn(Optional.empty())
                    .when(memberRepository)
                    .findById(id);

            assertThrows(RuntimeException.class, () -> memberService.updateMember(id, mockV1PatchMemberRequest));
        }
    }

    @Nested
    class Test_deleteMember {
        @Test
        void test_deleteAuthor_success() {
            doNothing()
                    .when(memberRepository)
                    .deleteById(id);

            assertDoesNotThrow(() -> memberService.deleteMember(id));
        }
    }
}