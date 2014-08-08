// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.Tplatform;
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

privileged aspect Tplatform_Roo_Entity {
    
    declare @type: Tplatform: @Entity;
    
    declare @type: Tplatform: @Table(name = "tplatform");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager Tplatform.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Tplatform.id;
    
    public Long Tplatform.getId() {
        return this.id;
    }
    
    public void Tplatform.setId(Long id) {
        this.id = id;
    }
    
    @Transactional("transactionManager")
    public void Tplatform.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void Tplatform.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Tplatform attached = Tplatform.findTplatform(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void Tplatform.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void Tplatform.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public Tplatform Tplatform.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Tplatform merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Tplatform.entityManager() {
        EntityManager em = new Tplatform().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Tplatform.countTplatforms() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Tplatform o", Long.class).getSingleResult();
    }
    
    public static List<Tplatform> Tplatform.findAllTplatforms() {
        return entityManager().createQuery("SELECT o FROM Tplatform o", Tplatform.class).getResultList();
    }
    
    public static Tplatform Tplatform.findTplatform(Long id) {
        if (id == null) return null;
        return entityManager().find(Tplatform.class, id);
    }
    
    public static List<Tplatform> Tplatform.findTplatformEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Tplatform o", Tplatform.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
