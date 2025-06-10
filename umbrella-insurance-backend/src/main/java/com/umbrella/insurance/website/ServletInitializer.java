package com.umbrella.insurance.website;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p>ServletInitializer class.</p>
 *
 * @author marcus
 * @version $Id: $Id
 * @since 0.0.21
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/** {@inheritDoc} */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebsiteApplication.class);
	}

}
