package app.devmedia.com.br.appdevmedia.entity;

public class Profissao {

	private int cod;
	private String descricao;
	private String subDescricao;
	private String urlImg;

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getSubDescricao() {
		return subDescricao;
	}

	public void setSubDescricao(String subDescricao) {
		this.subDescricao = subDescricao;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
