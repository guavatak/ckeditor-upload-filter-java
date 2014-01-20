package com.labsody.lab.ckeditor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CkServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(CkServlet.class);

    private static final String IMAGE_BASE_DIR_KEY = "ck.image.dir";
    private static final String IMAGE_BASE_URL_KEY = "ck.image.url";
    private static final String IMAGE_ALLOW_TYPE_KEY = "ck.image.type.allow";
    private static final String IMAGE_SAVE_CLASS_KEY = "ck.image.save.class";

    private CkImageSaver ckImageSaver;
	
	@Override
	public void init() throws ServletException {
		String properties = getServletConfig().getInitParameter("properties");
		InputStream inStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(properties);
		Properties props = new Properties();
		try {
			props.load(inStream);
		} catch (IOException e) {
			log.error(e);
		}

        String imageBaseDir = (String) props.get(IMAGE_BASE_DIR_KEY);
        String imageDomain = (String) props.get(IMAGE_BASE_URL_KEY);


        String[] allowFileTypeArr = null;
        String allowFileType = (String) props.get(IMAGE_ALLOW_TYPE_KEY);
        if(StringUtils.isNotBlank(allowFileType)) {
            allowFileTypeArr = StringUtils.split(allowFileType, ",");
        }

        String saveManagerClass = (String) props.get(IMAGE_SAVE_CLASS_KEY);

        ckImageSaver = new CkImageSaver(imageBaseDir, imageDomain, allowFileTypeArr, saveManagerClass);
	}
	
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

	}

	/**
	 * Passes a POST request to the dispatcher.
	 * 
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the request
	 * @throws ServletException
	 *             if the request for the POST could not be handled
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
        ckImageSaver.saveAndReturnUrlToClient(request, response);
	}
}
