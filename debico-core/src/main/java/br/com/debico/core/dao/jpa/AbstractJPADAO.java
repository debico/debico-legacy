package br.com.debico.core.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação da API para tratar das operações com o banco de dados.
 * 
 * @author r_fernandes
 *
 * @param <E>
 * @param <I>
 * 
 * @see <a href="http://stackoverflow.com/questions/161224/what-are-the-differences-between-the-different-saving-methods-in-hibernate">What are the differences between the different saving methods in Hibernate?</a>
 * @see <a href="http://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/objectstate.html">Hibernate: Chapter 11. Working with objects</a>
 * @see <a href="http://javahotpot.blogspot.in/2014/03/hibernate-difference-between-update-and.html">Hibernate Difference between update() and merge()</a>
 * @see <a href="http://techblog.bozho.net/?p=266">How Does Merge Work in JPA (and Hibernate)</a>
 */
public class AbstractJPADAO<E extends Serializable, I> implements JPADAO<E, I> {

    private Class<E> entityClass;
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractJPADAO.class);

    protected AbstractJPADAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void excluir(E e) {
        this.entityManager.remove(e);
    }

    public E atualizar(E e) {
       return this.entityManager.merge(e);
    }
    
    public void inserir(E e) {
        this.entityManager.persist(e);        
    }
   
    public E selecionarPorId(I id) {
        return this.entityManager.find(this.entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<E> selecionarTodos() {
        return this.entityManager
                .createQuery(String.format("from %s", this.entityClass.getName()))
                .getResultList();
    }

}
