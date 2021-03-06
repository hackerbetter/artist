// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.UserInf;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserInf_Roo_Entity {
    
    declare @type: UserInf: @Entity;
    
    declare @type: UserInf: @Table(name = "tbl_userinf");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager UserInf.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long UserInf.id;
    
    public Long UserInf.getId() {
        return this.id;
    }
    
    public void UserInf.setId(Long id) {
        this.id = id;
    }
    
    @Transactional("transactionManager")
    public void UserInf.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void UserInf.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UserInf attached = UserInf.findUserInf(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void UserInf.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void UserInf.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public UserInf UserInf.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UserInf merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager UserInf.entityManager() {
        EntityManager em = new UserInf().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long UserInf.countUserInfs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UserInf o", Long.class).getSingleResult();
    }
    
    public static List<UserInf> UserInf.findAllUserInfs() {
        return entityManager().createQuery("SELECT o FROM UserInf o", UserInf.class).getResultList();
    }
    
    public static UserInf UserInf.findUserInf(Long id) {
        if (id == null) return null;
        return entityManager().find(UserInf.class, id);
    }
    
    public static List<UserInf> UserInf.findUserInfEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UserInf o", UserInf.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
