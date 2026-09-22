package negocio;

import model.MetaEconomia;
import persistencia.MetaEconomiaP;

public class MetaEconomiaNegocio {

    public void salvarMeta(MetaEconomia meta) throws Exception 
    {
        if (meta.getDescricao() == null || meta.getDescricao().trim().isEmpty()) {
            throw new Exception("A descrição da meta não pode estar vazia.");
        }
        if (meta.getValorObjetivo() <= 0) {
            throw new Exception("O valor objetivo deve ser maior que zero.");
        }
        if (meta.getValorPoupado() < 0) {
            throw new Exception("O valor poupado não pode ser negativo.");
        }

        MetaEconomiaP persistencia = new MetaEconomiaP();
        persistencia.salvar(meta);
    }
}