package br.com.pehenmo.loja;

import br.com.pehenmo.loja.dao.CategoriaDAO;
import br.com.pehenmo.loja.dao.ProdutoDAO;
import br.com.pehenmo.loja.entities.Categoria;
import br.com.pehenmo.loja.entities.Produto;
import br.com.pehenmo.loja.factories.ConnectionFactory;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

/**
 * JPA pede para mapear as entidades dentro do XML conforme exemplo abaixo:
 * <class>br.com.pehenmo.loja.entities.Produto</class>
 * Porém como estamos utilizando o Hibernate isso não é necessario,
 * pois ele já consegue pegar automaticamente essa configuração
 *
 * //@Enumerated(EnumType.STRING) quando for Enum e queremos pegar o valor String ao inves da ordem (int)
 *
 */
public class Teste {
    public static void main(String[] args) {

        Categoria celulares = new Categoria("CELULARES"); // objeto no estado transient
        Produto produto = new Produto("Xiomi Redmi", "64GB 17'", BigDecimal.valueOf(3200), celulares);

        EntityManager em = ConnectionFactory.getEntityManager();
        cadastrarCategoria(celulares, em);
        cadastrarProduto(produto, em);

        //consultarPorId(1l, em);
        //consultarTodosProdutos(em);
        consultarPrecoDoProduto("Xiomi Redmi", em);
    }

    public static void consultarPrecoDoProduto(String nome, EntityManager em){
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        BigDecimal preco = produtoDAO.buscarPrecoPeloNomeProduto(nome);
        System.out.println("Produto: " + nome + " Preço: "+ preco);
    }

    public static void consultarProdutoPeloNomeCategoria(String nome, EntityManager em){
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        List<Produto> produtos = produtoDAO.buscarPeloNomeCategoria(nome);
        produtos.forEach(p -> System.out.println(p));
    }

    public static void consultarProdutoPeloNome(String nome, EntityManager em){
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        List<Produto> produtos = produtoDAO.buscarPeloNome(nome);
        produtos.forEach(p -> System.out.println(p));
    }

    public static void consultarTodosProdutos(EntityManager em){
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        List<Produto> produtos = produtoDAO.buscarTodos();
        produtos.forEach(p -> System.out.println(p));
    }

    public static void consultarPorId(Long id, EntityManager em){
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        Produto produto = produtoDAO.buscar(id);
        System.out.println(produto);
    }

    public static void cadastrarCategoria(Categoria categoria, EntityManager em){
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        em.getTransaction().begin();
        categoriaDAO.salvar(categoria);
        em.getTransaction().commit();
    }

    public static void cadastrarProduto(Produto produto, EntityManager em){

        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        em.getTransaction().begin();
        produtoDAO.salvar(produto);

        // realiza o update da entidade no banco de dados
        // pois está no estado managed
        //produto.setDescricao("32GB 15'");
        //em.clear();//em.close() //faz com que os objetos fiquem no estado detached
        //produto = produtoDAO.atualizar(produto); //merge() retorna um novo objeto detached para o estado managed
        //produto.setNome("Xiomi Redmi 2");  //realiza o update da entidade no banco de dados pois está no estado managed
        //em.flush();
        //produtoDAO.remover(produto); //delete no obj no banco de dados
        em.getTransaction().commit(); // em.flush(); leva todas as alterações para o BD

    }
}
