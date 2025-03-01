package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.MemberEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchMemberRequest;
import com.example.maxbitdigitalasset.model.request.V1PostMemberRequest;
import com.example.maxbitdigitalasset.model.response.V1GetMemberResponse;
import com.example.maxbitdigitalasset.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.maxbitdigitalasset.util.JsonUtil.objectToJson;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<V1GetMemberResponse> getAllMembers() {
        List<V1GetMemberResponse> response = new ArrayList<>();
        List<MemberEntity> members = new ArrayList<>(memberRepository.findAll());

        for (MemberEntity member : members) {
            V1GetMemberResponse tempResponse = new V1GetMemberResponse();
            BeanUtils.copyProperties(member, tempResponse);
            response.add(tempResponse);
        }
        log.debug("response = :{}", objectToJson(response));

        return response;
    }

    public V1GetMemberResponse getMemberById(String id) {
        V1GetMemberResponse response;
        Optional<MemberEntity> member = memberRepository.findById(id);

        response = member.map(memberEntity -> new V1GetMemberResponse()
                        .setId(memberEntity.getId())
                        .setEmail(memberEntity.getEmail())
                        .setName(memberEntity.getName()))
                .orElse(new V1GetMemberResponse());
        log.debug("response = :{}", objectToJson(response));

        return response;
    }

    public void insertMember(V1PostMemberRequest request) {
        memberRepository.save(new MemberEntity(request.getId(), request.getName(), request.getEmail(), new HashSet<>()));
    }

    public void updateMember(String id, V1PatchMemberRequest request) {
        memberRepository.findById(id)
                .map(memberEntity -> {
                    memberEntity.setName(request.getName())
                            .setEmail(request.getEmail());
                    return memberRepository.save(memberEntity);
                })
                .orElseThrow(() -> new RuntimeException(String.format("Can not found id: %s in MemberRepository", id)));
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }
}
