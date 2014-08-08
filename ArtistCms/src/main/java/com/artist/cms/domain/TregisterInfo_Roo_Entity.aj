// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.TregisterInfo;
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

privileged aspect TregisterInfo_Roo_Entity {
    
    declare @type: TregisterInfo: @Entity;
    
    declare @type: TregisterInfo: @Table(name = "tregisterinfo");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager TregisterInfo.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TregisterInfo.id;
    
    public Long TregisterInfo.getId() {
        return this.id;
    }
    
    public void TregisterInfo.setId(Long id) {
        this.id = id;
    }
    
    @Transactional("transactionManager")
    public void TregisterInfo.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void TregisterInfo.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TregisterInfo attached = TregisterInfo.findTregisterInfo(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void TregisterInfo.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void TregisterInfo.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public TregisterInfo TregisterInfo.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TregisterInfo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager TregisterInfo.entityManager() {
        EntityManager em = new TregisterInfo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TregisterInfo.countTregisterInfoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TregisterInfo o", Long.class).getSingleResult();
    }
    
    public static List<TregisterInfo> TregisterInfo.findAllTregisterInfoes() {
        return entityManager().createQuery("SELECT o FROM TregisterInfo o", TregisterInfo.class).getResultList();
    }
    
    public static TregisterInfo TregisterInfo.findTregisterInfo(Long id) {
        if (id == null) return null;
        return entityManager().find(TregisterInfo.class, id);
    }
    
    public static List<TregisterInfo> TregisterInfo.findTregisterInfoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TregisterInfo o", TregisterInfo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
