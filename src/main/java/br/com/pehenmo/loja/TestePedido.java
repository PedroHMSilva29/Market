package br.com.pehenmo.loja;

import br.com.pehenmo.loja.dao.ClienteDAO;
import br.com.pehenmo.loja.dao.PedidoDAO;
import br.com.pehenmo.loja.entities.*;
import br.com.pehenmo.loja.factories.ConnectionFactory;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class TestePedido {

    public static void main(String[] args) {

        EntityManager em = ConnectionFactory.getEntityManager();

        Cliente cliente = new Cliente("Pedro Henrique Moreira", "38565550051");
        Categoria celulares = new Categoria("CELULARES");
        Produto xiomi = new Produto("Xiomi Redmi", "64GB 17'", BigDecimal.valueOf(3200), celulares);
        Produto galaxy = new Produto("Galaxy", "64GB 17'", BigDecimal.valueOf(5000), celulares);

        TesteProduto.cadastrarCategoria(celulares, em);
        TesteProduto.cadastrarProduto(xiomi, em);
        TesteProduto.cadastrarProduto(galaxy, em);
        cadastrarCliente(cliente, em);

        Pedido pedido1 = new Pedido(cliente);

        ItemPedido item = new ItemPedido(2, pedido1, xiomi);
        ItemPedido item2 = new ItemPedido(5, pedido1, galaxy);
        pedido1.adicionaItem(item);
        pedido1.adicionaItem(item2);

        cadastrarPedido(pedido1, em);
        //consultaValorTotal(em);
        //geraRelatorioVendas(em);

        Pedido p = getPedido(1l, em);

        em.close();

        System.out.println(p.getCliente());

    }

    public static Pedido getPedido(Long id, EntityManager em ){
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        return pedidoDAO.getPedido(id);
    }

    public static void geraRelatorioVendas(EntityManager em ){
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.relatorioVendas().forEach(System.out::println);
    }

    public static void consultaValorTotal(EntityManager em ){
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        System.out.println(pedidoDAO.valorTotalPedido());
    }

    public static void cadastrarCliente(Cliente cliente, EntityManager em ){
        ClienteDAO clienteDAO = new ClienteDAO(em);
        em.getTransaction().begin();
        clienteDAO.cadastrar(cliente);
        em.getTransaction().commit();
    }

    public static void cadastrarPedido(Pedido pedido, EntityManager em){
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        em.getTransaction().begin();
        pedidoDAO.cadastrar(pedido);
        em.getTransaction().commit();
    }
}
