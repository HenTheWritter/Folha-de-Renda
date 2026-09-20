package model;

import java.sql.Date;

public class Gasto {
    private int id;
    private int idUsuario;
    private String descricao;
    private double valor;
    private Date dataGasto;

    public Gasto() {}

    public int getId() 
    { 
        return id; 
    }
    
    public void setId(int id) 
    { 
        this.id = id; 
    }

    public int getIdUsuario() 
    { 
        return idUsuario; 
    }
    
    public void setIdUsuario(int idUsuario) 
    { 
        this.idUsuario = idUsuario; 
    }

    public String getDescricao() 
    { 
        return descricao; 
    }
    
    public void setDescricao(String descricao) 
    { 
        this.descricao = descricao; 
    }

    public double getValor() 
    { 
        return valor; 
    }
    
    public void setValor(double valor) 
    { 
        this.valor = valor; 
    }

    public Date getDataGasto() 
    { 
        return dataGasto; 
    }
    
    public void setDataGasto(Date dataGasto) 
    { 
        this.dataGasto = dataGasto; 
    }
}