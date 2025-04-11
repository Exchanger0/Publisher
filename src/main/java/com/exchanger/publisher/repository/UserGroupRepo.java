package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.UserGroup;
import com.exchanger.publisher.model.UserRole;
import com.exchanger.publisher.model.key.UserGroupId;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepo extends ListCrudRepository<UserGroup, UserGroupId> {
    List<UserGroup> findAllByIdGroupId(long groupId);

    List<UserGroup> findAllByIdUserIdAndRoleIn(long userId, List<UserRole> role);
}
