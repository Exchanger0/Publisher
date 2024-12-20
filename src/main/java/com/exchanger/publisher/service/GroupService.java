package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.repository.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GroupService extends BaseService<Group, Long, GroupRepo> {

    @Autowired
    public GroupService(GroupRepo repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public List<Group> findAllByCreatorId(long creatorId) {
        return repository.findAllByCreatorId(creatorId);
    }
}
