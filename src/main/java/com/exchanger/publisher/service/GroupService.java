package com.exchanger.publisher.service;

import com.exchanger.publisher.model.Group;
import com.exchanger.publisher.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.LinkOption;
import java.util.List;

@Service
@Transactional
public class GroupService extends BaseService<Group, Long, GroupRepository> {

    @Autowired
    public GroupService(GroupRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public List<Group> findByCreatorId(long creatorId) {
        return repository.findByCreatorId(creatorId);
    }
}
