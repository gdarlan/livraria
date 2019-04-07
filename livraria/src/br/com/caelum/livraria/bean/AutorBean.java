package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private Integer autorId;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void carregarAutorPelaId() {
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	public String gravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.getAutor());
			return "livro?faces-redirect=true";
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.getAutor());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autor alterado com sucesso!", null));
			return null;
		}

	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public void remove(Autor autor) {
		new DAO<Autor>(Autor.class).remove(autor);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autor removido com sucesso!", null));
	}

	public void carregar(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
}
