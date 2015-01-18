package br.com.debico.core.dao.jpa;

import java.util.List;


/**
 * Trata das operações com o banco de dados sob o ponto de vista do JPA.
 * 
 * @author r_fernandes
 *
 * @param <E>
 * @param <I>
 * 
 */
public interface JPADAO<E, I> {

    E selecionarPorId(I id);
    
    void inserir(E e);
    
    /**
     * Utilizar sempre o objeto retornado por este método a partir da chamada.
     * <p/>
     * <code>merge will return attached instance letting the given one unchanged.</code>
     * 
     * @see <a href="http://stackoverflow.com/questions/22459233/hibernate-update-vs-jpa-merge-on-detached-instance">hibernate update vs JPA merge on detached instance</a>
     * @param e
     * @return
     */
    E atualizar(E e);
    
    void excluir(E e);
  
    List<E> selecionarTodos();
    
}
