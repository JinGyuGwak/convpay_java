package com.zerobase.convpay.service;

import com.zerobase.convpay.dto.*;
import com.zerobase.convpay.type.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ConveniencePayService {  //편결이 몸통 부분
    private final Map<PayMethodType,PaymentInterface> paymentInterfaceMap =
            new HashMap<>();
    private final DiscountInterface discountInterface;

    public ConveniencePayService(Set<PaymentInterface> paymentInterfaceset,
                                 @Qualifier("discountByConvenience")
                                 DiscountInterface discountInterface) {
        paymentInterfaceset.forEach(
                paymentInterface -> paymentInterfaceMap.put(
                        paymentInterface.getPayMethodType(),
                        paymentInterface
                )
        );

        this.discountInterface = discountInterface;
    }
    //    private final MoneyAdapter moneyAdapter = new MoneyAdapter();
//    private final CardAdapter cardAdapter = new CardAdapter();
//    private final DiscountInterface discountInterface = new DiscountByPayMethod();
    //private final DiscountInterface discountInterface = new DiscountAll();
    //private final DiscountInterface discountInterface = new DiscountByConvenience();
    //private final PointAdapter pointAdapter = new PointAdater();
    // dip를 만족하면 이렇게 새로 추가 되어도 밑에 조건문에 하나만 추가하면 됨

    public PayResponse pay(PayRequest payRequest){
        PaymentInterface paymentInterface = paymentInterfaceMap.get(payRequest.getPayMethodType());


        Integer discountedAmount = discountInterface.getDiscountedAmount(payRequest);
        PaymentResult payment = paymentInterface.payment(discountedAmount);

//        CardUseResult cardUseResult;
//        MoneyUseResult moneyUseResult;
//
//        if(payRequest.getPayMethodType() == PayMethodType.CARD){
//            cardAdapter.authorization();
//            cardAdapter.approval();
//            cardUseResult = cardAdapter.capture(payRequest.getPayAmount());
//
//        }else {
//            moneyUseResult = moneyAdapter.use(payRequest.getPayAmount());
//        }

        if(payment==PaymentResult.PAYMENT_FAIL){
            return new PayResponse(PayResult.FAIL, 0);
        }

        //Success Case
        return new PayResponse(PayResult.SUCCESS, discountedAmount);
    }
    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest){
        PaymentInterface paymentInterface = paymentInterfaceMap.get(payCancelRequest.getPayMethodType());

        CancelPaymentResult cancelPaymentResult
                = paymentInterface.cancelPayment(payCancelRequest.getPayCancelAmount());

        if(cancelPaymentResult == CancelPaymentResult.CANCEL_PAYMENT_FAIL){
            return new PayCancelResponse(PayCancelResult.PAY_CANCEL_FAIL, 0);
        }


        return new PayCancelResponse(PayCancelResult.PAY_CANCEL_SUCCESS,
                payCancelRequest.getPayCancelAmount());

    }
}
