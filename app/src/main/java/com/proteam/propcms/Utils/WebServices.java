package com.proteam.propcms.Utils;


import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proteam.propcms.Request.BillingUpdaterequest;
import com.proteam.propcms.Request.Clientlistrequest;
import com.proteam.propcms.Request.CompanyDetailsModel;
import com.proteam.propcms.Request.DashboardFilterDetailsRequest;
import com.proteam.propcms.Request.DivisionListModel;
import com.proteam.propcms.Request.ExpenseRequest;
import com.proteam.propcms.Request.InvApproverequest;
import com.proteam.propcms.Request.Loginmodel;
import com.proteam.propcms.Request.ProjectListModel;
import com.proteam.propcms.Request.UserIdRequest;
import com.proteam.propcms.Request.Updateuserrequest;
import com.proteam.propcms.Request.VctDeleteRequest;
import com.proteam.propcms.Request.VctUpdateRequest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServices<T> {
    T t;
    Call<T> call=null;
    public T getT() {
        return t;
    }

    public void setT(T t) {

        this.t = t;
    }

    ApiType apiTypeVariable;
    Context context;
    OnResponseListener<T> onResponseListner;
    private static OkHttpClient.Builder builder;

    public enum ApiType {
       general,login,profile,profileupdate,invoicemod,projectlist,approve,companylist,headlist,divisionlist,countitem,client
        ,verifyBi,verifyVct,divisioncountdashboard,dashboardfilterdetails,submitBI,update,SubmitCTN,deletectndata,DeleteBI
        ,updatevct,expense,pdfupload
    }

    String BaseUrl = "https://pcmsdemo.proteam.co.in/api/";

    public WebServices(OnResponseListener<T> onResponseListner) {
        this.onResponseListner = onResponseListner;

        if (onResponseListner instanceof Activity) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof IntentService) {
            this.context = (Context) onResponseListner;
        } else if (onResponseListner instanceof android.app.DialogFragment) {
            android.app.DialogFragment dialogFragment = (android.app.DialogFragment) onResponseListner;
            this.context = dialogFragment.getActivity();
        }else if (onResponseListner instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) onResponseListner;
            this.context = fragment.getActivity();
        }
        else if (onResponseListner instanceof Adapter) {

            this.context = (Context) onResponseListner;
        }
        else if (onResponseListner instanceof Adapter) {
            this.context = (Context) onResponseListner;
        }
        else {
            //android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) onResponseListner;
            //this.context = fragment.getActivity();
        }

        builder = getHttpClient();
    }

    public WebServices(Context context, OnResponseListener<T> onResponseListner) {
        this.onResponseListner = onResponseListner;
        this.context = context;
        builder = getHttpClient();
    }

    public OkHttpClient.Builder getHttpClient() {

        if (builder == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(10000, TimeUnit.SECONDS);
            client.readTimeout(10000, TimeUnit.SECONDS).build();
            client.addInterceptor(loggingInterceptor);
            /*to pass header information with request*/
            client.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
                    return chain.proceed(request);
                }
            });

            return client;
        }
        return builder;
    }

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();




    private Retrofit getRetrofitClient(String api)
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(api)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }


    public void login( ApiType apiTypes, Loginmodel loginmodel)
    {

        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.validatelogin(loginmodel);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void profileapi( ApiType apiTypes, UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.profile(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void profileupdateapi( ApiType apiTypes, Updateuserrequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.profileupdate(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void invoicemodificationapi( ApiType apiTypes)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.invoicemodify();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void companyname(ApiType apiTypes, CompanyDetailsModel companyDetailsModel)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.companydetail(companyDetailsModel);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void projectlist( ApiType apiTypes, ProjectListModel projectListModel)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.projectnamelist(projectListModel);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void approvecall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.aprovallist(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void rejectcall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.rejectList(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void companylist( ApiType apiTypes)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.companys();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void headlist( ApiType apiTypes)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.heads();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }


    public void divisionlist( ApiType apiTypes, DivisionListModel divisionListModel)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.divisionnamelist(divisionListModel);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    ////////////////////////////////////////Approval apis////////////////////////////////////////

    public void invoiceapprovalapi( ApiType apiTypes)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.invoiceapprove();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void approvectrncall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.aprovalctrnlist(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void rejectctrncall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.rejectctrnList(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void invoicancelationapi( ApiType apiTypes,UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.invoicecancelation(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void approveirfccall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.aprovalirfclist(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void rejectirfccall( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.rejectirfcList(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

///////////////////////////////dashboard /////////////////////////////////////////////////////

    public void dashboardcount( ApiType apiTypes, UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.count(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void dashboardcountDivision( ApiType apiTypes, UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.countDivision(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void ClientList( ApiType apiTypes, Clientlistrequest clientlistrequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.client(clientlistrequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }


    public void VerifyBIDataList( ApiType apiTypes, UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.VerifyBIList(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }



    public void VerifyVCTDataList( ApiType apiTypes, UserIdRequest userIdRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.VerifyvctList(userIdRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void dashboardFilter( ApiType apiTypes, DashboardFilterDetailsRequest dashboardFilterDetailsRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.dashboardfilterdetail(dashboardFilterDetailsRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void verifyandSubmitBI( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.sbumitBI(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void Updatebilling( ApiType apiTypes, BillingUpdaterequest billingUpdaterequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.billupdate(billingUpdaterequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }


    public void verifyandSubmitCTN( ApiType apiTypes, InvApproverequest invApproverequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.sbumitCTN(invApproverequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void DeleteCtn( ApiType apiTypes, VctDeleteRequest vctDeleteRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.Deletevctdata(vctDeleteRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void DeleteBI( ApiType apiTypes, VctDeleteRequest vctDeleteRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.DeleteBIdata(vctDeleteRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }


    public void UpdateVct( ApiType apiTypes, VctUpdateRequest vctUpdateRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.vctupdate(vctUpdateRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void ExpensetypeList( ApiType apiTypes, ExpenseRequest expenseRequest)
    {
        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient(BaseUrl);
        ProPCms proPCms=retrofit.create(ProPCms.class);

        call=(Call<T>)proPCms.ExpenseList(expenseRequest);

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("usercompany===="+response.body());
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
            }
        });

    }

    public void fileupload( ApiType apiTypes, File path,String userid,String billid) {


        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), path);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", "dock.pdf", requestFile);
        RequestBody user_id = RequestBody.create(MultipartBody.FORM, userid);
        RequestBody billing_id = RequestBody.create(MultipartBody.FORM, billid);

        apiTypeVariable = apiTypes;
        Retrofit retrofit=getRetrofitClient("https://pcmsdemo.proteam.co.in/api/");

        ProPCms proPCms=retrofit.create(ProPCms.class);

        //call=(Call<T>)auditApi.validateaudit(fileToUpload,audit1,rack1,rack_completed1,total_itemqty1,total_rack1,user_id1,total_untagged1);

        call=(Call<T>)proPCms.fileupload1(body,user_id,billing_id);
        //call=auditApi.fileupload1(fileToUpload,filename);

        call.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
                t=(T)response.body();
                onResponseListner.onResponse(t, apiTypeVariable, true,response.code());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onResponseListner.onResponse(null, apiTypeVariable, false,0);
                Log.e("debug,", t.toString());
            }
        });

    }

}

