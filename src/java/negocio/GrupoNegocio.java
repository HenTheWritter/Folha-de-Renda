package negocio;

import model.Grupo;
import persistencia.GrupoP;

public class GrupoNegocio {
    private GrupoP grupoP = new GrupoP();

    public boolean criarGrupo(Grupo grupo) throws Exception 
    {
        if (grupo.getNome() == null || grupo.getNome().trim().isEmpty()) 
        {
            throw new Exception("O nome do grupo é obrigatório.");
        }
        
        return grupoP.salvar(grupo);
    }
}