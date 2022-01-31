package br.com.pehenmo.loja.dao;

import br.com.pehenmo.loja.entities.Pedido;
import br.com.pehenmo.loja.entities.Produto;
import br.com.pehenmo.loja.va.RelatorioVendas;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

    private EntityManager manager;

    public PedidoDAO(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Inserir uma nova entidade no banco de dados
     * Coloca um objeto do estado transient para o estado managed
     * @param pedido
     */
    public void cadastrar(Pedido pedido){
        manager.persist(pedido);
    }

    public BigDecimal valorTotalPedido(){
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return manager.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioVendas> relatorioVendas(){
        String jpql = "SELECT new br.com.pehenmo.loja.va.RelatorioVendas(" +
                "produto.nome, " +
                "SUM(item.quantidade), " +
                "MAX(pedido.data)) " +
                "FROM Pedido pedido " +
                "JOIN pedido.itens item " +
                "JOIN item.produto produto " +
                "GROUP BY produto.nome " +
                "ORDER BY item.quantidade DESC";
        return manager.createQuery(jpql, RelatorioVendas.class)
                .getResultList();
    }

    /**
     * JOIN FETCH p.cliente
     * Força o JPA trazer consigo na consulta um relacionamento
     * Pois definimos que o carregamento para esse relacionamento seria lazy
     * 
     * @param id
     * @return
     */
    public Pedido getPedido(Long id){
        String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id=:id";
        return manager.createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
