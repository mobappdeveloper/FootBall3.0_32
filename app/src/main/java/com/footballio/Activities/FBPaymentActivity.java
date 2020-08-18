package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Security;
import com.footballio.model.PurchasedResponse;
import com.footballio.model.workout.paymentcheck.WorkoutApplyCopuonResponse;
import com.footballio.model.workout.paymentcheck.WorkoutPaymentPriceResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FBPaymentActivity";
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.txt_offer_code)
    TextView txtOfferCode;
    @BindView(R.id.img_payment_ic)
    ImageView imgPaymentIc;
    @BindView(R.id.txt_offer_details)
    TextView txtOfferDetails;
    @BindView(R.id.txt_year_plan)
    TextView txtYearPlan;
    @BindView(R.id.txt_year_monthly)
    TextView txtYearMonthly;
    @BindView(R.id.txt_trial)
    TextView txtTrial;
    @BindView(R.id.txt_after_trial)
    TextView txtAfterTrial;
    @BindView(R.id.edt_couponcode)
    EditText edtCouponcode;
    @BindView(R.id.imgbtn_submit)
    ImageButton imgbtnSubmit;
    @BindView(R.id.layout_couponcode)
    LinearLayout layoutCouponcode;
    @BindView(R.id.layout_1)
    CardView layout1;
    @BindView(R.id.layout_2)
    LinearLayout layout2;
    private Context mycontext;
    private WorkoutPaymentPriceResponse workoutPaymentPriceResponse;
    private WorkoutApplyCopuonResponse workoutApplyCopuonResponse;
    private BillingClient mBillingClient;

    String identifier ="", type="", coupon1="";
    private PurchasedResponse purchasedResponse;
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener;

    //AppSettings app_settings;
    /*https://medium.com/@KarthikPonnam/inapp-purchase-subscription-android-8fff52fa4d3b
    * https://stackoverflow.com/questions/11068686/this-version-of-the-application-is-not-configured-for-billing-through-google-pla*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbpayment);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        mycontext = this;

        imgbtnSubmit.setOnClickListener(this);
        imgClose.setOnClickListener(this);

        mBillingClient = BillingClient.newBuilder(mycontext).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && purchases != null) {

                for (Purchase purchase : purchases) {
                    // When every a new purchase is made
                    // Here we verify our purchase
                    Log.d(TAG, "onPurchasesUpdated: " + purchase.getSku());
                    if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                        // Invalid purchase
                        // show error to user
                        Log.i(TAG, "Got a purchase: " + purchase + "; but signature is bad. Skipping...");
                        handlePurchase(purchase);
                        return;
                    } else {
                        // purchase is valid
                        // Perform actions
                        handlePurchase(purchase);
                        Log.d(TAG, "onPurchasesUpdated:else " + purchase.getSku());
                    }
                }

                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // Handle an error caused by a user cancelling the purchase flow.
                } else {
                    // Handle any other error codes.
                }
            }


        }).build();
         acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(BillingResult billingResult) {

              //  getMessage("Purchase acknowledged");
                Log.d(TAG, "onAcknowledgePurchaseResponse: Purchase acknowledged");
            }

        };
        mBillingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The billing client is ready. You can query purchases here.

             /*       List<String> skuList = new ArrayList<>();
                   // skuList.add("android.test.purchased");
                     skuList.add("a17jmpbeo17381");

                    SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                            .setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build();
                    mBillingClient.querySkuDetailsAsync(skuDetailsParams,
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                    Log.d(TAG, "onSkuDetailsResponse: "+new Gson().toJson(skuDetailsList        ));
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(0))
                                            .build();
                                    BillingResult billingResponseCode = mBillingClient.launchBillingFlow(FBPaymentActivity.this, flowParams);
                                    if (billingResponseCode.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                        // do something you want
                                    }
                                }


                            });*/
                }

            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mBillingClient.startConnection(new BillingClientStateListener() {


                    @Override
                    public void onBillingSetupFinished(BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            // The billing client is ready. You can query purchases here.

                            List<String> skuList = new ArrayList<>();
                           // skuList.add("android.test.purchased");
                            //skuList.add("com.example.product");
                           skuList.add("a17jmpbeo17381y");

                            SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                                    .setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build();
                            mBillingClient.querySkuDetailsAsync(skuDetailsParams,
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                            Log.d(TAG, "onSkuDetailsResponse: "+new Gson().toJson(skuDetailsList));
                                            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetailsList.get(0))
                                                 //   .setSkuDetails(skuDetailsList.get(0))
                                                    .build();
                                            BillingResult billingResponseCode = mBillingClient.launchBillingFlow(FBPaymentActivity.this, flowParams);
                                            if (billingResponseCode.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                // do something you want

                                                identifier ="a17jmpbeo17381y";
                                                type = "Yearly";
                                                Log.d(TAG, "onSkuDetailsResponse: suceess");
                                            }
                                        }


                                    });
                        }

                    }

                    @Override
                    public void onBillingServiceDisconnected() {
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                    }
                });

            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mBillingClient.startConnection(new BillingClientStateListener() {


                    @Override
                    public void onBillingSetupFinished(BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            // The billing client is ready. You can query purchases here.

                            List<String> skuList = new ArrayList<>();
                           // skuList.add("android.test.purchased");
                             skuList.add("a17jmpbeo17381");

                            SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                                    .setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build();
                            mBillingClient.querySkuDetailsAsync(skuDetailsParams,
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                            Log.d(TAG, "onSkuDetailsResponse: "+new Gson().toJson(skuDetailsList));
                                            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetailsList.get(0))
                                                    .build();
                                            BillingResult billingResponseCode = mBillingClient.launchBillingFlow(FBPaymentActivity.this, flowParams);
                                            if (billingResponseCode.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                // do something you want
                                                identifier ="a17jmpbeo17381";
                                                type = "Monthly";
                                                Log.d(TAG, "onSkuDetailsResponse: suceess");
                                            }
                                        }


                                    });
                        }

                    }

                    @Override
                    public void onBillingServiceDisconnected() {
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                    }
                });   }
        });

        getSubscribeItem();
        workoutPaymentPrice();
    }

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            return Security.verifyPurchase(APPConst.INAPP_BASE64KEY, signedData, signature);
        } catch (IOException e) {
            Log.e(TAG, "Got an exception trying to validate a purchase: " + e);
            return false;
        }
    }

    void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Acknowledge purchase and grant the item to the user
            // Acknowledge the purchase if it hasn't already been acknowledged.
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                mBillingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                Log.d(TAG, "handlePurchase: acknownlege done");
            }
            getPurchaseData(identifier,type,coupon1);
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            // Here you can confirm to the user that they've started the pending
            // purchase, and to complete it, they should follow instructions that
            // are given to them. You can also choose to remind the user in the
            // future to complete the purchase if you detect that it is still
            // pending.
        }
    }

    public void getSubscribeItem() {
/*
        mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS) {

        }*/

        List<String> skuList = new ArrayList<>();
        skuList.add("a17jmpbeo17381");
        skuList.add("a17jmpbeo17381y");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        mBillingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                                && skuDetailsList != null) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                String sku = skuDetails.getSku();
                                String price = skuDetails.getPrice();
                                Log.d(TAG, "onSkuDetailsResponse: " + sku);
                                if ("a17jmpbeo17381".equals(sku)) {
                                    Toast.makeText(mycontext, "a17jmpbeo17381", Toast.LENGTH_SHORT).show();
                                } else if ("a17jmpbeo17381y".equals(sku)) {
                                    Toast.makeText(mycontext, "a17jmpbeo17381y", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }


                });
    }

    Call<WorkoutPaymentPriceResponse> workoutPaymentPriceResponseCall;

    private void workoutPaymentPrice() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutPaymentPriceResponseCall = apiService.workoutPamentPrice();


        workoutPaymentPriceResponseCall.enqueue(new Callback<WorkoutPaymentPriceResponse>() {
            @Override
            public void onResponse(Call<WorkoutPaymentPriceResponse> call, Response<WorkoutPaymentPriceResponse> response) {
                Log.d(TAG, "onResponse: workoutPaymentPriceResponse" + new Gson().toJson(response.body()));
                workoutPaymentPriceResponse = response.body();

                txtYearPlan.setText("Jährlich - " + workoutPaymentPriceResponse.getYearlyPrice() + " € / Jahr");
                txtYearMonthly.setText(workoutPaymentPriceResponse.getMonthlyYearlyPrice() + " € / Monat, wird jährlich in Rechnung gestellt");

                txtTrial.setText(workoutPaymentPriceResponse.getTrail() + " Tage kostenlos testen");
                txtAfterTrial.setText(workoutPaymentPriceResponse.getMonthlyPrice() + " € / Monat, nach " + workoutPaymentPriceResponse.getTrail() + " - tägiger Probezeit");

                txtOfferDetails.setText("Spare " + workoutPaymentPriceResponse.getDiscount() + " %");
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<WorkoutPaymentPriceResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


    Call<PurchasedResponse> workoutPaymentPrice;

    private void getPurchaseData(String identifier,String type,String coupon) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutPaymentPrice = apiService.getPurchaseData(Prefs.getString(APPConst.TOKEN,""),identifier,type,coupon);


        workoutPaymentPrice.enqueue(new Callback<PurchasedResponse>() {
            @Override
            public void onResponse(Call<PurchasedResponse> call, Response<PurchasedResponse> response) {
                if (response.isSuccessful()) {
                    purchasedResponse = response.body();
                    Log.d(TAG, "getPurchaseData: if "+new Gson().toJson(purchasedResponse));
                    Toast.makeText(mycontext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                    intent.putExtra(APPConst.PWORKSTATUS, "3");
                    startActivity(intent);
                }else{
                    Log.d(TAG, "getPurchaseData: else ");
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<PurchasedResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ResponseBody" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    Call<WorkoutApplyCopuonResponse> workoutApplyCopuonResponseCall;

    private void workoutPaymentPrice(String coupon) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutApplyCopuonResponseCall = apiService.workoutApplyCoupon(coupon, Prefs.getString(APPConst.TOKEN, ""));


        workoutApplyCopuonResponseCall.enqueue(new Callback<WorkoutApplyCopuonResponse>() {
            @Override
            public void onResponse(Call<WorkoutApplyCopuonResponse> call, Response<WorkoutApplyCopuonResponse> response) {
                Log.d(TAG, "onResponse: workoutApplyCopuonResponse" + new Gson().toJson(response.body()));
                workoutApplyCopuonResponse = response.body();
                if (workoutApplyCopuonResponse.isStatus()) {
                    layoutCouponcode.setVisibility(View.GONE);
                    txtOfferCode.setText("Code: " + coupon);
                    txtYearPlan.setText("Jährlich - " + workoutApplyCopuonResponse.getYearlyPrice() + " € / Jahr");
                    txtYearMonthly.setText(workoutApplyCopuonResponse.getMonthlyYearlyPrice() + " € / Monat, wird jährlich in Rechnung gestellt");

                    txtTrial.setText( "7 Tage kostenlos testen");
                    txtAfterTrial.setText(workoutApplyCopuonResponse.getMonthlyPrice() + " € / Monat, nach 7 - tägiger Probezeit");

                    txtOfferDetails.setText("Spare " + workoutApplyCopuonResponse.getDiscount() + " %");


                    layout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            mBillingClient.startConnection(new BillingClientStateListener() {


                                @Override
                                public void onBillingSetupFinished(BillingResult billingResult) {
                                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                        // The billing client is ready. You can query purchases here.

                                        List<String> skuList = new ArrayList<>();
                                        //  skuList.add("android.test.purchased");
                                        skuList.add("viscabarca20y");

                                        SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                                                .setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build();
                                        mBillingClient.querySkuDetailsAsync(skuDetailsParams,
                                                new SkuDetailsResponseListener() {
                                                    @Override
                                                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                                        Log.d(TAG, "onSkuDetailsResponse: "+new Gson().toJson(skuDetailsList));
                                                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                                .setSkuDetails(skuDetailsList.get(0))
                                                                //   .setSkuDetails(skuDetailsList.get(0))
                                                                .build();
                                                        BillingResult billingResponseCode = mBillingClient.launchBillingFlow(FBPaymentActivity.this, flowParams);
                                                                 if (billingResponseCode.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                            // do something you want
                                                            identifier ="viscabarca20y";
                                                            type = "Yearly";
                                                            coupon1 = "viscabarca20";
                                                            Log.d(TAG, "onSkuDetailsResponse: suceess");
                                                        }
                                                    }


                                                });
                                    }

                                }

                                @Override
                                public void onBillingServiceDisconnected() {
                                    // Try to restart the connection on the next request to
                                    // Google Play by calling the startConnection() method.
                                }
                            });

                        }
                    });
                    layout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            mBillingClient.startConnection(new BillingClientStateListener() {


                                @Override
                                public void onBillingSetupFinished(BillingResult billingResult) {
                                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                        // The billing client is ready. You can query purchases here.

                                        List<String> skuList = new ArrayList<>();
                                        // skuList.add("android.test.purchased");
                                        skuList.add("viscabarca20");

                                        SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                                                .setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build();
                                        mBillingClient.querySkuDetailsAsync(skuDetailsParams,
                                                new SkuDetailsResponseListener() {
                                                    @Override
                                                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                                        Log.d(TAG, "onSkuDetailsResponse: "+new Gson().toJson(skuDetailsList));
                                                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                                .setSkuDetails(skuDetailsList.get(0))
                                                                .build();
                                                        BillingResult billingResponseCode = mBillingClient.launchBillingFlow(FBPaymentActivity.this, flowParams);
                                                        if (billingResponseCode.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                            // do something you want
                                                            identifier ="viscabarca20";
                                                            type = "Monthly";
                                                            coupon1 = "viscabarca20";
                                                            Log.d(TAG, "onSkuDetailsResponse: suceess");
                                                        }
                                                    }


                                                });
                                    }

                                }

                                @Override
                                public void onBillingServiceDisconnected() {
                                    // Try to restart the connection on the next request to
                                    // Google Play by calling the startConnection() method.
                                }
                            });   }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show(); // display a toast when an video is completed

                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<WorkoutApplyCopuonResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_submit:
                if (edtCouponcode.getText().toString().isEmpty()) {
                    Toast.makeText(mycontext, "Please Enter CouponCode", Toast.LENGTH_SHORT).show();
                } else {
                    workoutPaymentPrice(edtCouponcode.getText().toString());
                }
                break;
            case R.id.img_close:
                finish();
                break;
        }
    }
}
