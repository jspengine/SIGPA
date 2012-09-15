package br.com.sigpa.vo;


public class VoUser {

	private int id;
	private String nome;
	private String email;
	private String username;
	private String password;
	private boolean bloqueado;
	private int fkIdPerfil;
	private String imageUrl;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public int getFkIdPerfil() {
		return fkIdPerfil;
	}
	public void setFkIdPerfil(int fkIdPerfil) {
		this.fkIdPerfil = fkIdPerfil;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
