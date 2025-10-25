package com.project.midpoint.service;

import com.project.midpoint.DTOs.GroupRequest;
import com.project.midpoint.DTOs.GroupResponse;
import com.project.midpoint.DTOs.MemberResponse;
import com.project.midpoint.model.Groups;
import com.project.midpoint.model.UserGroups;
import com.project.midpoint.model.Users;
import com.project.midpoint.repository.GroupRepository;
import com.project.midpoint.repository.UserGroupRepository;
import com.project.midpoint.repository.UserRepository;
import com.project.midpoint.repository.UserLocationsRepository;
import com.project.midpoint.repository.VotesRepository;  // ADD THIS
import com.project.midpoint.repository.VenuesRepository;  // ADD THIS
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserLocationsRepository userLocationsRepository;
    private final VotesRepository votesRepository;  // ADD THIS
    private final VenuesRepository venuesRepository;  // ADD THIS

    public List<GroupResponse> getUserGroups(Long userId) {
        List<UserGroups> userGroups = userGroupRepository.findByUserId(userId);

        return userGroups.stream().map(ug -> {
            Groups group = groupRepository.findById(ug.getGroupId()).orElseThrow();
            Users owner = userRepository.findById(group.getOwnerId()).orElseThrow();

            GroupResponse response = new GroupResponse();
            response.setId(group.getId());
            response.setName(group.getName());
            response.setOwnerId(group.getOwnerId());
            response.setOwnerName(owner.getUsername());
            response.setMemberCount(userGroupRepository.countByGroupId(group.getId()));
            response.setCreatedAt(group.getCreatedAt());
            return response;
        }).collect(Collectors.toList());
    }

    public GroupResponse getGroupDetails(Long groupId) {
        Groups group = groupRepository.findById(groupId).orElseThrow();
        Users owner = userRepository.findById(group.getOwnerId()).orElseThrow();

        List<UserGroups> userGroups = userGroupRepository.findByGroupId(groupId);
        List<MemberResponse> members = userGroups.stream().map(ug -> {
            Users user = userRepository.findById(ug.getUserId()).orElseThrow();
            MemberResponse member = new MemberResponse();
            member.setUserId(user.getId());
            member.setUsername(user.getUsername());
            member.setEmail(user.getEmail());
            member.setJoinedAt(ug.getJoinedAt());
            return member;
        }).collect(Collectors.toList());

        GroupResponse response = new GroupResponse();
        response.setId(group.getId());
        response.setName(group.getName());
        response.setOwnerId(group.getOwnerId());
        response.setOwnerName(owner.getUsername());
        response.setMemberCount(members.size());
        response.setCreatedAt(group.getCreatedAt());
        response.setMembers(members);
        return response;
    }

    public GroupResponse createGroup(GroupRequest request, Long userId) {
        Users user = userRepository.findById(userId).orElseThrow();

        Groups group = new Groups();
        group.setName(request.getName());
        group.setOwnerId(userId);
        group = groupRepository.save(group);

        UserGroups userGroup = new UserGroups();
        userGroup.setUserId(userId);
        userGroup.setGroupId(group.getId());
        userGroupRepository.save(userGroup);

        GroupResponse response = new GroupResponse();
        response.setId(group.getId());
        response.setName(group.getName());
        response.setOwnerId(group.getOwnerId());
        response.setOwnerName(user.getUsername());
        response.setMemberCount(1);
        response.setCreatedAt(group.getCreatedAt());
        return response;
    }

    public void addMember(Long groupId, String email) {
        Users user = userRepository.findByEmail(email);

        UserGroups userGroup = new UserGroups();
        userGroup.setUserId(user.getId());
        userGroup.setGroupId(groupId);
        userGroupRepository.save(userGroup);
    }

    @Transactional
    public void removeMember(Long groupId, Long userId) {
        // 1. Delete user's votes in this group
        votesRepository.deleteByUserIdAndGroupId(userId, groupId);

        // 2. Delete user's location in this group
        userLocationsRepository.deleteByUserIdAndGroupId(userId, groupId);

        // 3. Remove user from group
        UserGroups userGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow();
        userGroupRepository.delete(userGroup);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        // 1. Delete all votes for this group
        votesRepository.deleteByGroupId(groupId);

        // 2. Delete all venues for this group
        venuesRepository.deleteByGroupId(groupId);

        // 3. Delete all locations for this group
        userLocationsRepository.deleteByGroupId(groupId);

        // 4. Delete all user-group relationships
        userGroupRepository.deleteByGroupId(groupId);

        // 5. Delete the group itself
        groupRepository.deleteById(groupId);
    }
}