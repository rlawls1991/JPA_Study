package jpa.study.repository;

import jpa.study.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /*
     @PersistenceContext
     private EntityManager em;

     PersistenceContext는 JPA Manager을 자동으로 등록해준다.
     Spring boot 에서는 @PersistenceContext을 @Autowired로 바꿀 수 있다.
     그렇기 때문에 final 변수만 자동으로 생성자를 만들어 주는
     lombok의 @RequiredArgsConstructor을 사용하게 된다면 아래와 같이 사용 가능하다.
    */
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",
                Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}