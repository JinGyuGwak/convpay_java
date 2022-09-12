package com.zerobase.convpay.service;

import com.zerobase.convpay.dto.*;
import com.zerobase.convpay.type.*;

public class ConveniencePayService {  //편결이 몸통 부분
    private final MoneyAdapter moneyAdapter = new MoneyAdapter();
    private final CardAdapter cardAdapter = new CardAdapter();
    //private final PointAdapter pointAdapter = new PointAdater();
    // dip를 만족하면 이렇게 새로 추가 되어도 밑에 조건문에 하나만 추가하면 됨


    public PayResponse pay(PayRequest payRequest){
        PaymentInterface paymentInterface;
        if(payRequest.getPayMethodType() == PayMethodType.CARD){
            paymentInterface=cardAdapter;
        }else{
            paymentInterface=moneyAdapter;
        }
        PaymentResult payment = paymentInterface.payment(payRequest.getPayAmount());
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
        return new PayResponse(PayResult.SUCCESS, payRequest.getPayAmount());
    }
    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest){

        PaymentInterface paymentInterface;

        if(payCancelRequest.getPayMethodType() == PayMethodType.CARD){
            paymentInterface=cardAdapter;
        }else{
            paymentInterface=moneyAdapter;
        }

        CancelPaymentResult cancelPaymentResult
                = paymentInterface.cancelPayment(payCancelRequest.getPayCancelAmount());

        if(cancelPaymentResult == CancelPaymentResult.CANCEL_PAYMENT_FAIL){
            return new PayCancelResponse(PayCancelResult.PAY_CANCEL_FAIL, 0);
        }


        return new PayCancelResponse(PayCancelResult.PAY_CANCEL_SUCCESS,
                payCancelRequest.getPayCancelAmount());

    }
}
