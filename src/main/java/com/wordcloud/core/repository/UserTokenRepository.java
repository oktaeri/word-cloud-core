package com.wordcloud.core.repository;

import com.wordcloud.core.model.UserToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserTokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public UserToken postToken(UserToken token) {
        if (token.getId() == null) {
            em.persist(token);
        } else {
            em.merge(token);
        }

        return token;
    }

    public UserToken getTokenByText(String tokenText) {
        TypedQuery<UserToken> query = em.createQuery(
                "select ut from UserToken ut where ut.token = :token",
                UserToken.class
        );

        query.setParameter("token", tokenText);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
