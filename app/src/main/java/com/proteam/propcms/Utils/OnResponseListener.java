/*
 * Created by Krishnamurthy T
 * Copyright (c) 2019 .  V V Technologies All rights reserved.
 * Last modified 27/11/18 3:07 PM
 *
 */

package com.proteam.propcms.Utils;




/**
 * Created by Shashi on 10/9/2017.
 */

public interface OnResponseListener<T> {

    void onResponse(T response, WebServices.ApiType URL, boolean isSucces, int code);

}