package br.com.pehenmo.loja.dao;

import br.com.pehenmo.loja.entities.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {

    private EntityManager manager;

    public ProdutoDAO(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Inserir uma nova entidade no banco de dados
     * Coloca um objeto do estado transient para o estado managed
     * @param produto
     */
    public void salvar(Produto produto){
        manager.persist(produto);
    }

    /**
     * Na pratica não existe um comando para atualizar pois objetos no estado managed
     * são atualizados automaticamente após um commit/flush
     * @param produto
     * @return
     */
    public Produto atualizar(Produto produto){
        return manager.merge(produto);
    }

    /**
     * Remove uma entidade do banco de dados
     * é importante garantir que obj está no estado managed antes
     * @param produto
     */
    public void remover(Produto produto){
        produto = this.atualizar(produto);
        manager.remove(produto);
    }

    public Produto buscar(Long id){
        return manager.find(Produto.class, id);
    }

    public List<Produto> buscarTodos(){
        String jpql = "SELECT p FROM Produto p";
        return manager.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPeloNome(String nome){
        String jpql = "SELECT p FROM Produto p WHERE p.nome= :nome";
        return manager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPeloNomeCategoria(String nome){
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome= :nome";
        return manager.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPeloNomeProduto(String nome){
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome= :nome";
        return manager.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }
}
