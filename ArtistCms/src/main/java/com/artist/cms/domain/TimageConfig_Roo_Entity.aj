// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.TimageConfig;
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

privileged aspect TimageConfig_Roo_Entity {
    
    declare @type: TimageConfig: @Entity;
    
    declare @type: TimageConfig: @Table(name = "timageconfig");
    
    @PersistenceContext(unitName = "persistenceUnit")
    transient EntityManager TimageConfig.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TimageConfig.id;
    
    public Long TimageConfig.getId() {
        return this.id;
    }
    
    public void TimageConfig.setId(Long id) {
        this.id = id;
    }
    
    @Transactional("transactionManager")
    public void TimageConfig.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("transactionManager")
    public void TimageConfig.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TimageConfig attached = TimageConfig.findTimageConfig(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("transactionManager")
    public void TimageConfig.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("transactionManager")
    public void TimageConfig.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("transactionManager")
    public TimageConfig TimageConfig.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TimageConfig merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager TimageConfig.entityManager() {
        EntityManager em = new TimageConfig().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TimageConfig.countTimageConfigs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TimageConfig o", Long.class).getSingleResult();
    }
    
    public static List<TimageConfig> TimageConfig.findAllTimageConfigs() {
        return entityManager().createQuery("SELECT o FROM TimageConfig o", TimageConfig.class).getResultList();
    }
    
    public static TimageConfig TimageConfig.findTimageConfig(Long id) {
        if (id == null) return null;
        return entityManager().find(TimageConfig.class, id);
    }
    
    public static List<TimageConfig> TimageConfig.findTimageConfigEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TimageConfig o", TimageConfig.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
