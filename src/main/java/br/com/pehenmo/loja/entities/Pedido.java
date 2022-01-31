package br.com.pehenmo.loja.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data = LocalDate.now();
    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    /**
     * No JPA sempre que existir um relacionamento ManyToOne ou OneToOne, as queries de consultas
     * vão realizar um JOIN nessas tabelas, comportamento Eager (antecipadamente será carregado)
     *
     * Sempre que existir um relacionamento ManyToMany ou OneToMany, o processamento das queries não serão executadas
     * a menos que durante a execução do processo exista uma operação de consulta nessas entidades, se apresentando
     * desse modo o comportamento Lazy (tardio, somente "sob demanda")
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    /**
     * Desse modo não será criado uma terceira tabela apenas (pedidos, itens_pedido)
     * um pedido tem varios Itens (Many)
     * um Item está realcionado a apenas um pedido(One)
     *
     * pedido 1x -> n items_pedido
     * mappedBy = "pedido" indica que é um relacionamento bidirecional
     * e não deve ser criado uma terceira tabela
     *
     * cascade = CascadeType.ALL
     * Propagar as operações realizadas em uma entidade(no caso pedido como persiste, merge, remove) em seu relacionamento (no caso itens_pedido)
     * O cascade cria um efeito cascata nas operações realizadas em uma entidade
     */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {}

    public void adicionaItem(ItemPedido item){
        item.setPedido(this);
        itens.add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
