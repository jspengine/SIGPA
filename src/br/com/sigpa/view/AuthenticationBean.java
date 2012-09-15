package br.com.sigpa.view;



import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.primefaces.context.RequestContext;
import org.primefaces.json.JSONObject;

import br.com.sigpa.util.Util;
import br.com.sigpa.vo.VoUser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@ManagedBean(name = "loginBean")
public class AuthenticationBean {

	private String username;
	private String password;

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

	public void login(ActionEvent ae) {

		RequestContext rc = null;
		FacesMessage fm = null;
		boolean loggedin = false;
		ResourceBundle bundle = ResourceBundle.getBundle("appconfig");

		try {

			rc = RequestContext.getCurrentInstance();

			if ("".equals(username) || username == null) {
				fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Falha na autenticação",
						"Por favor o campo [USERNAME] é de preenchimento obrigatório.");
			} else if (("".equals(password) || password == null)) {
				fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Falha na autenticação",
						"Por favor o campo [SENHA] é de preenchimento obrigatório.");
			} else {
				// Faço a busca no webservice [rauth]

				ClientConfig config = new DefaultClientConfig();

				Client c = Client.create(config);

				WebResource wsService = c.resource(UriBuilder.fromUri(
						bundle.getString("rauth.endereco.restws")).build());

				ClientResponse clientResponse = wsService.path("service")
						.path("user")
						.path("login")
						.queryParam("username", this.username)
						.queryParam("password", Util.getHashMd5(this.password))
						.accept(MediaType.APPLICATION_JSON)
						.get(ClientResponse.class);

				if (clientResponse.getStatus() != 200)
					throw new Exception(
							"Não foi possível obter uma conexão com o Webservice. O Status do retorno é "
									+ clientResponse.getStatus());
				else {


					JSONObject json = new JSONObject(
							clientResponse.getEntity(String.class));
					
					int erro = (json.has("errocode"))
							? json
							.getInt("errocode") : 0;

					switch (erro) {
						case 1 :
							throw new Exception(
									"Por favor o campo [USERNAME] é de preenchimento obrigatório.");

						case 2 :
							throw new Exception(
									"Por favor o campo [SENHA] é de preenchimento obrigatório.");

						case 3 :
							throw new Exception(
									"Usuário ou senha não encontrados.");

						case 4 :

							throw new Exception("Usuário está Bloqueado.");

						case 100 :
							throw new Exception(json.getString("erromessage"));

						default :
							break;
					}

					VoUser user = new VoUser();

					user.setId(Integer.valueOf(json.getString("id")));
					user.setNome(json.getString("nome"));
					user.setUsername(json.getString("username"));
					user.setEmail(json.getString("email"));
					user.setFkIdPerfil(json.getInt("fkIdPerfil"));
					user.setImageUrl(json.getString("imageUrl"));

					HttpSession session = (HttpSession) FacesContext
							.getCurrentInstance().getExternalContext()
							.getSession(true);
					session.setAttribute("user", user);

					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("area-trabalho.jsf");

					// System.out.println(json.get("nome"));

				}

			}

		} catch (Exception e) {
			fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					":(   Desculpe-nos algum erro aconteceu.", e.toString());
		}

		if (fm != null)
			FacesContext.getCurrentInstance().addMessage(null, fm);

		rc.addCallbackParam("loggedIn", loggedin);
	}

	public void logout(ActionEvent ae) throws Exception {

		FacesContext context = null;
		HttpSession session = null;
		try {
			context = FacesContext.getCurrentInstance();
			session = (HttpSession) context.getExternalContext().getSession(
					false);
			session.invalidate();

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("user-auth.jsf");

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							":(   Desculpe-nos algum erro aconteceu.", e
									.toString()));

		}

	}

}
