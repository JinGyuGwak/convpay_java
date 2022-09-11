package com.zerobase.convpay.service;

import com.zerobase.convpay.dto.*;
import com.zerobase.convpay.type.MoneyUseResult;
import com.zerobase.convpay.type.PayResult;

public class ConveniencePayService {  //편결이 몸통 부분
    private final MoneyAdapter moneyAdapter = new MoneyAdapter();

    public PayResponse pay(PayRequest payRequest){
        MoneyUseResult moneyUseResult =
                moneyAdapter.use(payRequest.getPayAmount());

        if(moneyUseResult==MoneyUseResult.USE_FAIL) {
            return new PayResponse(PayResult.FAIL, 0);
        }
        return new PayResponse(PayResult.SUCCESS, payRequest.getPayAmount());
    }
    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest){
        moneyAdapter.useCancel(payCancelRequest.getPayCancelAmount());

    }
}
