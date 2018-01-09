package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.PathUtil;
import com.wiatec.panel.common.utils.XmlReader;
import net.authorize.Environment;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author patrick
 */
public class AuthorizeConfig {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizeConfig.class);

    public static void main (String [] args){
        AuthorizeConfig.init();
    }

    public static void init(){
        String xmlPath = PathUtil.getResourcePath() + "authorize.xml";
        logger.debug(xmlPath);
        config(xmlPath);
    }

    public static void init(HttpServletRequest request){
        String xmlPath = PathUtil.getRealPath(request) + "WEB-INF/classes/authorize.xml";
        logger.debug(xmlPath);
        config(xmlPath);
    }

    private static void config(String xmlPath){
        XmlReader xmlReader = new XmlReader(xmlPath);
        Element root = xmlReader.getRoot();
        Element sandbox = root.element("sandbox");
        boolean isSandbox = Boolean.parseBoolean(sandbox.attribute("active").getValue());
        if(isSandbox) {
            ApiOperationBase.setEnvironment(Environment.SANDBOX);
            String apiId = sandbox.element("api_id").getTextTrim();
            String transactionKey = sandbox.element("transaction_key").getTextTrim();
            MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
            merchantAuthenticationType.setName(apiId);
            merchantAuthenticationType.setTransactionKey(transactionKey);
            ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
            logger.debug("Authorize environment: sandbox");
            logger.debug("api id: {}", apiId);
            logger.debug("transaction key: {}", transactionKey);
            return;
        }
        Element production = root.element("production");
        boolean isProduction = Boolean.parseBoolean(production.attribute("active").getValue());
        if(isProduction) {
            ApiOperationBase.setEnvironment(Environment.PRODUCTION);
            String apiId = production.element("api_id").getTextTrim();
            String transactionKey = production.element("transaction_key").getTextTrim();
            MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
            merchantAuthenticationType.setName(apiId);
            merchantAuthenticationType.setTransactionKey(transactionKey);
            ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
            logger.debug("Authorize environment: production");
            logger.debug("api id: {}", apiId);
            logger.debug("transaction key: {}", transactionKey);
        }
    }
}
