package br.com.pehenmo.loja.dao;

import br.com.pehenmo.loja.entities.Categoria;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoriaDAO {

    private EntityManager manager;

    public CategoriaDAO(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Inserir uma nova entidade no banco de dados
     * Coloca um objeto do estado transient para o estado managed
     * @param categoria
     */
    public void salvar(Categoria categoria){
        manager.persist(categoria);
    }

    /**
     * Na pratica não existe um comando para atualizar pois objetos no estado managed
     * são atualizados automaticamente após um commit/flush
     * @param categoria
     * @return
     */
    public Categoria atualizar(Categoria categoria){
        return manager.merge(categoria);
    }

    /**
     * Remove uma entidade do banco de dados
     * é importante garantir que obj está no estado managed antes
     * @param categoria
     */
    public void remover(Categoria categoria){
        categoria = this.atualizar(categoria);
        manager.remove(categoria);
    }

    public List<Categoria> buscarTodos(){
        return manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
    }
}
