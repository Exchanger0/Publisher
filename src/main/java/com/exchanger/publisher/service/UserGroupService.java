package com.exchanger.publisher.service;

import com.exchanger.publisher.model.UserGroup;
import com.exchanger.publisher.model.UserRole;
import com.exchanger.publisher.model.key.UserGroupId;
import com.exchanger.publisher.repository.UserGroupRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserGroupService extends BaseService<UserGroup, UserGroupId, UserGroupRepo>{

    public UserGroupService(UserGroupRepo repository) {
        super(repository);
    }

    public List<UserGroup> findAllByIdGroupId(long groupId) {
        return repository.findAllByIdGroupId(groupId);
    }

    public List<UserGroup> findAllByIdUserIdAndRoleIn(long userId, List<UserRole> role) {
        return repository.findAllByIdUserIdAndRoleIn(userId, role);
    }

}
