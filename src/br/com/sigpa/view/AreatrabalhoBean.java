package br.com.sigpa.view;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.sigpa.vo.VoUser;

@ManagedBean(name = "areatrabalhoBean")
public class AreatrabalhoBean {

	private VoUser user;

	public VoUser getUser() {
		return user;
	}

	public void setUser(VoUser user) {
		this.user = user;
	}

	public AreatrabalhoBean() {

	}

	public void load() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		this.user = (VoUser) session.getAttribute("user");
	}

}
