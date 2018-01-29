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
public class AuthorizeTransactionRental {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeTransactionRental.class);
    private String path = this.getClass().getClassLoader().getResource("/").getPath() + "authorize.xml";

    public AuthorizeTransactionRentalInfo charge(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo){
        return charge(authorizeTransactionRentalInfo, null);
    }

    /**
     * charge a transaction
     * @param authorizeTransactionRentalInfo {@link AuthorizeTransactionRentalInfo}
     * @return {@link AuthorizeTransactionRentalInfo} include all transaction information
     */
    public AuthorizeTransactionRentalInfo charge(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo, HttpServletRequest request){
        if(request == null){
            AuthorizeConfig.init(path);
        }else {
            AuthorizeConfig.init(request);
        }
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionRentalInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionRentalInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionRentalInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionRentalInfo);
    }

    /**
     * auth a transaction
     * @param authorizeTransactionRentalInfo {@link AuthorizeTransactionRentalInfo}
     * @return {@link AuthorizeTransactionRentalInfo} include all transaction information
     */
    public AuthorizeTransactionRentalInfo authorize(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo){
        AuthorizeConfig.init(path);
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionRentalInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionRentalInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        requestType.setPayment(paymentType);
        requestType.setAmount(new BigDecimal(authorizeTransactionRentalInfo.getAmount()).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();
        return handleResponse(response, authorizeTransactionRentalInfo);
    }

    /**
     * refund a transaction
     * @param authorizeTransactionRentalInfo {@link AuthorizeTransactionRentalInfo}
     * @return {@link AuthorizeTransactionRentalInfo} include all transaction information
     */
    public AuthorizeTransactionRentalInfo refund(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo){
        AuthorizeConfig.init(path);
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(authorizeTransactionRentalInfo.getCardNumber());
        creditCard.setExpirationDate(authorizeTransactionRentalInfo.getExpirationDate()+"");
        paymentType.setCreditCard(creditCard);

        TransactionRequestType requestType = new TransactionRequestType();
        requestType.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
        requestType.setRefTransId(authorizeTransactionRentalInfo.getTransactionId());
        requestType.setAmount(new BigDecimal(authorizeTransactionRentalInfo.getAmount()).setScale(2, RoundingMode.CEILING));
        requestType.setPayment(paymentType);

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(requestType);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        CreateTransactionResponse response = controller.getApiResponse();

        return handleResponse(response, authorizeTransactionRentalInfo);
    }

    private AuthorizeTransactionRentalInfo handleResponse(CreateTransactionResponse response, AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo){
        if (response!=null) {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    logger.debug("Successfully created transaction with Transaction ID: " + result.getTransId());
                    logger.debug("Response Code: " + result.getResponseCode());
                    logger.debug("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    logger.debug("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    logger.debug("Auth Code: " + result.getAuthCode());
                    AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo1 = new AuthorizeTransactionRentalInfo();
                    authorizeTransactionRentalInfo1.setAmount(authorizeTransactionRentalInfo.getAmount());
                    authorizeTransactionRentalInfo1.setDeposit(authorizeTransactionRentalInfo.getDeposit());
                    authorizeTransactionRentalInfo1.setLdCommission(authorizeTransactionRentalInfo.getLdCommission());
                    authorizeTransactionRentalInfo1.setLdeCommission(authorizeTransactionRentalInfo.getLdeCommission());
                    authorizeTransactionRentalInfo1.setDealerCommission(authorizeTransactionRentalInfo.getDealerCommission());
                    authorizeTransactionRentalInfo1.setSalesCommission(authorizeTransactionRentalInfo.getSalesCommission());
                    authorizeTransactionRentalInfo1.setExpirationDate(authorizeTransactionRentalInfo.getExpirationDate());
                    authorizeTransactionRentalInfo1.setCardNumber(authorizeTransactionRentalInfo.getCardNumber());
                    authorizeTransactionRentalInfo1.setSecurityKey(authorizeTransactionRentalInfo.getSecurityKey());
                    authorizeTransactionRentalInfo1.setCategory(authorizeTransactionRentalInfo.getCategory());
                    authorizeTransactionRentalInfo1.setSalesId(authorizeTransactionRentalInfo.getSalesId());
                    authorizeTransactionRentalInfo1.setDealerId(authorizeTransactionRentalInfo.getDealerId());
                    authorizeTransactionRentalInfo1.setClientKey(authorizeTransactionRentalInfo.getClientKey());
                    authorizeTransactionRentalInfo1.setType(authorizeTransactionRentalInfo.getType());
                    authorizeTransactionRentalInfo1.setTransactionId(result.getTransId());
                    authorizeTransactionRentalInfo1.setAuthCode(result.getAuthCode());
                    authorizeTransactionRentalInfo1.setStatus("approved");
                    authorizeTransactionRentalInfo1.setPrice(authorizeTransactionRentalInfo.getPrice());
                    authorizeTransactionRentalInfo1.setTxFee(authorizeTransactionRentalInfo.getTxFee());
                    return authorizeTransactionRentalInfo1;
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
