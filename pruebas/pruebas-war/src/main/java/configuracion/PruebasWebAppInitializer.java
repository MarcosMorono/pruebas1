package configuracion;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class PruebasWebAppInitializer implements WebApplicationInitializer 
{
	@Value("${apps_temp_path:UnknownAppsTemp}")
	private String appsTempPath;
	private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; 
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		// 1: Creamos un context de aplicacion: root context
		rootContext.register(RootConfig.class);
		// 2: configuration beans en este contexto
		rootContext.setServletContext(servletContext);
		// COC para el archetipo de la AMTEGA

		// Clase propia de inicialización de contexto de Spring
	

		/*
		 * Carga el "Spring ROOT Application Context 3: Forma de ligar Spring con la Web
		 * y poder acceder al ServletContext, el cual es el objeto de aplicacion web por
		 * completo a nivel de J2EE=
		 * >"Ligamos el contexto propio de JEE de la aplicacin web con el contexto de Spring recien creado"
		 */
		servletContext.addListener(new ContextLoaderListener(rootContext));
		/* cargamos el contexto */

		/* definimos un perfil a nivel de servlet */

		servletContext.setInitParameter("spring.profiles.default", "local");

		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		// 1: Creamos un context de aplicacion wev
		webContext.setParent(rootContext);
		/*
		 * Por tanto, la forma habitual de trabajar es usar un fichero XML o clase de
		 * configuracion JAVA para los beans de la capa web y otro (u otros) distinto
		 * para los de la capa de negocio y DAOs. Spring establece una jerarquia de
		 * contextos de modo que el contexto e la capa web hereda automaticamente los
		 * otros beans, lo que nos permite referenciar los objetos de negocio en nuestro
		 * codigo MVC
		 */

		webContext.register(WebMvcConfig.class);
		// 2: configuration beans de capa web como puede ser Sprong MVC en este contexto

		/*
		 * Lo que hace la siguiente instruccion es registar dinamicamente un servlet. a.
		 * Fijate que es lo que hacia estaticamente en el web.xml b. Fijate que mantengo
		 * segun COC el nombre tipico que espera Spring para este servlet c. Fijate que
		 * este servlet se genera para nuetro contenedor: contenedor.addServlet(....) d.
		 * Fijate que en el constructor de DispatcherServlet le pasamos el contexto de
		 * Spring. e. fijate que podemos poner mas de una terminacion que debe ser
		 * atendida por el.
		 */

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(webContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.setInitParameter("spring.profiles.default", "local");
		/*
		 * Configuro el servlet a. Definimos el perfil de SPring por defecto:Su
		 * utililidad se explica en otra parte del manual. b. Hacemos que l servlet se
		 * cargue de prmiero tras arrancar la apliacion (puede haber registrados mas
		 * servlets) c. Indicamos a que Url's va atender este servlet Dispatcher. En
		 * este caso es a todas.
		 */

		// Servlet de apache CXF
		/*
		 * ServletRegistration.Dynamic servletApacheCxf =
		 * servletContext.addServlet("cxf", new CXFServlet());
		 * servletApacheCxf.setLoadOnStartup(2);
		 * servletApacheCxf.addMapping("/services/*");
		 */
		/*
		 * Configuro el filtro de Spring security: Atendiendo a SRP principo de diseno
		 * esto lo llevare a cabo en una clase especial que explicare os despues.
		 * servletContext.addFilter("securityFilter", new
		 * DelegatingFilterProxy("springSecurityFilterChain"))
		 * .addMappingForUrlPatterns(null, false, "/*");
		 * 
		 */
		
		// configuracion para subida de archivos
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(appsTempPath, 
				MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);        
		dispatcher.setMultipartConfig(multipartConfigElement);

	}

}
