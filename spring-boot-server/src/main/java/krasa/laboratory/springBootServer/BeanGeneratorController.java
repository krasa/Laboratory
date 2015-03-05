package krasa.laboratory.springBootServer;

import java.lang.ref.WeakReference;

import krasa.laboratory.springBootServer.modularContext.BeanGeneratorFacade;
import krasa.laboratory.springBootServer.modularContext.ContextReloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BeanGeneratorController {

	@Autowired
	ApplicationContext app;
	WeakReference<BeanGeneratorFacade> oldBean;

	@RequestMapping({ "/hello", "/helloBeans" })
	@ResponseBody
	public String helloBeans() {
		BeanGeneratorFacade bean = app.getBean(BeanGeneratorFacade.class);
		return bean.helloBeans();
	}

	@RequestMapping({ "/refreshBeans" })
	@ResponseBody
	public String refreshBeans() {
		ContextReloader bean = app.getBean(ContextReloader.class);

		setWeakReferenceToCheckGcOnAspectedClass();

		bean.refreshContext();

		System.gc();

		if (oldBean.get() == null) {
			return "done, old bean was garbage collected";
		} else {
			return "ERROR, old bean was not garbage collected";
		}
	}

	private void setWeakReferenceToCheckGcOnAspectedClass() {
		BeanGeneratorFacade bean1 = app.getBean(BeanGeneratorFacade.class);
		oldBean = new WeakReference<BeanGeneratorFacade>(bean1);
	}
}