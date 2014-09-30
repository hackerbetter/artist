// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artist.domain;

import com.hackerbetter.artist.domain.Tmessage;
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

privileged aspect Tmessage_Roo_Entity {
    
    declare @type: Tmessage: @Entity;
    
    declare @type: Tmessage: @Table(name = "tmessage");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager Tmessage.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Tmessage.id;
    
    public Long Tmessage.getId() {
        return this.id;
    }
    
    public void Tmessage.setId(Long id) {
        this.id = id;
    }
    
    @Transactional("transactionManager")
    public void Tmessage.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void Tmessage.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Tmessage attached = Tmessage.findTmessage(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void Tmessage.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void Tmessage.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public Tmessage Tmessage.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Tmessage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Tmessage.entityManager() {
        EntityManager em = new Tmessage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Tmessage.countTmessages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Tmessage o", Long.class).getSingleResult();
    }
    
    public static List<Tmessage> Tmessage.findAllTmessages() {
        return entityManager().createQuery("SELECT o FROM Tmessage o", Tmessage.class).getResultList();
    }
    
    public static Tmessage Tmessage.findTmessage(Long id) {
        if (id == null) return null;
        return entityManager().find(Tmessage.class, id);
    }
    
    public static List<Tmessage> Tmessage.findTmessageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Tmessage o", Tmessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}