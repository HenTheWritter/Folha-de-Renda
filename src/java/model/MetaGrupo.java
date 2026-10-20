package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MetaGrupo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int idGrupo;
    private String descricao;
    private BigDecimal valorObjetivo;
    private BigDecimal valorPoupado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(BigDecimal valorObjetivo) { this.valorObjetivo = valorObjetivo; }

    public BigDecimal getValorPoupado() { return valorPoupado; }
    public void setValorPoupado(BigDecimal valorPoupado) { this.valorPoupado = valorPoupado; }
}