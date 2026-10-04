package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Gasto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int idUsuario;
    private String descricao;
    private BigDecimal valor;
    private Date dataGasto;

    public Gasto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Date getDataGasto() { return dataGasto; }
    public void setDataGasto(Date dataGasto) { this.dataGasto = dataGasto; }
}
