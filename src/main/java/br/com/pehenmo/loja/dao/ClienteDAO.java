package br.com.pehenmo.loja.dao;

import br.com.pehenmo.loja.entities.Cliente;
import br.com.pehenmo.loja.entities.Pedido;
import br.com.pehenmo.loja.entities.Produto;

import javax.persistence.EntityManager;

public class ClienteDAO {

    private EntityManager manager;

    public ClienteDAO(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Inserir uma nova entidade no banco de dados
     * Coloca um objeto do estado transient para o estado managed
     * @param cliente
     */
    public void cadastrar(Cliente cliente){
        manager.persist(cliente);
    }

    public Cliente buscarPeloId(Long id){
        return manager.find(Cliente.class, id);
    }
}
