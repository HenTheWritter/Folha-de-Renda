package model;

public class MetaEconomia {
    private int id;
    private int idUsuario;
    private String descricao;
    private double valorObjetivo;
    private double valorPoupado;

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

    public double getValorObjetivo() 
    { 
        return valorObjetivo; 
    }
    
    public void setValorObjetivo(double valorObjetivo) 
    { 
        this.valorObjetivo = valorObjetivo; 
    }

    public double getValorPoupado() 
    { 
        return valorPoupado; 
    }
    
    public void setValorPoupado(double valorPoupado) 
    { 
        this.valorPoupado = valorPoupado; 
    }
}