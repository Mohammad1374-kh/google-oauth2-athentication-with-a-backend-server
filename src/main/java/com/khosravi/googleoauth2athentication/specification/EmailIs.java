package com.khosravi.googleoauth2athentication.specification;

import com.khosravi.googleoauth2athentication.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.khosravi.googleoauth2athentication.entity.User.Fields.email;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@RequiredArgsConstructor(access = PRIVATE)
public class EmailIs implements Specification<User> {
    private final String emailToMatch;
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.get(email),
                emailToMatch);
    }

    public static Specification<User> emailIs(String emailToMatch) {
        if (ofNullable(emailToMatch).orElse(EMPTY).isBlank())
            return Specification.where(null);

        return new EmailIs(emailToMatch);
    }

}
