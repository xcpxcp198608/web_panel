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
public class AuthorizeTransaction {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeTransaction.class);

    public static AuthorizeTransactionInfo charge(AuthorizeTransactionInfo authorizeTransactionInfo){
        return charge(authorizeTransactionInfo, null);
    }

    /**
     * charge a transaction
     * @param authorizeTransactionInfo {@link AuthorizeTransactionInfo}
     * @return {@link AuthorizeTransactionInfo} include all transaction information
     */
    public static AuthorizeTransactionInfo charge(AuthorizeTransactionInfo authorizeTransactionInfo, HttpServletRequest request){
        if(request == null){
            AuthorizeConfig.init();
        }else {
            AuthorizeConfig.init(request);
        }
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionInfo);
    }

    /**
     * auth a transaction
     * @param authorizeTransactionInfo {@link AuthorizeTransactionInfo}
     * @return {@link AuthorizeTransactionInfo} include all transaction information
     */
    public static AuthorizeTransactionInfo authorize(AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeConfig.init();
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionInfo);
    }

    /**
     * refund a transaction
     * @param authorizeTransactionInfo {@link AuthorizeTransactionInfo}
     * @return {@link AuthorizeTransactionInfo} include all transaction information
     */
    public static AuthorizeTransactionInfo refund(AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeConfig.init();
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
        requestType.setRefTransId(authorizeTransactionInfo.getTransactionId());
        requestType.setAmount(new BigDecimal(authorizeTransactionInfo.getAmount()).setScale(2, RoundingMode.CEILING));
        requestType.setPayment(paymentType);

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();

        return handleResponse(response, authorizeTransactionInfo);
    }

    private static AuthorizeTransactionInfo handleResponse(CreateTransactionResponse response, AuthorizeTransactionInfo authorizeTransactionInfo){
        if (response!=null) {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    logger.debug("Successfully created transaction with Transaction ID: " + result.getTransId());
                    logger.debug("Response Code: " + result.getResponseCode());
                    logger.debug("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    logger.debug("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    logger.debug("Auth Code: " + result.getAuthCode());
                    AuthorizeTransactionInfo authorizeTransactionInfo1 = new AuthorizeTransactionInfo();
                    authorizeTransactionInfo1.setAmount(authorizeTransactionInfo.getAmount());
                    authorizeTransactionInfo1.setDeposit(authorizeTransactionInfo.getDeposit());
                    authorizeTransactionInfo1.setLdCommission(authorizeTransactionInfo.getLdCommission());
                    authorizeTransactionInfo1.setDealerCommission(authorizeTransactionInfo.getDealerCommission());
                    authorizeTransactionInfo1.setSalesCommission(authorizeTransactionInfo.getSalesCommission());
                    authorizeTransactionInfo1.setExpirationDate(authorizeTransactionInfo.getExpirationDate());
                    authorizeTransactionInfo1.setCardNumber(authorizeTransactionInfo.getCardNumber());
                    authorizeTransactionInfo1.setSecurityKey(authorizeTransactionInfo.getSecurityKey());
                    authorizeTransactionInfo1.setCategory(authorizeTransactionInfo.getCategory());
                    authorizeTransactionInfo1.setSalesId(authorizeTransactionInfo.getSalesId());
                    authorizeTransactionInfo1.setDealerId(authorizeTransactionInfo.getDealerId());
                    authorizeTransactionInfo1.setClientKey(authorizeTransactionInfo.getClientKey());
                    authorizeTransactionInfo1.setType(authorizeTransactionInfo.getType());
                    authorizeTransactionInfo1.setTransactionId(result.getTransId());
                    authorizeTransactionInfo1.setAuthCode(result.getAuthCode());
                    authorizeTransactionInfo1.setStatus("approved");
                    authorizeTransactionInfo1.setPrice(authorizeTransactionInfo.getPrice());
                    authorizeTransactionInfo1.setTxFee(authorizeTransactionInfo.getTxFee());
                    return authorizeTransactionInfo1;
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
