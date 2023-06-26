package model.bo;

import java.util.List;

import model.dao.AgendamentoDAO;
import model.exception.CampoInvalidoException;
import model.seletor.EnderecoSeletor;
import model.vo.Agendamento;

public class AgendamentoBO {
	
private AgendamentoDAO dao = new AgendamentoDAO();
	
	public Agendamento inserir(Agendamento novoAgendamento) {
		return dao.inserir(novoAgendamento);
	}
	
	public boolean atualizar(Agendamento agendamentoAlterado){
		return dao.atualizar(agendamentoAlterado);
	}
	
	public boolean excluir(int id) throws CampoInvalidoException {	
		return dao.excluir(id);
	}
	
	public Agendamento consultarPorId(int id) {
		return dao.consultarPorId(id);
	}
	
	public List<Agendamento> consultarTodos() {
		return dao.consultarTodos();
	}
	
	public int contarTotalRegistros(Agendamento agendamento) {
		return dao.contarTotalRegistros(agendamento);
	}

	public boolean recusar(int id) throws CampoInvalidoException {
		return dao.recusar(id);
	}
	
	public boolean aceitar(int id) throws CampoInvalidoException {
		return dao.aceitar(id);
	}

}
