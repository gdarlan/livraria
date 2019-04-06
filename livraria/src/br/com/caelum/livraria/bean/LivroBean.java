package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();

	public Livro getLivro() {
		return livro;
	}

	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		FacesContext context = FacesContext.getCurrentInstance();

		if (livro.getAutores().isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Livro deve ter pelo menos um autor", null));
			return;
		}
		new DAO<Livro>(Livro.class).adiciona(this.livro);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Livro gravado com sucesso!", null));
		limpar();

	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}
	
	public void limpar() {
		this.livro = new Livro();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}


}
