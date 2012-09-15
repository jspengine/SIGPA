package br.com.sigpa.view;

import javax.faces.bean.ManagedBean;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

@ManagedBean(name = "menuBean")
public class AuthMenu {
	private MenuModel model;

	public AuthMenu() {
		model = new DefaultMenuModel();

		// First submenu
		Submenu sbmGErenciarEnquetes = new Submenu();
		sbmGErenciarEnquetes.setLabel("GERENCIAMENTOS DE ENQUETE");
		// submenu.setIcon("ui-icon-contact");

		MenuItem mnuEnquete = new MenuItem();
		mnuEnquete.setValue("Gerenciar Enquetes");
		mnuEnquete.setUrl("manter-enquete-montar.jsf");
		mnuEnquete.setIcon("ui-icon-document");
		sbmGErenciarEnquetes.getChildren().add(mnuEnquete);

		MenuItem mnuPerguntas = new MenuItem();
		mnuPerguntas.setValue("Gerenciar Perguntas");
		mnuPerguntas.setUrl("manter-enquete-perguntas.jsf");
		mnuPerguntas.setIcon("ui-icon-help");
		sbmGErenciarEnquetes.getChildren().add(mnuPerguntas);

		MenuItem mnuRespostas = new MenuItem();
		mnuRespostas.setValue("Gerenciar Respostas");
		mnuRespostas.setUrl("manter-enquete-respostas.jsf");
		mnuRespostas.setIcon("ui-icon-document-b");
		sbmGErenciarEnquetes.getChildren().add(mnuRespostas);

		model.addSubmenu(sbmGErenciarEnquetes);

		// Second submenu
		// submenu = new Submenu();
		// submenu.setLabel("Dynamic Submenu 2");
		//
		// item = new MenuItem();
		// item.setValue("Dynamic Menuitem 2.1");
		// item.setUrl("#");
		// submenu.getChildren().add(item);
		//
		// item = new MenuItem();
		// item.setValue("Dynamic Menuitem 2.2");
		// item.setUrl("#");
		// submenu.getChildren().add(item);
		//
		// model.addSubmenu(submenu);
	}

	public MenuModel getModel() {
		return model;
	}
}
