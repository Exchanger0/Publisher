package com.exchanger.publisher.jpa;

import com.exchanger.publisher.model.Post;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> filterByCriteria(String title, String tag, Integer groupId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            List<Predicate> titleOrTagPredicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                titleOrTagPredicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (tag != null && !tag.isEmpty()) {
                titleOrTagPredicates.add(criteriaBuilder.like(
                        criteriaBuilder.function("ARRAY_TO_STRING", String.class, root.get("tags"), criteriaBuilder.literal(",")),
                        "%" + tag + "%"
                ));
            }
            if (!titleOrTagPredicates.isEmpty())
                predicates.add(criteriaBuilder.or(titleOrTagPredicates.toArray(new Predicate[0])));

            if (groupId != null) {
                Predicate p1 = criteriaBuilder.isNotNull(root.get("group"));
                Predicate p2 = criteriaBuilder.equal(root.get("group").get("id"), groupId);
                predicates.add(criteriaBuilder.and(p1, p2));
            }

            if (!predicates.isEmpty()) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            return criteriaBuilder.conjunction();
        };
    }
}
