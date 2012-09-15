package br.com.sigpa.view;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.primefaces.event.FileUploadEvent;

import br.com.sigpa.vo.VoUser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

@ManagedBean(name = "userBean")
public class UserManagmentBean {

	public void atualizarImagem(FileUploadEvent fue) {

		// RequestContext rc = null;
		FacesMessage fm = null;

		ResourceBundle bundle = ResourceBundle.getBundle("appconfig");

		try {

			// multiPart.bodyPart(filePart);
			//
			//
			// ClientResponse response = null;
			// response =
			// webResource.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,
			// multiPart);

			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);

			VoUser user = (VoUser) session.getAttribute("user");

			// ClientConfig config = new DefaultClientConfig();
			//
			// Client c = Client.create(config);
			//
			// WebResource wsService = c.resource(UriBuilder.fromUri(
			// bundle.getString("rauth.endereco.restws")).build());

			// FormDataBodyPart fdbp = new FormDataBodyPart("content",
			// new ByteArrayInputStream(fue.getFile().getContents()),
			// MediaType.APPLICATION_OCTET_STREAM_TYPE);
			//
			// FormDataMultiPart frm = new FormDataMultiPart();
			//
			// frm.field("fileName", fue.getFile().getFileName());
			// frm.bodyPart(fdbp);

			// FormDataMultiPart imagePart = new
			// FormDataMultiPart().field("file",
			// fue.getFile().getInputstream(),
			// MediaType.MULTIPART_FORM_DATA_TYPE);

			// MultiPart multipart = new MultiPart().bodyPart(new BodyPart(fue
			// .getFile().getContents(),
			// MediaType.APPLICATION_OCTET_STREAM_TYPE));
			//
			// WebResource resource = wsService.path("service")
			// .path("user").path("savepicture")
			// .path(String.valueOf(user.getId()));
			//
			// resource.type(new MediaType("multipart", "mixed"));
			// // resource.post();
			//
			// ClientResponse clientResponse =
			// resource.post(ClientResponse.class,
			// multipart);

			// .type("multipart/mixed");
			// .accept(MediaType.TEXT_PLAIN)

			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			client.addFilter(new LoggingFilter());
			WebResource webResource = client
					.resource("http://localhost:8080/r_auth/service/savepicture/1");
			FormDataMultiPart fdmp = new FormDataMultiPart();

			if (fue.getFile() != null) {

				fdmp.bodyPart(new BodyPart(fue.getFile().getContents(),
						MediaType.APPLICATION_OCTET_STREAM_TYPE));
			}
			fdmp.bodyPart(new FormDataBodyPart("name", "ingredientName"));
			fdmp.bodyPart(new FormDataBodyPart("description", "ingredientDesc"));
			ClientResponse response = webResource.type(
					MediaType.MULTIPART_FORM_DATA_TYPE).post(
					ClientResponse.class, fdmp);
			String string = response.getEntity(String.class);

	         System.out.println(string);

// if (clientResponse.getStatus() != 200) {
			// throw new Exception(
			// "Não foi possível obter uma conexão com o Webservice. O Status do retorno é "
			// + clientResponse.getStatus());
			// } else {
			// JSONObject json = new JSONObject(
			// clientResponse.getEntity(String.class));
			//
			// int erro = (json.has("errocode")) ? json.getInt("errocode") : 0;
			//
			// switch (erro) {
			// case 5 :
			// throw new Exception(
			// "Arquivo inválido, somente estas são as extensões permitidas [jpg, gif, png].");
			//
			// case 6 :
			// throw new Exception(
			// "Por Algum motivo não foi possível salvar a imagem.");
			//
			// case 100 :
			// throw new Exception(json.getString("erromessage"));
			//
			// default :
			// break;
			// }
			//
			// fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
			// "Foto foi Atualizada",
			// "Sua Foto foi atualizada com sucesso.");

			// }

		} catch (Exception e) {

			fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					":(   Desculpe-nos algum erro aconteceu.", e.toString());

			e.printStackTrace();
		}

		if (fm != null)
			FacesContext.getCurrentInstance().addMessage(null, fm);
	}

}
