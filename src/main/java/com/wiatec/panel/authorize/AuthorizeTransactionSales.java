package com.wiatec.panel.authorize;

import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * @author patrick
 */
public class AuthorizeTransactionSales {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeTransactionSales.class);
    private String path = this.getClass().getClassLoader().getResource("/").getPath() + "authorize.xml";

    public AuthorizeTransactionSalesInfo charge(AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo){
        return charge(authorizeTransactionSalesInfo, null);
    }

    /**
     * charge a transaction
     * @param authorizeTransactionSalesInfo {@link AuthorizeTransactionRentalInfo}
     * @return {@link AuthorizeTransactionSalesInfo} include all transaction information
     */
    public AuthorizeTransactionSalesInfo charge(AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo, HttpServletRequest request){
        if(request == null){
            AuthorizeConfig.init(path);
        }else {
            AuthorizeConfig.init(request);
        }
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionSalesInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionSalesInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionSalesInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionSalesInfo);
    }

    /**
     * auth a transaction
     * @param authorizeTransactionSalesInfo {@link AuthorizeTransactionRentalInfo}
     * @return {@link AuthorizeTransactionSalesInfo} include all transaction information
     */
    public AuthorizeTransactionSalesInfo authorize(AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo){
        AuthorizeConfig.init(path);
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionSalesInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionSalesInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionSalesInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionSalesInfo);
    }

    /**
     * refund a transaction
     * @param authorizeTransactionSalesInfo {@link AuthorizeTransactionSalesInfo}
     * @return {@link AuthorizeTransactionSalesInfo} include all transaction information
     */
    public AuthorizeTransactionSalesInfo refund(AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo){
        AuthorizeConfig.init(path);
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionSalesInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionSalesInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
        requestType.setRefTransId(authorizeTransactionSalesInfo.getTransactionId());
        requestType.setAmount(new BigDecimal(authorizeTransactionSalesInfo.getAmount()).setScale(2, RoundingMode.CEILING));
        requestType.setPayment(paymentType);

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();

        return handleResponse(response, authorizeTransactionSalesInfo);
    }

    private AuthorizeTransactionSalesInfo handleResponse(CreateTransactionResponse response, AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo){
        if (response!=null) {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    logger.debug("Successfully created transaction with Transaction ID: " + result.getTransId());
                    logger.debug("Response Code: " + result.getResponseCode());
                    logger.debug("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    logger.debug("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    logger.debug("Auth Code: " + result.getAuthCode());
                    AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo1 = new AuthorizeTransactionSalesInfo();
                    authorizeTransactionSalesInfo1.setSalesId(authorizeTransactionSalesInfo.getSalesId());
                    authorizeTransactionSalesInfo1.setCardNumber(authorizeTransactionSalesInfo.getCardNumber());
                    authorizeTransactionSalesInfo1.setExpirationDate(authorizeTransactionSalesInfo.getExpirationDate());
                    authorizeTransactionSalesInfo1.setSecurityKey(authorizeTransactionSalesInfo.getSecurityKey());
                    authorizeTransactionSalesInfo1.setActivateCategory(authorizeTransactionSalesInfo.getActivateCategory());
                    authorizeTransactionSalesInfo1.setActivatePay(authorizeTransactionSalesInfo.getActivatePay());
                    authorizeTransactionSalesInfo1.setGoldCategory(authorizeTransactionSalesInfo.getGoldCategory());
                    authorizeTransactionSalesInfo1.setGoldPay(authorizeTransactionSalesInfo.getGoldPay());
                    authorizeTransactionSalesInfo1.setAmount(authorizeTransactionSalesInfo.getAmount());
                    authorizeTransactionSalesInfo1.setTransactionId(result.getTransId());
                    authorizeTransactionSalesInfo1.setAuthCode(result.getAuthCode());
                    authorizeTransactionSalesInfo1.setStatus("approved");
                    return authorizeTransactionSalesInfo1;
                } else {
                    logger.debug("Failed Transaction.");
                    if (response.getTransactionResponse().getErrors() != null) {
                        logger.debug("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                        logger.debug("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                        throw new XException(ResultMaster.error(response.getTransactionResponse().getErrors().getError().get(0).getErrorCode(),
                                response.getTransactionResponse().getErrors().getError().get(0).getErrorText()));
                    }
                }
            } else {
                logger.debug("Failed Transaction.");
                if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
                    logger.debug("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                    logger.debug("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                    throw new XException(ResultMaster.error(response.getTransactionResponse().getErrors().getError().get(0).getErrorCode(),
                            response.getTransactionResponse().getErrors().getError().get(0).getErrorText()));
                }
                else {
                    logger.debug("Error Code: " + response.getMessages().getMessage().get(0).getCode());
                    logger.debug("Error message: " + response.getMessages().getMessage().get(0).getText());
                    throw new XException(ResultMaster.error(response.getTransactionResponse().getErrors().getError().get(0).getErrorCode(),
                            response.getTransactionResponse().getErrors().getError().get(0).getErrorText()));
                }
            }
        } else {
            logger.debug("Null Response.");
            throw new XException(ResultMaster.error(500, "authorize no response"));
        }
        return null;
    }
}
