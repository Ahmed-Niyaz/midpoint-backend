package com.project.midpoint.controller;

import com.project.midpoint.DTOs.AddMemberRequest;
import com.project.midpoint.DTOs.GroupRequest;
import com.project.midpoint.DTOs.GroupResponse;

import com.project.midpoint.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllMyGroups(@RequestHeader("userId") Long userId) {
        return ResponseEntity.ok(groupService.getUserGroups(userId));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupDetails(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroupDetails(groupId));
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @RequestBody GroupRequest request,
            @RequestHeader("userId") Long userId) {
        return ResponseEntity.ok(groupService.createGroup(request, userId));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<String> addMember(
            @PathVariable Long groupId,
            @RequestBody AddMemberRequest request) {
        groupService.addMember(groupId, request.getEmail());
        return ResponseEntity.ok("Member added successfully");
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> removeMember(
            @PathVariable Long groupId,
            @PathVariable Long userId) {
        groupService.removeMember(groupId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok("Group deleted successfully");
    }
}