package com.delivery.api.common.api;

import com.delivery.api.common.error.ErrorCodeIfs;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    // 앞은 제네릭 타입으로 리턴, 뒤의 <T>는 T형식의 data를 받을 수 없으니 처리할 수 있도록 해준다는 뜻
    public static <T> Api<T> OK(T data){
        var api = new Api<T>();     // 제네릭 타입으로 생성
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    // Error일때는 body가 필요없고 Result만 받아 Object형으로 return
    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;
    }

    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, description);
        return api;
    }
}
