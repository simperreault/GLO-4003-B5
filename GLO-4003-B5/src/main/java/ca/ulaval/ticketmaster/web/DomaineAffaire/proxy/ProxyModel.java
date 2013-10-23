package ca.ulaval.ticketmaster.web.DomaineAffaire.proxy;

import org.springframework.ui.Model;

public class ProxyModel {

    private Model model;

    private ProxyModel(Model model) {
	this.model = model;
    }

    static public ProxyModel create(Model model) {
	return new ProxyModel(model);
    }

    public void addAttribute(String string, Object obj) {
	this.model.addAttribute(string, obj);
    }
}
